package result;

import result.parser.DriverEntry;
import result.parser.DriversParser;
import result.parser.TimeEntry;
import result.parser.TimesParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The main program. TODO!
 */
public class ResultProgram {

    private Boolean sorted;
    private LocalTime minTime;
    private String raceType;



    public ResultProgram(){
        try {

            String propPath = "../config.properties";

            InputStream input = Files.newInputStream(Paths.get(propPath));

            Properties prop = new Properties();
    
            // load a properties file
            prop.load(input);

            this.sorted = Boolean.parseBoolean(  prop.getProperty("sorted"));
            this.minTime = LocalTime.parse( prop.getProperty("minTime"));
            this.raceType = prop.getProperty("raceType");
            // get the property value and print it out
    
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // try{
    //     Properties config = loadProperties("config.properties");
        
    // }
    // catch(RaceError e){
    //     System.out.println("could not read file");
    //     System.err.println(e.getMessage());
    //     e.printStackTrace();
    // }
}

  

    public boolean isSorted(){
        return sorted;
    }

    public String getRaceType(){
        return raceType;
    }

    public LocalTime getMinTime(){
        return minTime;
    }
   


    public static void main(String[] args) {
        try {
            tryMain( "result/src/main/java/result/data/race.properties");
        } catch (RaceError e) {
            System.out.println("test");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void tryMain(String propPath) throws RaceError {
        // Load settings
        Properties props = loadProperties(propPath);
        String driversPath = (String) props.get("driversPath");
        String startTimesPath = (String) props.get("startTimesPath");
        String endTimesPath = (String) props.get("endTimePath");
        String resultPath = (String) props.get("driverResultPath");


        // Load files
        ArrayList<DriverEntry> driverEntries = new DriversParser().tryParse(driversPath);
        ArrayList<TimeEntry> startTimes = new TimesParser().tryParse(startTimesPath);
        ArrayList<TimeEntry> endTimes = new TimesParser().tryParse(endTimesPath);
        // Add entries
        Matcher matcher = new Matcher();
        matcher.addDrivers(driverEntries);
        matcher.addStartTimes(startTimes);
        matcher.addEndTimes(endTimes);
        // Generate result
        List<ResultEntry> results = matcher.result();
        Formatter formatter = new Formatter();
        writeResult(resultPath, formatter.formatResult(results));
    }

    private static void writeResult(String resultPath, List<String> formattedResultLines) throws RaceError {
        try {
            Files.write(Paths.get(resultPath), formattedResultLines, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RaceError("Error when writing file " + resultPath + " " + e.getMessage(), e);
        }
    }

    public static Properties loadProperties(String propPath) throws RaceError {
        Properties props;
        try (InputStream file = Files.newInputStream(Paths.get(propPath))) {
            props = new Properties();
            props.load(file);
        } catch (IOException e) {
            throw new RaceError("Error when reading file " + propPath, e);
        }
        return props;
    }

}
