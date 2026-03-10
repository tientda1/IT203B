package datetime;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // Trước java 8
        Date date = new Date(); // Trả về thời gian của hệ thống theo mili giây
        System.out.println("miliseconds: " + date.getTime());
        System.out.println(date);

        // Tạo 1 ngày 2-2-2007

        // Date time API
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(today);
        System.out.println(time);
        System.out.println(dateTime);
        System.out.println(zonedDateTime);

        LocalDate bornIn = LocalDate.of(2006, 7, 12);

        Period age = Period.between(bornIn, today);
        System.out.println("Tuổi Linh: "+ age);

        LocalDate nextDate = bornIn.plusDays(100);
        System.out.println(nextDate);

        // Optional
        Optional<Integer> op1 = Optional.empty();
        Optional<Integer> op2 = Optional.of(1);
        Optional<Integer> op3 = Optional.ofNullable(2);

        if (op3.isPresent()) {
            // Có giá trị khác null
            //Lấy ra giá trị
            System.out.println(op3.get());
        }

        // Lấy trực tiếp giá trị nếu nó tồn tại hoặc 1 giá trị chỉ định
        Integer value = op3.orElse(100);

        // Lấy trực tiếp giá trị hoặc ném ngoại lệ
        Integer val = op3.orElseThrow(()-> new RuntimeException("Lỗi gì đó"));

        // Ví dụ:
        List<Integer> randomList = Stream.generate(() -> new Random().nextInt(400)-200) // Tạo ngẫu nhiên
                .limit(10) // Giới hạn
                .toList();
        // Tìm giá trị lớn nhất trong danh sách
        // Tìm ra giá trị đầu tiên chia hết cho 3 trong danh sách
        Integer max = randomList.stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE);

        Integer find = randomList.stream().filter(valuee -> valuee%3==0)
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Không có số nào chia hết cho 3"));
    }
}
