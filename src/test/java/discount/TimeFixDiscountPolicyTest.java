package discount;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import reservation.ReservationDto;
import screening.Movie;

class TimeFixDiscountPolicyTest {

    @Test
    void 오전_11시_이전_20시_이후엔_2000원_할인() throws Exception {
        //given
        TimeFixDiscountPolicy timeFixDiscountPolicy = new TimeFixDiscountPolicy();
        Movie movie = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-10");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");
        ReservationDto reservation = new ReservationDto(movie, date, time, seats);
        int basePrice = 10000;

        //when
        int discounted = timeFixDiscountPolicy.discount(reservation, basePrice);

        //when & then
        assertThat(discounted).isEqualTo(8000);
    }

    @Test
    void 오전_11시_이전_20시_이후엔_2000원_할인2() throws Exception {
        //given
        TimeFixDiscountPolicy timeFixDiscountPolicy = new TimeFixDiscountPolicy();
        Movie movie = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-10");
        LocalTime time = LocalTime.parse("21:30");
        List<String> seats = List.of("B1", "B2");
        ReservationDto reservation = new ReservationDto(movie, date, time, seats);
        int basePrice = 10000;

        //when
        int discounted = timeFixDiscountPolicy.discount(reservation, basePrice);

        //when & then
        assertThat(discounted).isEqualTo(8000);
    }
}