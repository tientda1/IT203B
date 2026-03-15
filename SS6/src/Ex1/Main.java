package Ex1;

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

    public TicketPool(String roomName, int totalTickets) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        for (int i = 1; i <= totalTickets; i++) {
            String ticketId = roomName + "-" + String.format("%03d", i);
            tickets.add(new Ticket(ticketId, roomName));
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public synchronized Ticket sellTicket() {
        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) {
                ticket.setSold(true);
                return ticket;
            }
        }
        return null;
    }

    public synchronized boolean hasTickets() {
        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) {
                return true;
            }
        }
        return false;
    }

    public synchronized int getRemainingTicketsCount() {
        int count = 0;
        for (Ticket ticket : tickets) {
            if (!ticket.isSold()) count++;
        }
        return count;
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

    public int getSoldCount() {
        return soldCount;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (roomA.hasTickets() || roomB.hasTickets()) {
            TicketPool targetRoom;

            if (random.nextBoolean()) {
                targetRoom = roomA;
            } else {
                targetRoom = roomB;
            }

            if (!targetRoom.hasTickets()) {
                targetRoom = (targetRoom == roomA) ? roomB : roomA;
            }

            Ticket soldTicket = targetRoom.sellTicket();

            if (soldTicket != null) {
                soldCount++;
                System.out.println(counterName + " đã bán vé " + soldTicket.getTicketId());
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);

        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);

        System.out.println("--- BẮT ĐẦU BÁN VÉ ---");

        thread1.start();
        thread2.start();

        try {
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