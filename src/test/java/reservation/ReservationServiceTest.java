package reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screening.Movie;
import screening.ScreeningInfo;
import screening.Theater;

class ReservationServiceTest {

    private Theater theater;

    @BeforeEach
    void init() {
        this.theater = initMovie();
    }

    @Test
    void 영화_티켓에는_영화_상영_정보가_담겨있다() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2025-09-16");
        LocalTime time = LocalTime.parse("10:30");
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        List<String> seats = List.of("B1", "B2");

        ReservationService reservation = new ReservationService(theater);

        //when
        Ticket ticket = reservation.reserve(tanjiro, date, time, seats);

        //then
        assertThat(ticket.getMovie()).isEqualTo(tanjiro);
        assertThat(ticket.getDate()).isEqualTo(date);
        assertThat(ticket.getStartTime()).isEqualTo(time);
        assertThat(ticket.getSeats()).containsExactly("B1", "B2");
        assertThat(ticket.getSeats().size()).isEqualTo(2);
    }

    @Test
    void 티켓을_발급하면_좌석이_예약_처리된다() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2025-09-16");
        LocalTime time = LocalTime.parse("10:30");
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        List<String> seats = List.of("B1", "B2");

        ReservationService reservation = new ReservationService(theater);

        //when
        Ticket ticket = reservation.reserve(tanjiro, date, time, seats);

        //then
        ScreeningInfo screeningInfo = theater.findScreeningInfoBy(tanjiro, date, time);

        assertNotNull(ticket);
        assertNotNull(screeningInfo);
        assertTrue(screeningInfo.isReserved("B1"));
        assertTrue(screeningInfo.isReserved("B2"));
    }

    private Theater initMovie() {
        Theater theater = new Theater();
        Movie tanjiro = new Movie("귀멸의 칼날", 155);

        theater.save(tanjiro, "2025-09-16", "10:30");
        theater.save(tanjiro, "2025-09-16", "14:20");
        theater.save(tanjiro, "2025-09-17", "19:30");
        theater.save(tanjiro, "2025-09-17", "23:00");

        Movie f1 = new Movie("F1 the Movie", 155);

        theater.save(f1, "2025-09-16", "09:00");
        theater.save(f1, "2025-09-16", "17:20");
        theater.save(f1, "2025-09-17", "09:20");
        theater.save(f1, "2025-09-17", "18:00");

        return theater;
    }
}