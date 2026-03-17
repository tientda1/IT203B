import java.awt.*;

public class FactoryMethod {
    public Shape create(String type){
        switch (type.toLowerCase()){
            case "circle":
                return new Circle();
            case "rectangle":
                return new Triangle();
            default:
                throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        Shape shape = new FactoryMethod().create("Circle");

        shape.draw();
    }
}


interface Shape{
    void draw();
}

class Circle implements Shape{
    @Override
    public void draw() {
        // Vẽ hình tròn
    }
}

class Triangle implements Shape{
    @Override
    public void draw() {
        // Vẽ tam giác
    }
}


