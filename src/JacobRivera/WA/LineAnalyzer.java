package JacobRivera.WA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by EdgarJacob on 01/03/2015.
 */
public class LineAnalyzer {
    public boolean validLine(String line){
        if ((line.split("\\.").length < 2)||(line.split(":").length < 3)||(line.split("\\-").length < 2))
            return false;
        else
            return true;
    }

    public String getMessage(String line){
        String message;
        String[] parts = line.split("\\-");
        try{
            message = parts[1].split(":")[1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Linea de mensaje");
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
            System.out.println("No hay participante en la linea");
            participant = "NO_PART";
        }

        return participant;
    }

    public Date getDate(String line){
        Date date = null;
        String strDate;
        String[] l = line.split("\\.");
        strDate = l[0];

        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(strDate);
        //System.out.println(line);

        // Year
        String strYear;
        Calendar y = new GregorianCalendar();
        int year = y.get(Calendar.YEAR);
        try {
            if (line.split("de").length > 2) {
                strYear = line.split("de")[2].split(" ")[1];
                year = Integer.parseInt(strYear);
            }
        }
        catch (NumberFormatException e) {
            year = y.get(Calendar.YEAR);
        }
        catch (ArrayIndexOutOfBoundsException e){
            //System.out.println(line);
            year = y.get(Calendar.YEAR);
        }

        // Month
        int month = 0;
        try{
            String strMonth = strDate.split(" ")[2];

            if (strMonth.equals("ene"))
                month = 1;
            if (strMonth.equals("feb"))
                month = 2;
            if (strMonth.equals("mar"))
                month = 3;
            if (strMonth.equals("abr"))
                month = 4;
            if (strMonth.equals("may"))
                month = 5;
            if (strMonth.equals("jun"))
                month = 6;
            if (strMonth.equals("jul"))
                month = 7;
            if (strMonth.equals("ago"))
                month = 8;
            if (strMonth.equals("sep"))
                month = 9;
            if (strMonth.equals("oct"))
                month = 10;
            if (strMonth.equals("nov"))
                month = 11;
            if (strMonth.equals("dic"))
                month = 12;
        }
        catch (NumberFormatException e) {
            date = null;
            System.out.println(strDate + " no es una fecha");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            date = null;
            System.out.println(strDate + " no es una fecha");
        }

        try {
            date = st.parse(year+"-"+month+"-"+strDate.split(" ")[0]);
        }
        catch (ParseException e) {
            System.out.println(line);
        }

        return date;
    }

}
