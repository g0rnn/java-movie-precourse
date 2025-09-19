package payment;

public class CardPayment implements Payment {

    private final PaymentType type = PaymentType.CARD;

    @Override
    public int pay(int amount) {
        return 0;
    }

    @Override
    public void refund(int amount) {

    }

    @Override
    public PaymentType getType() {
        return this.type;
    }
}
