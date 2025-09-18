import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class TheaterTest {

//    @Test
//    void reserveMovie() throws Exception {
//        //given
//        Theater theater = new Theater();
//
//        //when
//        Ticket ticket = theater.reserve("귀멸의 칼날", "2025-09-16", "19:30", 2);
//
//        //then
//        assertThat(ticket.getTitle()).isEqualTo("귀멸의 칼날");
//        assertThat(ticket.getDate()).isEqualTo(LocalDate.parse("2025-09-16"));
//        assertThat(ticket.getStartTime()).isEqualTo(LocalTime.parse("19:30"));
//        assertThat(ticket.getCompanion()).isEqualTo(2);
//    }

    @Test
    void saveMovie() throws Exception {
        //given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        //when
        theater.save(movie, "2025-09-16", "10:30");
        theater.save(movie, "2025-09-16", "14:20");
        theater.save(movie, "2025-09-16", "19:30");
        theater.save(movie, "2025-09-16", "23:00");

        //then
        assertThat(theater.getScreeningInfoOf(movie, LocalDate.parse("2025-09-16"))
                .stream().map(ScreeningInfo::getStartTime))
                .containsExactly(LocalTime.parse("10:30"), LocalTime.parse("14:20"), LocalTime.parse("19:30"),
                        LocalTime.parse("23:00"));
    }

    @Test
    void saveTwoMovies() throws Exception {
        //given
        Theater theater = new Theater();
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        Movie f1 = new Movie("F1 The Movie", 155);

        //when
        theater.save(tanjiro, "2025-09-16", "10:30");
        theater.save(tanjiro, "2025-09-16", "14:20");
        theater.save(f1, "2025-09-16", "10:30");
        theater.save(f1, "2025-09-16", "15:20");

        //then
        assertThat(theater.getScreeningInfoOf(tanjiro, LocalDate.parse("2025-09-16")).stream()
                .map(ScreeningInfo::getStartTime))
                .containsExactly(LocalTime.parse("10:30"), LocalTime.parse("14:20"));

        assertThat(theater.getScreeningInfoOf(f1, LocalDate.parse("2025-09-16")).stream()
                .map(ScreeningInfo::getStartTime))
                .containsExactly(LocalTime.parse("10:30"), LocalTime.parse("15:20"));
    }

    @Test
    void 각_영화의_상영정보는_서로_다른_인스턴스다() {
        // given
        Theater theater = new Theater();
        Movie tanjiro = new Movie("귀멸의 칼날", 155);
        Movie f1 = new Movie("F1 The Movie", 155);

        theater.save(tanjiro, "2025-09-16", "10:30");
        theater.save(tanjiro, "2025-09-16", "14:20");
        theater.save(f1, "2025-09-16", "10:30");
        theater.save(f1, "2025-09-16", "14:20");

        LocalDate date = LocalDate.parse("2025-09-16");

        Set<ScreeningInfo> tanjiroScreenings = theater.getScreeningInfoOf(tanjiro, date);
        Set<ScreeningInfo> f1Screenings = theater.getScreeningInfoOf(f1, date);

        // 1) 같은 영화 내부에서도 각 상영정보가 서로 다른 객체(참조)인지 확인
        Set<ScreeningInfo> idSet = Collections.newSetFromMap(new IdentityHashMap<>());
        idSet.addAll(tanjiroScreenings);
        assertThat(idSet).hasSize(tanjiroScreenings.size());

        idSet.clear();
        idSet.addAll(f1Screenings);
        assertThat(idSet).hasSize(f1Screenings.size());

        // 2) 서로 다른 영화 간에도 같은 시간대 객체 공유가 없는지(전부 참조가 다름) 확인
        List<ScreeningInfo> all =
                Stream.concat(tanjiroScreenings.stream(), f1Screenings.stream()).toList();
        Set<ScreeningInfo> idAll = Collections.newSetFromMap(new IdentityHashMap<>());
        idAll.addAll(all);
        assertThat(idAll).hasSize(all.size());

        // 3) 스폿 체크: 같은 10:30이라도 서로 다른 객체여야 함
        ScreeningInfo t1030 = tanjiroScreenings.stream()
                .filter(si -> si.getStartTime().equals(LocalTime.parse("10:30")))
                .findFirst().orElseThrow();
        ScreeningInfo f1030 = f1Screenings.stream()
                .filter(si -> si.getStartTime().equals(LocalTime.parse("10:30")))
                .findFirst().orElseThrow();
        assertThat(t1030).isNotSameAs(f1030); // == 비교(동일성)
    }

    @Test
    void 상영_시간은_영화_상영길이에_따라_정해진다() throws Exception {
        // given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        //when
        theater.save(movie, "2025-10-19", "19:30");

        //then
        assertThrows(IllegalArgumentException.class,
                () -> theater.save(movie, "2025-10-19", "19:30"));
    }

    @Test
    void 상영_시간은_영화_상영길이에_따라_정해진다2() throws Exception {
        // given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        //when
        theater.save(movie, "2025-10-19", "19:30");

        //then
        assertThrows(IllegalArgumentException.class,
                () -> theater.save(movie, "2025-10-19", "18:30"));
    }

    @Test
    void 상영_시간은_영화_상영길이에_따라_정해진다3() throws Exception {
        // given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        //when
        theater.save(movie, "2025-10-19", "19:30");
        theater.save(movie, "2025-10-19", "23:00");

        //then
        assertEquals(2, theater.getScreeningInfoOf(movie, LocalDate.parse("2025-10-19")).size());
        System.out.println("theater = " + theater.getScreeningInfoOf(movie, LocalDate.parse("2025-10-19")));
        assertThrows(IllegalArgumentException.class,
                () -> theater.save(movie, "2025-10-19", "22:05"));

        assertThrows(IllegalArgumentException.class,
                () -> theater.save(movie, "2025-10-19", "22:10"));
    }

    @Test
    void 상영_시간은_이전_영화_종료_시간_30분_이후부터_가능하다() throws Exception {
        // given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        //when
        theater.save(movie, "2025-10-19", "19:30");

        //then
        assertThrows(IllegalArgumentException.class,
                () -> theater.save(movie, "2025-10-19", "22:34"));
        assertDoesNotThrow(() -> theater.save(movie, "2025-10-19", "22:35")); // 이전 영화 종료시간에 대해 닫혀 있음
    }

    @Test
    void 기본적인_상영관_운영_시간은_8시반에서_00시반이다() throws Exception {
        //given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        assertDoesNotThrow(() -> theater.save(movie, "2025-10-19", "08:30"));
        assertDoesNotThrow(() -> theater.save(movie, "2025-10-20", "00:30"));

    }

    @Test
    void 상영관_운영_시간을_벗어나면_예외를_던진다() throws Exception {
        //given
        Theater theater = new Theater();
        Movie movie = new Movie("귀멸의 칼날", 155);

        assertThrows(IllegalArgumentException.class, () -> theater.save(movie, "2025-10-19", "08:29"));
        assertThrows(IllegalArgumentException.class, () -> theater.save(movie, "2025-10-20", "00:31"));
    }
}
