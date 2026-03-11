package SS4_Testing;

import org.junit.jupiter.api.*;

class MainTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @Disabled
    @Test
    void sum() {
        Main main = new Main();
        // Giá trị mong muốn : a=1, b=2
        Assertions.assertEquals(4,main.sum(1,2));
    }

    @Test
    void sum2() {
        System.out.println("sum2");
    }
}