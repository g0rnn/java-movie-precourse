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

    public Ticket reserve(ReservationDto rsv) {
        Movie movie = rsv.movie();
        LocalDate date = rsv.date();
        LocalTime startTime = rsv.startTime();
        List<String> seats = rsv.seats();

        if (theater.canReserve(movie, date, startTime, seats)) {
            theater.reserveSeats(movie, date, startTime, seats);
            return theater.issue(movie, date, startTime, seats);
        }

        throw new IllegalArgumentException("이미 예약된 좌석입니다.");
    }
}
