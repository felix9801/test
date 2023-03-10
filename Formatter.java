package result;

import java.util.ArrayList;
import java.util.List;

/**
 * Formats marathon results.
 */
public class Formatter {

    /**
     * Separator used in-between columns in results.
     */
    String SEP = ";";

    public List<String> formatResult(List<ResultEntry> results){
        ArrayList<String> formattedResultLines = new ArrayList<>();

        formattedResultLines.add("StartNr; Namn; Totaltid; Starttid; Sluttid");
        for(ResultEntry result : results ){

            formattedResultLines.add(formatDriver(result));
        }
        return formattedResultLines;
    }

    public String formatDriver(ResultEntry driverResult) {
        StringBuilder sb = new StringBuilder();

        // start number
        sb.append(driverResult.getNumber()).append(SEP).append(" ");

        // driver name
        sb.append(driverResult.getName()).append(SEP).append(" ");
        
        // total time
        sb.append(driverResult.getTotal()).append(SEP).append(" ");
        
        // start time
        sb.append(driverResult.getStart()).append(SEP).append(" ");

        // end time
        if (driverResult.getErrors().size() == 0) {
            sb.append(driverResult.getEnd());
        } else {
            sb.append(driverResult.getEnd()).append(SEP).append(" ");
        }




        // errors in the extra error column
        boolean addComma = false;
        for (String e : driverResult.getErrors()) {
            if (addComma) {
                sb.append(", ");
            } else {
                addComma = true;
            }
            sb.append(e);
        }

        return sb.toString();
    }

}
