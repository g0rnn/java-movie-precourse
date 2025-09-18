package discount;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import reservation.ReservationDto;
import screening.Movie;

class MovieDayDiscountPolicyTest {

    @Test
    void 매월_특정일마다_할인이_적용된다() throws Exception {
        //given
        MovieDayDiscountPolicy movieDay = new MovieDayDiscountPolicy();
        Movie movie = new Movie("귀멸의 칼날", 155);
        LocalDate date = LocalDate.parse("2025-09-10");
        LocalTime time = LocalTime.parse("10:30");
        List<String> seats = List.of("B1", "B2");
        ReservationDto reservation = new ReservationDto(movie, date, time, seats);
        int basePrice = 10000;

        //when
        int discounted = movieDay.discount(reservation, basePrice);

        //then
        assertThat(discounted).isEqualTo(9000);
    }
}