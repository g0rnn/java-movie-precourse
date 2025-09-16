package seat;

public enum SeatGrade {
    S(18_000),
    A(15_000),
    B(12_000),
    NONE(0);

    private final int price;

    SeatGrade(int price) {
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }
}
