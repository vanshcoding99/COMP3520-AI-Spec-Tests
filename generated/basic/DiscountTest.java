package bench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DiscountTest {

    private static final double DELTA = 1e-9;

    @Test(expected = IllegalArgumentException.class)
    public void negativeUnitPriceThrows() {
        Discount.finalPrice(-0.01, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroQuantityThrows() {
        Discount.finalPrice(10.0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeQuantityThrows() {
        Discount.finalPrice(10.0, -1);
    }

    @Test
    public void zeroUnitPriceAllowed() {
        assertEquals(0.0, Discount.finalPrice(0.0, 5), DELTA);
        assertEquals(0.0, Discount.finalPrice(0.0, 10), DELTA);
    }

    @Test
    public void noDiscountSmallOrder() {
        assertEquals(10.0, Discount.finalPrice(10.0, 1), DELTA);
        assertEquals(29.97, Discount.finalPrice(9.99, 3), DELTA);
    }

    @Test
    public void noDiscountJustBelow100() {
        assertEquals(99.0, Discount.finalPrice(11.0, 9), DELTA);
        assertEquals(99.99, Discount.finalPrice(99.99, 1), DELTA);
    }

    @Test
    public void fivePercentAtExactly100() {
        assertEquals(95.0, Discount.finalPrice(25.0, 4), DELTA);
        assertEquals(95.0, Discount.finalPrice(100.0, 1), DELTA);
    }

    @Test
    public void fivePercentAbove100() {
        assertEquals(171.0, Discount.finalPrice(20.0, 9), DELTA);
        assertEquals(190.0, Discount.finalPrice(200.0, 1), DELTA);
    }

    @Test
    public void tenPercentAtQuantityTen() {
        assertEquals(9.0, Discount.finalPrice(1.0, 10), DELTA);
        assertEquals(90.0, Discount.finalPrice(10.0, 10), DELTA);
    }

    @Test
    public void tenPercentLargeQuantity() {
        assertEquals(18.0, Discount.finalPrice(1.0, 20), DELTA);
    }

    @Test
    public void onlyTenPercentWhenBothConditionsMet() {
        // subtotal 200, quantity 10: only 10% applies, not 10% + 5%
        assertEquals(180.0, Discount.finalPrice(20.0, 10), DELTA);
        assertEquals(1350.0, Discount.finalPrice(100.0, 15), DELTA);
    }

    @Test
    public void quantityNineUsesSubtotalRule() {
        assertEquals(9.0, Discount.finalPrice(1.0, 9), DELTA);
    }

    @Test
    public void roundsToTwoDecimals() {
        // 3 * 0.333 = 0.999 -> 1.0
        assertEquals(1.0, Discount.finalPrice(0.333, 3), DELTA);
        // 1.234 * 10 = 12.34 * 0.9 = 11.106 -> 11.11
        assertEquals(11.11, Discount.finalPrice(1.234, 10), DELTA);
        // 0.111 * 1 = 0.111 -> 0.11
        assertEquals(0.11, Discount.finalPrice(0.111, 1), DELTA);
    }

    @Test
    public void roundsFivePercentResult() {
        // 33.33 * 3 = 99.99 no discount
        assertEquals(99.99, Discount.finalPrice(33.33, 3), DELTA);
        // 50.55 * 2 = 101.1 * 0.95 = 96.045 -> approx 96.04 or 96.05 depending on FP;
        // use a value without ambiguity: 101.0 * 0.95 = 95.95
        assertEquals(95.95, Discount.finalPrice(50.5, 2), DELTA);
    }
}
