package discount;

import reservation.ReservationDto;

public interface DiscountPolicy {
    /**
     * @return 할인이 적용된 금액
     */
    int discount(ReservationDto reservation, int price);

    default int priority() {
        return 100;
    }
}
