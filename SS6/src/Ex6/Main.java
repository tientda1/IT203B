package Ex6;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Ticket {
    private String id;
    private boolean isSold;
    private double price = 250000; // 250.000 VNĐ

    public Ticket(String id) {
        this.id = id;
        this.isSold = false;
    }
    public String getId() { return id; }
    public boolean isSold() { return isSold; }
    public void setSold(boolean sold) { isSold = sold; }
    public double getPrice() { return price; }
}

class TicketPool {
    private String roomName;
    private List<Ticket> tickets;
    private int soldCount = 0;
    private double revenue = 0;

    public TicketPool(String roomName, int totalTickets) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        addTickets(totalTickets);
    }

    public String getRoomName() { return roomName; }
    public int getSoldCount() { return soldCount; }
    public int getTotalTickets() { return tickets.size(); }
    public double getRevenue() { return revenue; }

    public synchronized void addTickets(int count) {
        int currentSize = tickets.size();
        for (int i = 1; i <= count; i++) {
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", currentSize + i)));
        }
    }

    public synchronized Ticket sellTicket() {
        for (Ticket t : tickets) {
            if (!t.isSold()) {
                t.setSold(true);
                soldCount++;
                revenue += t.getPrice();
                return t;
            }
        }
        return null;
    }
}

class SimulationManager {
    private List<TicketPool> pools = new ArrayList<>();
    private ExecutorService executor;
    private final Object pauseLock = new Object();
    private volatile boolean isPaused = false;
    private volatile boolean isRunning = false;

    public void initSystem(int numRooms, int ticketsPerRoom, int numCounters) {
        pools.clear();
        int totalTickets = 0;
        char roomChar = 'A';
        for (int i = 0; i < numRooms; i++) {
            pools.add(new TicketPool(String.valueOf((char)(roomChar + i)), ticketsPerRoom));
            totalTickets += ticketsPerRoom;
        }

        System.out.println("Đã khởi tạo hệ thống với " + numRooms + " phòng, " +
                totalTickets + " vé, " + numCounters + " quầy");

        executor = Executors.newFixedThreadPool(numCounters);
        isRunning = true;
        isPaused = false;

        for (int i = 1; i <= numCounters; i++) {
            final String counterName = "Quầy " + i;
            System.out.println(counterName + " bắt đầu bán vé...");
            executor.submit(() -> runCounter(counterName));
        }
    }

    private void runCounter(String counterName) {
        while (isRunning) {
            synchronized (pauseLock) {
                while (isPaused) {
                    try { pauseLock.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                }
            }

            TicketPool targetPool = pools.get((int) (Math.random() * pools.size()));
            Ticket ticket = targetPool.sellTicket();

            if (ticket != null) {
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            } else {
                try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }

    public void pauseSimulation() {
        if (!isRunning) return;
        isPaused = true;
        System.out.println("Đã tạm dừng tất cả quầy bán vé.");
    }

    public void resumeSimulation() {
        if (!isRunning || !isPaused) return;
        isPaused = false;
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
        System.out.println("Đã tiếp tục hoạt động.");
    }

    public void addTicketsToRoom(String roomName, int count) {
        for (TicketPool pool : pools) {
            if (pool.getRoomName().equalsIgnoreCase(roomName)) {
                pool.addTickets(count);
                System.out.println("Đã thêm " + count + " vé vào phòng " + roomName.toUpperCase());
                return;
            }
        }
        System.out.println("Không tìm thấy phòng " + roomName);
    }

    public void printStats() {
        System.out.println("\n=== THỐNG KÊ HIỆN TẠI ===");
        double totalRevenue = 0;
        for (TicketPool pool : pools) {
            System.out.println("Phòng " + pool.getRoomName() + ": Đã bán " +
                    pool.getSoldCount() + "/" + pool.getTotalTickets() + " vé");
            totalRevenue += pool.getRevenue();
        }
        System.out.printf("Tổng doanh thu: %,.0f VNĐ\n", totalRevenue);
    }

    public void stopSimulation() {
        isRunning = false;
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        System.out.println("Đang dừng hệ thống...");
    }
}

class DeadlockDetector {
    public static void checkDeadlock() {
        System.out.println("Đang quét deadlock...");
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();

        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            System.out.println("[CẢNH BÁO] Phát hiện " + deadlockedThreads.length + " luồng bị deadlock!");
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreads);
            for (ThreadInfo info : threadInfos) {
                System.out.println("- Luồng bị treo: " + info.getThreadName() + " (chờ khóa " + info.getLockName() + ")");
            }
            System.out.println("-> Khuyến nghị: Khởi động lại hệ thống hoặc tái cấu trúc thứ tự khóa.");
        } else {
            System.out.println("Không phát hiện deadlock.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimulationManager manager = new SimulationManager();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENU RẠP CHIẾU PHIM ---");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng mô phỏng");
            System.out.println("3. Tiếp tục mô phỏng");
            System.out.println("4. Thêm vé vào phòng");
            System.out.println("5. Xem thống kê");
            System.out.println("6. Phát hiện deadlock");
            System.out.println("7. Thoát");
            System.out.print("Chọn chức năng (1-7): ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhập số phòng: ");
                    int numRooms = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập số vé/phòng: ");
                    int tickets = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập số quầy: ");
                    int counters = Integer.parseInt(scanner.nextLine());
                    manager.initSystem(numRooms, tickets, counters);
                    break;
                case 2:
                    manager.pauseSimulation();
                    break;
                case 3:
                    manager.resumeSimulation();
                    break;
                case 4:
                    System.out.print("Nhập tên phòng (VD: A, B): ");
                    String room = scanner.nextLine();
                    System.out.print("Nhập số lượng vé cần thêm: ");
                    int addCount = Integer.parseInt(scanner.nextLine());
                    manager.addTicketsToRoom(room, addCount);
                    break;
                case 5:
                    manager.printStats();
                    break;
                case 6:
                    DeadlockDetector.checkDeadlock();
                    break;
                case 7:
                    manager.stopSimulation();
                    System.out.println("Kết thúc chương trình.");
                    exit = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
        scanner.close();
    }
}
