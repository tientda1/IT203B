package Ex4;

import java.util.ArrayList;
import java.util.List;

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

    public TicketPool(String roomName, int totalTickets) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        for (int i = 1; i <= totalTickets; i++) {
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", i), roomName));
        }
    }

    public String getRoomName() { return roomName; }

    public Ticket sellTicketDirectly() {
        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) {
                ticket.setSold(true);
                return ticket;
            }
        }
        return null;
    }
}

class BookingCounter implements Runnable {
    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;
    private boolean isDeadlockMode;

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB, boolean isDeadlockMode) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
        this.isDeadlockMode = isDeadlockMode;
    }

    private boolean sellCombo() {
        TicketPool firstLock, secondLock;

        if (isDeadlockMode && counterName.equals("Quầy 2")) {
            firstLock = roomB;
            secondLock = roomA;
        } else {
            firstLock = roomA;
            secondLock = roomB;
        }

        synchronized (firstLock) {
            Ticket firstTicket = firstLock.sellTicketDirectly();

            if (firstTicket != null) {
                System.out.println(counterName + ": Đã lấy vé " + firstTicket.getTicketId() +
                        ", đang chờ vé phòng " + secondLock.getRoomName() + "...");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (secondLock) {
                    Ticket secondTicket = secondLock.sellTicketDirectly();

                    if (secondTicket != null) {
                        System.out.println(counterName + " bán combo thành công: " +
                                firstTicket.getTicketId() + " & " + secondTicket.getTicketId());
                        return true;
                    } else {
                        firstTicket.setSold(false);
                        System.out.println(counterName + ": Hết vé phòng " + secondLock.getRoomName() +
                                ", bán combo thất bại. Đã hoàn lại " + firstTicket.getTicketId());
                        return false;
                    }
                }
            } else {
                System.out.println(counterName + ": Hết vé phòng " + firstLock.getRoomName() + ", bán combo thất bại.");
                return false;
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sellCombo();
            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        boolean FIX_DEADLOCK = false;
        boolean isDeadlockMode = !FIX_DEADLOCK;

        if (isDeadlockMode) {
            System.out.println("--- ĐANG CHẠY CHẾ ĐỘ GÂY DEADLOCK ---");
        } else {
            System.out.println("--- ĐANG CHẠY CHẾ ĐỘ ĐÃ FIX DEADLOCK ---");
        }

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB, isDeadlockMode);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB, isDeadlockMode);

        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);

        thread1.start();
        thread2.start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                if (thread1.isAlive() && thread2.isAlive()) {
                    System.err.println("\n[CẢNH BÁO]: Chương trình bị treo (DEADLOCK)! Hãy dừng thủ công.");
                }
            } catch (Exception e) {}
        }).start();
    }
}
