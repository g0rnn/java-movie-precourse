package reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import screening.Movie;
import screening.Theater;

public class ReservationService {

    private final Theater theater;

    public ReservationService(Theater theater) {
        this.theater = theater;
    }

    public Ticket reserve(Movie movie, LocalDate date, LocalTime startTime, List<String> seats) {
        if (theater.canReserve(movie, date, startTime, seats)) {
            theater.reserveSeats(movie, date, startTime, seats);
            return theater.issue(movie, date, startTime, seats);
        }
        return null;
    }
}
