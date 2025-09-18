package screening;

import java.time.LocalTime;
import seat.Seat;
import seat.Seats;

public class ScreeningInfo implements Comparable<ScreeningInfo> {

    private LocalTime startTime;
    private final Seats seats = Seats.create();

    public ScreeningInfo(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public boolean isReserved(String seatName) {
        return seats.isReserved(Seat.from(seatName));
    }

    public int reserve(String seatName) {
        return seats.reserve(seatName);
    }

    public void rollback(String seatName) {
        seats.rollback(seatName);
    }

    public static ScreeningInfo from(String startTime) {
        // TODO: validate(startTime);
        return new ScreeningInfo(LocalTime.parse(startTime));
    }

    public static ScreeningInfo from(LocalTime startTime) {
        // TODO: validate(startTime);
        return new ScreeningInfo(startTime);
    }

    @Override
    public int compareTo(ScreeningInfo o) {
        return this.startTime.compareTo(o.startTime);
    }
}
