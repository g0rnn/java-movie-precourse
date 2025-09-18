package payment;

public interface Payment {
    int pay(int amount);

    void refund(int amount);
}
