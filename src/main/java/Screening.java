import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screening {

    private final Map<Movie, List<ScreeningInfo>> info = new HashMap<>();

    public List<ScreeningInfo> get(Movie movie) {
        return info.get(movie);
    }

    public boolean put(Movie movie, String startTime) {
        return this.info.computeIfAbsent(movie, m -> new ArrayList<>())
                .add(ScreeningInfo.from(startTime));
    }
}
