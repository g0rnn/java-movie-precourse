package reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import screening.Movie;

public class Ticket {

    private Movie movie;
    private LocalDate date;
    private LocalTime startTime;
    private List<String> seats;

    public Ticket(Movie movie, LocalDate date, LocalTime startTime, List<String> seats) {
        this.movie = movie;
        this.date = date;
        this.startTime = startTime;
        this.seats = seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public List<String> getSeats() {
        return seats;
    }
}
