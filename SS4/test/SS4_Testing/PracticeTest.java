package SS4_Testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PracticeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isPrime() {
        Practice prime = new Practice();
        Assertions.assertTrue(Practice.isPrime(2));
        Assertions.assertTrue(Practice.isPrime(3));
        Assertions.assertTrue(prime.isPrime(4));
    }

}