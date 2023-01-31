package result;

import java.util.List;
import java.io.EOFException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
public class ResultEntry implements Comparable<ResultEntry> {
    String number;
    String name;
    List<String> errors;
    String start;
    String end;
    String total;

    public ResultEntry(String number, String name, List<String> errors, String start, String end, String total) {
        this.number = number;
        this.name = name;
        this.errors = errors;
        this.start = start;
        this.end = end;
        this.total = total;
    }

    public String getNumber() {
        return number;
    }


    public String getName() {
        return name;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getTotal() {
        return total;
    }

    @Override
    public int compareTo(ResultEntry other) {

        if(!isValidLocalTime(total)){
            return 1;
        }
        else if(!isValidLocalTime(other.total)){
            return -1;
        } else {
            return LocalTime.parse(total).compareTo(LocalTime.parse(other.total));
        }
    }

    private boolean isValidLocalTime(String time){
        try {
            LocalTime.parse(time);
        } catch(DateTimeParseException e) {
            return false;
        }
        return true;
    }

}
