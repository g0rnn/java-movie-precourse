package discount;

import reservation.ReservationDto;

public class MovieDayDiscountPolicy implements DiscountPolicy {

    private final int rate = 10;

    @Override
    public int discount(ReservationDto reservation, int price) {
        int day = reservation.date().getDayOfMonth();
        if (day == 10 || day == 20 || day == 30) {
            return price - price * rate / 100;
        }
        return price;
    }

    @Override
    public int priority() {
        return 1;
    }
}
