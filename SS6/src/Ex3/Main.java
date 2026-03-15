package Ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Ticket {
    private String ticketId;
    private String roomName;
    private boolean isSold;

    public Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }

    public String getTicketId() { return ticketId; }
    public String getRoomName() { return roomName; }
    public boolean isSold() { return isSold; }
    public void setSold(boolean sold) { isSold = sold; }
}

class TicketPool {
    private String roomName;
    private List<Ticket> tickets;
    private int lastTicketId;
    private boolean supplierDone;

    public TicketPool(String roomName, int initialTickets) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        this.lastTicketId = 0;
        this.supplierDone = false;
        addTickets(initialTickets);
    }

    public String getRoomName() { return roomName; }

    private boolean hasTicketsInternal() {
        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) return true;
        }
        return false;
    }

    public synchronized void setSupplierDone() {
        this.supplierDone = true;
        notifyAll();
    }

    public synchronized boolean isCompletelyExhausted() {
        return supplierDone && !hasTicketsInternal();
    }

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            lastTicketId++;
            String ticketId = roomName + "-" + String.format("%03d", lastTicketId);
            tickets.add(new Ticket(ticketId, roomName));
        }
        notifyAll();
    }

    public synchronized Ticket sellTicket(String counterName) {
        while (!hasTicketsInternal()) {
            if (supplierDone) {
                return null;
            }

            System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) {
                ticket.setSold(true);
                return ticket;
            }
        }
        return null;
    }

    public synchronized int getRemainingTicketsCount() {
        int count = 0;
        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) count++;
        }
        return count;
    }
}

class TicketSupplier implements Runnable {
    private TicketPool roomA;
    private TicketPool roomB;
    private int supplyCount;
    private int interval;
    private int rounds;

    public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
        this.roomA = roomA;
        this.roomB = roomB;
        this.supplyCount = supplyCount;
        this.interval = interval;
        this.rounds = rounds;
    }

    @Override
    public void run() {
        for (int i = 0; i < rounds; i++) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            roomA.addTickets(supplyCount);
            System.out.println("Nhà cung cấp: Đã thêm " + supplyCount + " vé vào phòng A");

            roomB.addTickets(supplyCount);
            System.out.println("Nhà cung cấp: Đã thêm " + supplyCount + " vé vào phòng B");
        }

        roomA.setSupplierDone();
        roomB.setSupplierDone();
    }
}

class BookingCounter implements Runnable {
    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;
    private int soldCount;

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
        this.soldCount = 0;
    }

    public int getSoldCount() { return soldCount; }

    @Override
    public void run() {
        Random random = new Random();

        while (true) {
            boolean aExhausted = roomA.isCompletelyExhausted();
            boolean bExhausted = roomB.isCompletelyExhausted();

            if (aExhausted && bExhausted) {
                break;
            }

            TicketPool targetRoom;
            if (!aExhausted && !bExhausted) {
                targetRoom = random.nextBoolean() ? roomA : roomB;
            } else if (!aExhausted) {
                targetRoom = roomA;
            } else {
                targetRoom = roomB;
            }

            Ticket soldTicket = targetRoom.sellTicket(counterName);

            if (soldTicket != null) {
                soldCount++;
                String note = (Integer.parseInt(soldTicket.getTicketId().split("-")[1]) > 10) ? " (vé mới)" : "";
                System.out.println(counterName + " bán vé " + soldTicket.getTicketId() + note);

                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        TicketSupplier supplier = new TicketSupplier(roomA, roomB, 3, 3000, 2);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);

        Thread supplierThread = new Thread(supplier);
        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);

        System.out.println("--- BẮT ĐẦU BÁN VÉ ---");

        supplierThread.start();
        thread1.start();
        thread2.start();

        try {
            supplierThread.join();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--- Kết thúc chương trình ---");
        System.out.println("Quầy 1 bán được: " + counter1.getSoldCount() + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.getSoldCount() + " vé");
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingTicketsCount());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingTicketsCount());
    }
}
