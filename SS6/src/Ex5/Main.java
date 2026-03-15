package Ex5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Ticket {
    private String ticketId;
    private String roomName;
    private boolean isSold;
    private boolean isHeld;
    private long holdExpiryTime;
    private boolean isVIP;

    public Ticket(String ticketId, String roomName, boolean isVIP) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isVIP = isVIP;
        this.isSold = false;
        this.isHeld = false;
        this.holdExpiryTime = 0;
    }

    public String getTicketId() { return ticketId; }
    public String getRoomName() { return roomName; }
    public boolean isSold() { return isSold; }
    public void setSold(boolean sold) { isSold = sold; }
    public boolean isHeld() { return isHeld; }
    public void setHeld(boolean held) { isHeld = held; }
    public long getHoldExpiryTime() { return holdExpiryTime; }
    public void setHoldExpiryTime(long time) { this.holdExpiryTime = time; }
    public boolean isVIP() { return isVIP; }
}

class TicketPool {
    private String roomName;
    private List<Ticket> tickets;

    public TicketPool(String roomName, int totalTickets, int vipCount) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        for (int i = 1; i <= totalTickets; i++) {
            boolean isVIP = (i <= vipCount);
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", i), roomName, isVIP));
        }
    }

    public String getRoomName() { return roomName; }

    public synchronized Ticket holdTicket(String counterName, boolean wantVip) {
        String typeStr = wantVip ? "VIP" : "thường";

        for (Ticket t : tickets) {
            if (!t.isSold() && !t.isHeld() && t.isVIP() == wantVip) {
                t.setHeld(true);
                t.setHoldExpiryTime(System.currentTimeMillis() + 5000); // Giữ trong 5 giây
                System.out.println(counterName + ": Đã giữ vé " + t.getTicketId() +
                        (wantVip ? " (VIP)" : "") + ". Vui lòng thanh toán trong 5s");
                return t;
            }
        }

        boolean hasHeld = false;
        for (Ticket t : tickets) {
            if (!t.isSold() && t.isHeld() && t.isVIP() == wantVip) {
                hasHeld = true; break;
            }
        }

        if (hasHeld) {
            System.out.println(counterName + ": Vé " + typeStr + " phòng " + roomName +
                    " đang được giữ bởi quầy khác, chờ...");
        } else {
            System.out.println(counterName + ": Đã bán hết sạch vé " + typeStr + " phòng " + roomName);
        }
        return null;
    }

    public synchronized boolean sellHeldTicket(String counterName, Ticket ticket) {
        if (ticket.isHeld() && !ticket.isSold()) {
            ticket.setHeld(false);
            ticket.setSold(true);
            System.out.println(counterName + ": Thanh toán thành công vé " + ticket.getTicketId());
            return true;
        }
        return false;
    }

    public synchronized void releaseExpiredTickets() {
        long currentTime = System.currentTimeMillis();
        for (Ticket t : tickets) {
            if (t.isHeld() && !t.isSold() && currentTime > t.getHoldExpiryTime()) {
                t.setHeld(false);
                t.setHoldExpiryTime(0);
                System.out.println("TimeoutManager: Vé " + t.getTicketId() +
                        " hết hạn giữ, đã trả lại kho.");
            }
        }
    }
}

class TimeoutManager implements Runnable {
    private List<TicketPool> pools;

    public TimeoutManager(List<TicketPool> pools) {
        this.pools = pools;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                for (TicketPool pool : pools) {
                    pool.releaseExpiredTickets();
                }
            } catch (InterruptedException e) {
                System.out.println("TimeoutManager: Hệ thống đóng cửa, ngừng giám sát.");
                Thread.currentThread().interrupt();
            }
        }
    }
}

class BookingCounter implements Runnable {
    private String counterName;
    private List<TicketPool> pools;

    public BookingCounter(String counterName, List<TicketPool> pools) {
        this.counterName = counterName;
        this.pools = pools;
    }

    @Override
    public void run() {
        Random rand = new Random();

        for (int i = 0; i < 2; i++) {
            boolean wantVip = rand.nextBoolean();
            TicketPool targetPool = pools.get(rand.nextInt(pools.size()));

            Ticket heldTicket = null;
            int retries = 0;

            while (heldTicket == null && retries < 3) {
                heldTicket = targetPool.holdTicket(counterName, wantVip);
                if (heldTicket == null) {
                    retries++;
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                }
            }

            if (heldTicket != null) {
                boolean isSlowCustomer = rand.nextInt(100) < 30;
                int paymentTime = isSlowCustomer ? 6000 : 3000;

                try { Thread.sleep(paymentTime); } catch (InterruptedException e) {}

                boolean success = targetPool.sellHeldTicket(counterName, heldTicket);
                if (!success) {
                    System.out.println(counterName + ": Thanh toán thất bại vé " +
                            heldTicket.getTicketId() + " do quá hạn 5s.");
                }
            }

            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- KHỞI ĐỘNG HỆ THỐNG ĐẶT VÉ TRỰC TUYẾN ---");

        List<TicketPool> pools = new ArrayList<>();
        pools.add(new TicketPool("A", 5, 2));
        pools.add(new TicketPool("B", 4, 1));
        pools.add(new TicketPool("C", 3, 1));

        Thread timeoutThread = new Thread(new TimeoutManager(pools));
        timeoutThread.setDaemon(true);
        timeoutThread.start();

        List<Thread> counterThreads = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Thread t = new Thread(new BookingCounter("Quầy " + i, pools));
            counterThreads.add(t);
            t.start();
        }

        for (Thread t : counterThreads) {
            try { t.join(); } catch (InterruptedException e) {}
        }

        System.out.println("--- TẤT CẢ CÁC QUẦY ĐÃ ĐÓNG CỬA ---");
    }
}
