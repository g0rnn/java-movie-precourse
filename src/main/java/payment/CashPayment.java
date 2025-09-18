package payment;

public class CashPayment implements Payment {

    @Override
    public int pay(int amount) {
        return 0;
    }

    @Override
    public void refund(int amount) {

    }
}
