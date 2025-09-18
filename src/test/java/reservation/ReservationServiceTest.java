package reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-16");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");

        ReservationService reservation = new ReservationService(theater);

        //when
        Ticket ticket = reservation.reserve(new ReservationDto(tanjiro, date, time, seats));

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
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-16");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");

        ReservationService reservation = new ReservationService(theater);

        //when
        Ticket ticket = reservation.reserve(new ReservationDto(tanjiro, date, time, seats));

        //then
        ScreeningInfo screeningInfo = theater.findScreeningInfoBy(tanjiro, date, time);

        assertNotNull(ticket);
        assertNotNull(screeningInfo);
        assertTrue(screeningInfo.isReserved("B1"));
        assertTrue(screeningInfo.isReserved("B2"));
    }

    @Test
    void 존재하지_않는_영화를_예매하면_예외를_던진다() throws Exception {
        //given
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-16");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");

        ReservationService reservation = new ReservationService(theater);

        //when
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservation.reserve(new ReservationDto(tanjiro, LocalDate.parse("2025-08-16"), time, seats));
                    reservation.reserve(new ReservationDto(tanjiro, date, LocalTime.parse("10:33"), seats));
                });
    }

    @Test
    void 이미_예약된_좌석을_예매하면_예외를_던진다() throws Exception {
        //given
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-16");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");

        //when
        ReservationService reservation = new ReservationService(theater);
        reservation.reserve(new ReservationDto(tanjiro, date, time, seats));

        //then
        assertThrows(IllegalArgumentException.class,
                () -> reservation.reserve(new ReservationDto(tanjiro, date, time, seats)));
    }

    @Test
    void 여러_영화를_한번에_예매할_수_있다() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2025-09-16");
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalTime tanjiroTime = LocalTime.parse("10:30");
        List<String> tanjiroSeat = List.of("B1", "B2");

        Movie f1 = new Movie("F1 the Movie", 155);
        LocalTime f1Time = LocalTime.parse("17:20");
        List<String> f1Seat = List.of("B1", "B2");

        List<ReservationDto> reservationDtos = List.of(
                new ReservationDto(tanjiro, date, tanjiroTime, tanjiroSeat),
                new ReservationDto(f1, date, f1Time, f1Seat)
        );

        //when
        ReservationService reservation = new ReservationService(theater);
        List<Ticket> tickets = reservation.reserveAll(reservationDtos);

        //then
        assertThat(tickets.size()).isEqualTo(2);
        assertTrue(tickets.stream().map(Ticket::getMovie)
                .anyMatch(movie -> movie.equals(tanjiro)));
        assertTrue(tickets.stream().map(Ticket::getMovie)
                .anyMatch(movie -> movie.equals(f1)));
    }

    @Test
    void 상영_시간이_겹치는_영화는_예매할_수_없다() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2025-09-16");
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalTime tanjiroTime = LocalTime.parse("10:30"); // endTime="13:05"
        List<String> tanjiroSeat = List.of("B1", "B2");

        Movie f1 = new Movie("F1 the Movie", 155);
        LocalTime f1Time = LocalTime.parse("09:00");
        List<String> f1Seat = List.of("B1", "B2");

        List<ReservationDto> reservationDtos = List.of(
                new ReservationDto(tanjiro, date, tanjiroTime, tanjiroSeat),
                new ReservationDto(f1, date, f1Time, f1Seat)
        );

        ReservationService reservation = new ReservationService(theater);

        //when & then
        assertThrows(IllegalArgumentException.class,
                () -> reservation.reserveAll(reservationDtos));
    }

    @Test
    void 예매가_취소되면_좌석도_돌아온다() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2025-09-16");
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        LocalTime tanjiroTime = LocalTime.parse("10:30"); // endTime="13:05"
        List<String> tanjiroSeat = List.of("B1", "B2");

        Movie f1 = new Movie("F1 the Movie", 155);
        LocalTime f1Time = LocalTime.parse("09:00");
        List<String> f1Seat = List.of("B1", "B2");

        List<ReservationDto> reservationDtos = List.of(
                new ReservationDto(tanjiro, date, tanjiroTime, tanjiroSeat),
                new ReservationDto(f1, date, f1Time, f1Seat)
        );

        ReservationService reservation = new ReservationService(theater);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> reservation.reserveAll(reservationDtos));

        ScreeningInfo tanjiroScreening = theater.findScreeningInfoBy(tanjiro, date, tanjiroTime);
        assertFalse(tanjiroScreening.isReserved("B1"));
        assertFalse(tanjiroScreening.isReserved("B2"));

        ScreeningInfo f1Screening = theater.findScreeningInfoBy(f1, date, f1Time);
        assertFalse(f1Screening.isReserved("B1"));
        assertFalse(f1Screening.isReserved("B2"));
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