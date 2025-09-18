package screening;

import java.time.LocalTime;
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

    public static ScreeningInfo from(String startTime) {
        // TODO: validate(startTime);
        return new ScreeningInfo(LocalTime.parse(startTime));
    }

    @Override
    public int compareTo(ScreeningInfo o) {
        return this.startTime.compareTo(o.startTime);
    }
}
