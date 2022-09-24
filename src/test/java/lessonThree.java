import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;


class lessonThree {
    @ParameterizedTest
    @ValueSource(strings = {"123456789012345", "1234567890123456", "12345"})
    void ex10Test(String inputValue) {
        assertTrue(inputValue.length() >= 15, "Значение inputValue больше или равно 15: " + inputValue);
    }
}







