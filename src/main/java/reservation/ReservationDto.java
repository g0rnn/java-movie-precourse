package reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import screening.Movie;

public record ReservationDto(
        Movie movie,
        LocalDate date,
        LocalTime startTime,
        List<String> seats
) {
}