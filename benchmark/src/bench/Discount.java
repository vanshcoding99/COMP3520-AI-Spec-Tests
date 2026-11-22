package bench;

public class Discount {
    public static double finalPrice(double unitPrice, int quantity) {
        if (unitPrice < 0 || quantity <= 0) {
            throw new IllegalArgumentException("invalid price or quantity");
        }
        double subtotal = unitPrice * quantity;
        double total;
        if (quantity >= 10) {
            total = subtotal * 0.90;
        } else if (subtotal >= 100.0) {
            total = subtotal * 0.95;
        } else {
            total = subtotal;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}
