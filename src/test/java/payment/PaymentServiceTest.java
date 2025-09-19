package payment;

import static org.assertj.core.api.Assertions.assertThat;

import discount.MovieDayDiscountPolicy;
import discount.TimeFixDiscountPolicy;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import reservation.ReservationDto;
import screening.Movie;

class PaymentServiceTest {

    @Test
    void 결제_영수증엔_가격이_포함되어있다() throws Exception {
        //given
        PaymentService paymentService = new PaymentService();
        CardPayment cardPayment = new CardPayment();
        ReservationDto reservation = getMovieReservation();

        //when
        Receipt receipt = paymentService.process(reservation, 10_000, cardPayment);

        //then
        assertThat(receipt.getPrice()).isEqualTo(10_000);
    }

    @Test
    void 무비데이_할인이_우선_적용된다() throws Exception {
        //given
        PaymentService paymentService = new PaymentService(
                List.of(new TimeFixDiscountPolicy(), new MovieDayDiscountPolicy()));
        CardPayment cardPayment = new CardPayment();
        ReservationDto reservation = getMovieReservation();

        //when
        Receipt receipt = paymentService.process(reservation, 10_000, cardPayment);

        //then
        assertThat(receipt.getPrice()).isEqualTo(7_000);
    }

    private ReservationDto getMovieReservation() {
        Movie movie = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-10");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");
        return new ReservationDto(movie, date, time, seats);
    }
}