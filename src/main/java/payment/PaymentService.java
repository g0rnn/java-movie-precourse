package payment;

import discount.DiscountPolicy;
import java.util.Comparator;
import java.util.List;
import reservation.ReservationDto;

public class PaymentService {

    private final List<DiscountPolicy> discountPolicies;

    public PaymentService(List<DiscountPolicy> discountPolicies) {
        this.discountPolicies = discountPolicies.stream()
                .sorted(Comparator.comparingInt(DiscountPolicy::priority))
                .toList();
    }

    // 사용자가 지불해야하는 금액 반환
    public int process(ReservationDto reservation, int basePrice, Payment payment) {
        int discounted = basePrice;

        // 무비 데이, 시간 조건 적용
        for (DiscountPolicy policy : discountPolicies) {
            discounted = policy.discount(reservation, discounted);
        }

        return discounted;
    }

    public void addDiscountPolicies(DiscountPolicy discountPolicy) {
        this.discountPolicies.add(discountPolicy);
    }
}
