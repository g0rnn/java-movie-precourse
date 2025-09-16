package seat;

import java.util.ArrayList;
import java.util.List;

public class Seats {

    private static final int MAX_COLUMN = 8;
    private static final String RESERVED = "__";
    private static final String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private List<List<Seat>> seats;

    public Seats(List<List<Seat>> seats) {
        this.seats = seats;
    }

    public static Seats create() {
        List<List<Seat>> seats = new ArrayList<>();

        for (String prefix : rows) {
            List<Seat> row = new ArrayList<>();
            for (int i = 1; i <= MAX_COLUMN; i++) {
                Seat seat = Seat.valueOf(prefix, i);
                row.add(seat);
            }
            seats.add(row);
        }

        return new Seats(seats);
    }

    public int reserve(String seatName) {
        char row = seatName.charAt(0);
        char col = seatName.charAt(1);

        int rowIdx = row - 'A';
        int colIdx = col - '0' - 1;

        Seat seat = seats.get(rowIdx).get(colIdx);

        if (seat.isReserved()) {
            throw new IllegalArgumentException("이미 예약된 좌석입니다.");
        }
        return seat.reserve();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Seat> row : seats) {
            sb.append(row.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
