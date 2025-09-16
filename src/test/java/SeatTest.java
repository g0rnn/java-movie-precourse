import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    void reservedSeats() throws Exception {
        //given
        Seat seat = new Seat();

        //when
        seat.init();
        seat.reserve("E4");

        //then
        System.out.println(seat);
        assertThat(seat.toString()).containsOnlyOnce("__");
    }

}