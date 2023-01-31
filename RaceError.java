package result;

public class RaceError extends Exception {

    public RaceError(String s, Exception e) {
        super(s,e);
    }
}
