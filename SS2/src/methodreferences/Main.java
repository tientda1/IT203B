package methodreferences;


import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // Tham chiếu của 1 phương thức

        // Duyệt qua 1 danh sách tên học sinh và in ra màn hình
        List<String> names = Arrays.asList("Nam", "Trang", "Sơn");
        for (String name : names) {
            System.out.println(name);
        }

        names.forEach(System.out::println);  // Lambda expression
        names.forEach(Printer::println); // Thay thế lambda expression

        // Biến đổi thành danh sách Student
        List<Student> studentList = names.stream().map(Student::new).toList();

        // Lọc ra những sinh viên trong tên có chứa chữ h
        studentList.stream().filter(student -> student.getName().contains("h")).forEach(System.out::println);

        // 4 phương thức cơ bản : map, filter, foreach, reduce
        Random rad = new Random();
        List<Integer> intergers = Stream.generate(() -> rad.nextInt(100)).limit(20).toList();

        // foreach
        intergers.forEach(System.out::println);

        // filter
        intergers.stream().filter(a -> a % 3 == 0).forEach(System.out::println);

        // Map
        intergers.stream().map(a -> Math.pow(a, 3)).forEach(System.out::println);

        // Reduce
        long total = intergers.stream().reduce(0, Integer::sum);
        System.out.println(total);
    }
}
