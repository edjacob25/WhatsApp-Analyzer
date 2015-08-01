package JacobRivera.WA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EdgarJacob on 01/03/2015.
 */
public class LineAnalyzer {

    private static String[] months = {"ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic"};
    private String monthRegex = "", yearRegex = "";

    public LineAnalyzer() {
        
        /* Used in old regionalizations*/
        /*for (int i= 0;i<months.length;i++){
            monthRegex = monthRegex + months[i];
            if (i!=months.length-1)
                monthRegex = monthRegex + "|";
        }*/

        monthRegex = "\\/[0-1][0-9]\\/";
        yearRegex = "2[0-9]{3}";
    }

    public boolean validLine(String line)   {
        if ((line.split(":").length < 3)||(line.split("\\-").length < 2))
            return false;
        else
            if (getDate(line) != null)
                return true;
            else
                return false;
    }

    public String getMessage(String line){
        String message;
        String[] parts = line.split("\\-");
        try{
            message = parts[1].split(":")[1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Linea de mensaje");
            message = line;
        }

        return message;
    }

    public String getParticipant(String line){
        String[] parts = line.split("\\-");
        String participant;
        try{
            participant = parts[1].split(":")[0];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("No hay participante en la linea");
            participant = "NO_PART";
        }
        return participant;
    }

    public Date getDate(String line){
        Date date = null;
        String strDate = line.split("-")[0];
        Pattern pattern;
        Matcher matcher;
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");

        // Year
        String strYear;
        Calendar y = new GregorianCalendar();
        pattern = Pattern.compile(yearRegex);
        matcher = pattern.matcher(strDate);
        if (matcher.find())
            strYear = matcher.group();
        else
            strYear = y.get(Calendar.YEAR) + "";
        // Month
        int month = 0;
        String strMonth = "";
        pattern = Pattern.compile(monthRegex);
        matcher = pattern.matcher(strDate);
        if (matcher.find())
            strMonth = matcher.group();
        for (int i = 0; i<months.length; i++) {
            if (strMonth.equals(months[i]))
                month = i+1;
        }

        //Day
        try {
            /* Used in old regionalizations */
            //date = st.parse(strYear+"-"+month+"-"+strDate.split(" ")[0]);
            date = st.parse(strYear+"-"+strMonth.substring(1,3)+"-"+strDate.substring(0,2));
        }
        catch (Exception e){
            System.err.println(e);
            return null;
        }
        return date;
    }

}
