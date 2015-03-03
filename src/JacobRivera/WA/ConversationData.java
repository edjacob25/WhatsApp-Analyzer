package JacobRivera.WA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by EdgarJacob on 01/03/2015.
 */
public class ConversationData {
    private Map<String,Integer> participants = new HashMap<String, Integer>();
    private Map<Date,String> messages = new HashMap<Date, String>();
    private Map<Date,Integer> days = new HashMap<Date, Integer>();
    private Map<String,Integer> months = new HashMap<String, Integer>();
    //private Map<String,Integer> months = new HashMap<String, Integer>();
    //private String[] messages;
    //private Date[] dates;

    public void addData(String participant, String message, Date date){
        Integer numMess = participants.get(participant);
        participants.put(participant,(numMess == null) ? 1: numMess + 1);

        Integer numDay = days.get(date);
        days.put(date,(numDay == null) ? 1: numDay + 1);

        messages.put(date,message);
    }

    public String[] getParticipants(){
        String[] parts = new String[participants.size()];
        int i = 0;
        for (String iterator : participants.keySet()) {
            parts[i] = iterator;
            i++;
        }
        //participants.size();
        return parts;
    }

    public String getDates(){
        return days.toString();
    }

    public Date getMostTalkedDay(){
        int msg = 0;
        Date date = null;
        for (Date iterator : days.keySet()){
            if (days.get(iterator) > msg) {
                msg = days.get(iterator);
                date = iterator;
            }
        }
        return date;
    }

    public String getMostTalkedMonth() {
        String mostalkedMonth = "";
        int msg = 0;

        for (String mt : months.keySet()) {
            if (months.get(mt) > msg) {
                msg = months.get(mt);
                mostalkedMonth = mt;
            }
        }
        return mostalkedMonth;
    }

    public void createMonthsData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Integer numMonth;
        for (Date iterator : days.keySet()){
            numMonth = months.get(sdf.format(iterator));
            months.put(sdf.format(iterator),(numMonth == null) ? days.get(iterator) : numMonth + days.get(iterator));
        }
    }

    public int getDayData(Date date) {
        return days.get(date);
    }

    public int getMonthData(String month) {
        return months.get(month);
    }

    public int getParticipantData(String pt) {
        return participants.get(pt);
    }

    public float getParticipantAverage(String pt) {
        float avg = 0;
        int tot = getTotalMessages();
        avg =(float) (participants.get(pt)*100.0)/tot;
        return avg;
    }

    public int getTotalMessages(){
        int total = 0;
        for (String iterator : participants.keySet())
            total = total + participants.get(iterator);
        return total;
    }

    public float getDailyAvg() {
        return (float) getTotalMessages() / days.size();
    }
}
