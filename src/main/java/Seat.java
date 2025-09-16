import java.util.ArrayList;
import java.util.List;

public class Seat {

    private static final int MAX_COLUMN = 8;
    private static final String RESERVED = "__";
    private static final String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private List<List<String>> seats;

    public void init() {
        seats = new ArrayList<>();
        for (String prefix : rows) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= MAX_COLUMN; i++) {
                row.add(prefix + i);
            }
            seats.add(row);
        }
    }

    public void reserve(String seatName) {
        char row = seatName.charAt(0);
        char col = seatName.charAt(1);

        int rowIdx = row - 'A';
        int colIdx = col - '0' - 1;
        seats.get(rowIdx).set(colIdx, RESERVED);
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
