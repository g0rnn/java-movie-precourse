package screening;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import reservation.Ticket;

public class Theater {

    private final Map<LocalDate, Screening> schedule = new HashMap<>();

    public boolean save(Movie movie, String date, String startTime) {
        LocalDate screeningDate = LocalDate.parse(date);

        return this.schedule.computeIfAbsent(screeningDate, k -> new Screening())
                .put(movie, screeningDate, startTime);
    }

    public Set<ScreeningInfo> getScreeningInfoOf(Movie movie, LocalDate date) {
        // date 형식 validate -> 0000-00-00
        return schedule.get(date).get(movie);
    }

    public void reserveSeats(Movie movie, LocalDate date, LocalTime startTime, List<String> seats) {
        ScreeningInfo screeningInfo = findScreeningInfoBy(movie, date, startTime);

        for (String seat : seats) {
            screeningInfo.reserve(seat);
        }
    }

    public Ticket issue(Movie movie, LocalDate date, LocalTime startTime, List<String> seats) {

        return new Ticket(movie, date, startTime, seats);
    }

    public ScreeningInfo findScreeningInfoBy(Movie movie, LocalDate date, LocalTime startTime) {
        Set<ScreeningInfo> screeningInfos = this.getScreeningInfoOf(movie, date);

        return screeningInfos.stream()
                .filter(info -> info.getStartTime().equals(startTime))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화 상영입니다."));
    }

    public boolean canReserve(Movie movie, LocalDate date, LocalTime startTime, List<String> seats) {
        ScreeningInfo screeningInfo = findScreeningInfoBy(movie, date, startTime);

        for (String seatName : seats) {
            if (screeningInfo.isReserved(seatName)) {
                return false;
            }
        }
        return true;
    }
}
