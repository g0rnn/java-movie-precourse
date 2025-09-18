package reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public List<Ticket> reserveAll(List<ReservationDto> reservationDtos) throws IllegalArgumentException {
        List<Ticket> tickets = new ArrayList<>();

        for (ReservationDto dto : reservationDtos) {
            tickets.add(reserve(dto));
        }

        // tickets에 겹치는 영화가 있으면 예외
        tickets.sort((t1, t2) -> {
            if (t1.getStartTime().equals(t2.getStartTime())) {
                return Integer.compare(t1.getMovie().getRunningTime(), t2.getMovie().getRunningTime());
            }
            return t1.getStartTime().compareTo(t2.getStartTime());
        });

        for (int i = 0; i < tickets.size() - 1; i++) {
            Ticket current = tickets.get(i);
            LocalTime currentEndTime = current.getStartTime().plusMinutes(current.getMovie().getRunningTime());

            Ticket next = tickets.get(i + 1);
            LocalTime nextStartTime = next.getStartTime();

            if (currentEndTime.isAfter(nextStartTime)) {
                // 좌석 되돌리기
                theater.rollback(tickets);
                throw new IllegalArgumentException("상영 시간이 겹치는 영화가 존재합니다.");
            }
        }

        return tickets;
    }
}
