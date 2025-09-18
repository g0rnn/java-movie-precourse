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
    void 무비데이_할인이_우선_적용된다() throws Exception {
        //given
        PaymentService paymentService = new PaymentService(
                List.of(new TimeFixDiscountPolicy(), new MovieDayDiscountPolicy()));
        CardPayment cardPayment = new CardPayment();

        Movie movie = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-10");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");
        ReservationDto reservation = new ReservationDto(movie, date, time, seats);

        //when
        int finalPrice = paymentService.process(reservation, 10_000, cardPayment);

        //then
        assertThat(finalPrice).isEqualTo(7_000);
    }
}