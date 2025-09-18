package seat;

public class Seat {

    public static final String RESERVED = "__";

    private String name;
    private SeatGrade grade;

    public Seat(String name, SeatGrade grade) {
        this.name = name;
        this.grade = grade;
    }

    public boolean isReserved() {
        return this.name.equals("__");
    }

    public int reserve() {
        this.name = RESERVED;
        return grade.getPrice();
    }

    public void rollback(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Seat valueOf(String row, int col) {
        String name = row + col;
        if (row.equals("E")) {
            return new Seat(name, SeatGrade.A);
        }
        return new Seat(name, SeatGrade.B);
    }

    public static Seat from(String name) {
        return new Seat(name, SeatGrade.B);
    }

}
