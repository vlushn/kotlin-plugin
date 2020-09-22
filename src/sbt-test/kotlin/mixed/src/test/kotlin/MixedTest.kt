import demo.Calculator
import demo.JavaCalculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MixedTest {

    @Test
    fun `should sum 2 plus 2`() {
        val calculator = Calculator(JavaCalculator())
        assertEquals(4, calculator.sum(2, 2))
    }

}