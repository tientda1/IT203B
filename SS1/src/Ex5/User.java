package Ex5;

public class User {
    private int age;

    public void setAge(int age) {
        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm! Vui lòng nhập độ tuổi hợp lệ cho người dùng.");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
