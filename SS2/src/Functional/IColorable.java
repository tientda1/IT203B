package Functional;

public interface IColorable {
    String RED = "abcx";
    void printColor(String color);

    // Java 8
    default void draw(){        // Không bắt buộc lớp con phải ghi đè
        System.out.println("Tô màu!!");
    }


    static void erase(){        // Thuộc về interface
        System.out.println("Xóa");
    }
    private void toStr(){           // Java 9

    }
}
