package seat;

import java.util.ArrayList;
import java.util.List;

public class Seats {

    private static final int MAX_COLUMN = 8;
    private static final String RESERVED = "__";
    private static final String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private List<List<String>> seats;

    public Seats(List<List<String>> seats) {
        this.seats = seats;
    }

    public static Seats create() {
        List<List<String>> seats = new ArrayList<>();

        for (String prefix : rows) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= MAX_COLUMN; i++) {
                row.add(prefix + i);
            }
            seats.add(row);
        }

        return new Seats(seats);
    }

    public void reserve(String seatName) {
        char row = seatName.charAt(0);
        char col = seatName.charAt(1);

        int rowIdx = row - 'A';
        int colIdx = col - '0' - 1;

        List<String> rowSeat = seats.get(rowIdx);

        if (RESERVED.equals(rowSeat.get(colIdx))) {
            throw new IllegalArgumentException("이미 예약된 좌석입니다.");
        }

        rowSeat.set(colIdx, RESERVED);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> row : seats) {
            sb.append(row.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
