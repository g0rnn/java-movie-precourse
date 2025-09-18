import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Theater {

    private final Map<LocalDate, Screening> schedule = new HashMap<>();

    public Ticket reserve(String movieTitle, String date, String startTime, int companion) {
        // 영확가 있는지 확인

        // 티켓을 발급할 수 있는지 확인

        // 티켓 발급

        return new Ticket();
    }

    public boolean save(Movie movie, String date, String startTime) {
        LocalDate screeningDate = LocalDate.parse(date);

        return this.schedule.computeIfAbsent(screeningDate, k -> new Screening())
                .put(movie, screeningDate, startTime);
    }

    public Set<ScreeningInfo> getScreeningInfoOf(Movie movie, LocalDate date) {
        // date 형식 validate -> 0000-00-00
        return schedule.get(date).get(movie);
    }
}
