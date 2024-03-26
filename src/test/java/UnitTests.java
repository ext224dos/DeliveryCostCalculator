import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import testing.project.DeliveryCostCalculator;
import testing.project.DeliveryLoadLevel;
import testing.project.Fragility;
import testing.project.Size;

class UnitTests {
    @Test
    @Tag("smoke")
    @DisplayName("Distance can not be zero or negative")
    void TestZero() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(
                -1,
                Size.SMALL,
                Fragility.NO,
                DeliveryLoadLevel.NORMAL
        );

        Assertions.assertEquals(-1, result);
    }

    @Test
    @Tag("smoke")
    @DisplayName("Minimal cost delivery")
    void TestOne() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(
                1,
                Size.SMALL,
                Fragility.NO,
                DeliveryLoadLevel.NORMAL
        );

        Assertions.assertEquals(400, result);
    }

    @ParameterizedTest
    @Tag("smoke")
    @CsvSource({"880, VERY_HIGH", "770, HIGH", "660, INCREASED", "550, NORMAL"})
    @DisplayName("Check delivery with different DeliveryLoadLevel")
    void TestTwo(Double expectCost, String loadLevel) {
        double result = DeliveryCostCalculator.calculateDeliveryCost(
                1,
                Size.BIG,
                Fragility.YES,
                DeliveryLoadLevel.valueOf(loadLevel)
        );

        Assertions.assertEquals(expectCost, result);
    }


    @ParameterizedTest
    @Tag("smoke")
    @ValueSource(doubles = {-356.65, 834.743, 4, 0, 3232, 4.348534, -34834})
    @DisplayName("Check distance input")
    void TestThree(Double distance) {
        double result = DeliveryCostCalculator.calculateDeliveryCost(
                distance,
                Size.BIG,
                Fragility.YES,
                DeliveryLoadLevel.INCREASED
        );

        Assertions.assertNotNull(result, "The error does not occur at different distances");
    }

    @ParameterizedTest
    @Tag("smoke")
    @CsvSource({"0, -1", "1, 660", "8, 720", "16, 840", "24, 840", "32, -1"})
    @DisplayName("Check fragile delivery")
    void TestFour(Double distance, Double expect) {
        double result = DeliveryCostCalculator.calculateDeliveryCost(
                distance,
                Size.BIG,
                Fragility.YES,
                DeliveryLoadLevel.INCREASED
        );

        Assertions.assertEquals(expect, result);
    }
}
