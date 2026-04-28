package bankAccount;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComputationsTest {

    @Test
    void testFibonacci() {
        assertEquals(0, Computations.fibonacci(0));
        assertEquals(1, Computations.fibonacci(1));
        assertEquals(5, Computations.fibonacci(5));
        assertThrows(IllegalArgumentException.class, () -> Computations.fibonacci(-1));
    }

    @Test
    void testIsPrime() {
        assertFalse(Computations.isPrime(1));
        assertTrue(Computations.isPrime(2));
        assertTrue(Computations.isPrime(7));
        assertFalse(Computations.isPrime(9));
    }

    @Test
    void testParity() {
        assertTrue(Computations.isEven(4));
        assertFalse(Computations.isEven(7));
        assertTrue(Computations.isOdd(7));
        assertFalse(Computations.isOdd(4));
    }

    @Test
    void testTemperatureConversion() {
        // Freezing point
        assertEquals(0.0, Computations.toCelsius(32.0), 0.001);
        assertEquals(32.0, Computations.toFahrenheit(0.0), 0.001);
        // Boiling point
        assertEquals(100.0, Computations.toCelsius(212.0), 0.001);
    }
}