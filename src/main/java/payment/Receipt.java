package payment;

import discount.DiscountPolicy;
import java.util.List;

public class Receipt {

    private int price;
    private int usedPoints;
    private PaymentType paymentType;
    private List<DiscountPolicy> appliedDiscounts;

    public Receipt(int price, int usedPoints, PaymentType paymentType, List<DiscountPolicy> appliedDiscounts) {
        this.price = price;
        this.usedPoints = usedPoints;
        this.paymentType = paymentType;
        this.appliedDiscounts = appliedDiscounts;
    }

    public int getPrice() {
        return price;
    }
}
