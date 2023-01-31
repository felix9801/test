package result;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of a single driver's start number and times.
 */
public class Driver{

    private final List<LocalTime> starts;
    private final List<LocalTime> ends;
    private final String startNumber;
    private String name;

    public Driver(String startNumber) {
        this.startNumber = startNumber;
        starts = new ArrayList<>();
        ends = new ArrayList<>();
    }

    public List<LocalTime> getStarts() {
        // Is it ok to return the private attribute here? Maybe it should return only the first value?
        return starts;
    }

    public void addStart(LocalTime start) {
        this.starts.add(start);
    }

    public List<LocalTime> getEnds() {
        return ends;
    }

    public void addEnd(LocalTime end) {
        this.ends.add(end);
    }

    public String getStartNumber() {
        return startNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getTotal(){
        //long seconds = ends.get(0).toSecondOfDay() - starts.get(0).toSecondOfDay();
        //return LocalTime.ofSecondOfDay(seconds);
         if(starts.size() > 1 || ends.size() > 1){
             return null;
         }
         else{
             long seconds = 0;
             try{
                 seconds = ends.get(0).toSecondOfDay() - starts.get(0).toSecondOfDay(); 
             }catch(Exception e){
                 return null;
             }
             return LocalTime.ofSecondOfDay(seconds);
         }
         
        
     }
 
 
}
