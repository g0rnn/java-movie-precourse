import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Screening {

    private static final LocalTime CUTOFF = LocalTime.of(3, 0); // 영화 상영 종료 시간은 최대 03시
    private static final Duration MIN_GAP = Duration.ofMinutes(30);
    private static final String OPEN_TIME = "08:30";
    private static final String CLOSE_TIME = "00:30";

    private final Map<Movie, NavigableSet<ScreeningInfo>> info = new HashMap<>();
    private final LocalTime openTime;
    private final LocalTime closeTime;

    public Screening() {
        this.openTime = LocalTime.parse(OPEN_TIME);
        this.closeTime = LocalTime.parse(CLOSE_TIME);
    }

    public Screening(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public Set<ScreeningInfo> get(Movie movie) {
        return info.get(movie);
    }

    public boolean put(Movie movie, LocalDate screeningDate, String startTime) {
        int runningTime = movie.getRunningTime();
        NavigableSet<ScreeningInfo> screeningInfos = this.info.computeIfAbsent(movie, m -> new TreeSet<>());

        ScreeningInfo newInfo = ScreeningInfo.from(startTime);
        ScreeningInfo lower = screeningInfos.floor(newInfo);
        ScreeningInfo higher = screeningInfos.ceiling(newInfo);

        // 상영관이 운영될 때 영화가 상영되는지
        validateOperatingHour(startTime);

        // 영화 상영 시간이 겹치는지
        validateNeighborMovie(lower, screeningDate, startTime, runningTime);
        validateNeighborMovie(higher, screeningDate, startTime, runningTime);

        return screeningInfos.add(newInfo);
    }

    private void validateOperatingHour(String startTime) {
        LocalTime start = LocalTime.parse(startTime);

        if (start.equals(openTime) || start.equals(closeTime)) {
            return;
        }

        if (start.isBefore(openTime)) {
            throw new IllegalArgumentException(
                    "상영관의 운영 시간은 " + openTime + " ~ " + closeTime + " 입니다. 주어진 시작 시간: " + start
            );
        }

        if (closeTime.isAfter(start) && openTime.isBefore(start)) {
            throw new IllegalArgumentException(
                    "상영관의 운영 시간은 " + openTime + " ~ " + closeTime + " 입니다. 주어진 시작 시간: " + start
            );
        }
    }

    private void validateNeighborMovie(ScreeningInfo neighbor, LocalDate date, String startTime, int runningTime) {
        if (neighbor == null) {
            return;
        }

        LocalDateTime newStart = normalizeStart(date, LocalTime.parse(startTime));
        LocalDateTime newEnd = newStart.plusMinutes(runningTime);

        LocalDateTime otherStart = normalizeStart(date, neighbor.getStartTime());
        LocalDateTime otherEnd = otherStart.plusMinutes(runningTime);

        if (overlaps(newStart, newEnd, otherStart, otherEnd)) {
            throw new IllegalArgumentException("상영 시간이 다른 상영 일정과 겹칩니다." + startTime);
        }
    }

    private LocalDateTime normalizeStart(LocalDate businessDate, LocalTime startTime) {
        if (startTime.isBefore(CUTOFF)) {
            businessDate = businessDate.plusDays(1);
        }
        return LocalDateTime.of(businessDate, startTime);
    }

    private boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd, LocalDateTime bStart, LocalDateTime bEnd) {
        return aStart.isBefore(bEnd.plus(MIN_GAP)) && bStart.isBefore(aEnd.plus(MIN_GAP));
    }
}
