import java.util.Objects;

public class Movie {

    private String name;
    private int runningTime;

    public Movie(String name, int runningTime) {
        this.name = name;
        this.runningTime = runningTime;
    }

    public int getRunningTime() {
        return this.runningTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return runningTime == movie.runningTime && Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, runningTime);
    }
}
