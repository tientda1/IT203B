package Functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        IColorable obj = new Shape();

        obj.draw();
        obj.printColor("red");
        IColorable.erase();

        // Function interface dựng sẵn
        // Consumer, Predicate,
        // Function
        // Supplier


        // Lambda expression : Là cú pháp của 1 method dùng để tạo nhanh đối tượng

        IFunctional lb = (a,b) -> 1;

//        Comparator<Cat> com1 = (c1,c2) -> -1;
//        Comparator<Cat> com2 = (c2,c1) -> 1;
        Collections.sort(new ArrayList<>(), (o1,o2) -> 1);

        int[] arr = {1,2,3,4,5};

        Arrays.stream(arr).map(value -> value*value).forEach(t -> System.out.println(t));
    }
}
