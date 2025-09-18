package discount;

import java.time.LocalTime;
import reservation.ReservationDto;

public class TimeFixDiscountPolicy implements DiscountPolicy {

    private final int discountPrice = 2000;
    private final LocalTime START_DISCOUNT_TIME = LocalTime.parse("11:00");
    private final LocalTime END_DISCOUNT_TIME = LocalTime.parse("20:00");

    @Override
    public int discount(ReservationDto reservation, int price) {
        LocalTime currentTime = reservation.startTime();

        if (START_DISCOUNT_TIME.isBefore(currentTime) || END_DISCOUNT_TIME.isAfter(currentTime)) {
            return price - 2000;
        }

        return price;
    }
}
