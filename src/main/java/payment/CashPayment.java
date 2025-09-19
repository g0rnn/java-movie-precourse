package payment;

public class CashPayment implements Payment {

    private final PaymentType type = PaymentType.CASH;

    @Override
    public int pay(int amount) {
        return 0;
    }

    @Override
    public void refund(int amount) {

    }

    @Override
    public PaymentType getType() {
        return type;
    }
}
