package seat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SeatsTest {

    @Test
    void reservedSeats() throws Exception {
        //given
        Seats seats = Seats.create();

        //when
        seats.reserve("E4");

        //then
        assertThat(seats.toString()).containsOnlyOnce("__");
    }

    @Test
    void throwIfTryReservedSeat() throws Exception {
        //given
        Seats seats = Seats.create();

        //when
        seats.reserve("E4");

        //then
        assertThrows(IllegalArgumentException.class, () -> seats.reserve("E4"));
    }

    @Test
    void reserveSeatA() throws Exception {
        //given
        Seats seats = Seats.create();

        //when
        int normalSeatPrice = seats.reserve("A1");
        int goodSeatPrice = seats.reserve("E4");

        //then
        assertEquals(normalSeatPrice, SeatGrade.B.getPrice());
        assertEquals(goodSeatPrice, SeatGrade.A.getPrice());
    }
}