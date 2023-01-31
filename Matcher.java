package result;

import result.checks.CheckChain;
import result.checks.ErrorChecks;
import result.checks.FinalCheck;
import result.parser.DriverEntry;
import result.parser.TimeEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Matcher {

    protected Map<String, Driver> drivers;

    public Matcher() {
        drivers = new TreeMap<>();
    }

    public void addStartTimes(List<TimeEntry> newTimes) {
        for (TimeEntry entry : newTimes) {
            getDriver(entry.getNumber()).addStart(entry.getTime());
        }
    }

    public void addEndTimes(List<TimeEntry> newTimes) {
        for (TimeEntry entry : newTimes) {
            getDriver(entry.getNumber()).addEnd(entry.getTime());
        }
    }

    public void addDrivers(List<DriverEntry> newDrivers) {
        for (DriverEntry entry : newDrivers) {
            getDriver(entry.getNumber()).setName(entry.getName());
        }
    }

    private Driver getDriver(String number) {
        Driver driver;
        if ((driver = drivers.get(number)) == null) {
            driver = new Driver(number);
            drivers.put(number, driver);
        }
        return driver;
    }

    public List<ResultEntry> result() {
        List<ResultEntry> results = new ArrayList<>();
        CheckChain chain = new CheckChain(){};
        chain.addCheck(new ErrorChecks.CheckMissingTimes()).
                addCheck(new ErrorChecks.CheckMultipleTimes()).
                addCheck(new FinalCheck());


        for (Driver driver : drivers.values()) {
            ResultEntry result = new ResultEntry(chain.getNumber(driver),
                    chain.getName(driver),
                    chain.getErrors(driver),
                    chain.getStart(driver),
                    chain.getEnd(driver),
                    chain.getTotal(driver));
            if(!chain.getNumber(driver).equals("StartNr"))
                results.add(result);
        }
        // To commit
        ResultProgram resultProgram = new ResultProgram();

        if(resultProgram.isSorted()) {
            return results.stream().sorted().collect(Collectors.toList());
        } else {
            return results;
        }
        
    }

}