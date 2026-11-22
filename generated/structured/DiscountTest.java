package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class DiscountTest {

    private static final double DELTA = 1e-9;

    // Requirement: throws IllegalArgumentException if unitPrice < 0 (closest invalid)
    @Test
    public void negativeUnitPriceThrows() {
        assertThrows(IllegalArgumentException.class, () -> Discount.finalPrice(-0.01, 1));
    }

    // Requirement: throws IllegalArgumentException if quantity <= 0 (zero)
    @Test
    public void zeroQuantityThrows() {
        assertThrows(IllegalArgumentException.class, () -> Discount.finalPrice(10.0, 0));
    }

    // Requirement: throws IllegalArgumentException if quantity <= 0 (negative)
    @Test
    public void negativeQuantityThrows() {
        assertThrows(IllegalArgumentException.class, () -> Discount.finalPrice(10.0, -1));
    }

    // Requirement: unitPrice 0 is valid (boundary) -> 0.0
    @Test
    public void zeroUnitPriceIsValid() {
        assertEquals(0.0, Discount.finalPrice(0.0, 5), DELTA);
    }

    // Requirement: quantity 1 is valid (boundary), no discount below 100
    @Test
    public void quantityOneNoDiscount() {
        assertEquals(20.0, Discount.finalPrice(20.0, 1), DELTA);
    }

    // Requirement: no discount when quantity < 10 and subtotal < 100
    @Test
    public void noDiscountTypical() {
        assertEquals(45.0, Discount.finalPrice(15.0, 3), DELTA);
    }

    // Requirement: quantity 9 (just below 10) with subtotal < 100 -> no discount
    @Test
    public void quantityNineNoDiscount() {
        assertEquals(45.0, Discount.finalPrice(5.0, 9), DELTA);
    }

    // Requirement: quantity 10 (boundary) -> 10% discount
    @Test
    public void quantityTenGetsTenPercent() {
        assertEquals(45.0, Discount.finalPrice(5.0, 10), DELTA);
    }

    // Requirement: quantity 11 (just above 10) -> 10% discount
    @Test
    public void quantityElevenGetsTenPercent() {
        assertEquals(49.5, Discount.finalPrice(5.0, 11), DELTA);
    }

    // Requirement: subtotal just below 100.00 -> no discount
    @Test
    public void subtotalJustBelowHundredNoDiscount() {
        assertEquals(99.99, Discount.finalPrice(99.99, 1), DELTA);
    }

    // Requirement: subtotal exactly 100.00 -> 5% discount
    @Test
    public void subtotalExactlyHundredGetsFivePercent() {
        assertEquals(95.0, Discount.finalPrice(100.0, 1), DELTA);
    }

    // Requirement: subtotal just above 100.00 -> 5% discount (100.02 * 0.95 = 95.019 -> 95.02)
    @Test
    public void subtotalJustAboveHundredGetsFivePercent() {
        assertEquals(95.02, Discount.finalPrice(100.02, 1), DELTA);
    }

    // Requirement: subtotal from multiple units reaching 100 with quantity < 10 -> 5%
    @Test
    public void multipleUnitsReachingHundredGetFivePercent() {
        assertEquals(95.0, Discount.finalPrice(25.0, 4), DELTA);
    }

    // Requirement: quantity 9 with subtotal >= 100 -> 5% discount
    @Test
    public void quantityNineLargeSubtotalGetsFivePercent() {
        assertEquals(171.0, Discount.finalPrice(20.0, 9), DELTA);
    }

    // Requirement: only one discount applies; quantity >= 10 with subtotal >= 100 gets 10% only
    @Test
    public void onlyTenPercentWhenBothConditionsHold() {
        assertEquals(180.0, Discount.finalPrice(20.0, 10), DELTA);
    }

    // Requirement: result rounded to 2 decimal places (0.999 -> 1.0)
    @Test
    public void resultRoundedToTwoDecimals() {
        assertEquals(1.0, Discount.finalPrice(0.333, 3), DELTA);
    }

    // Requirement: rounding is half up (0.125 -> 0.13)
    @Test
    public void roundingIsHalfUp() {
        assertEquals(0.13, Discount.finalPrice(0.125, 1), DELTA);
    }

    // Requirement: rounding after 10% discount (1.15 * 10 = 11.5, * 0.9 = 10.35)
    @Test
    public void roundingAfterTenPercentDiscount() {
        assertEquals(10.35, Discount.finalPrice(1.15, 10), DELTA);
    }

    // Requirement: rounding down when below half (0.123 -> 0.12)
    @Test
    public void roundingDownBelowHalf() {
        assertEquals(0.12, Discount.finalPrice(0.123, 1), DELTA);
    }
}
