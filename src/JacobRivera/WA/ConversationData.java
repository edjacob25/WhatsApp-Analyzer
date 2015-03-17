package JacobRivera.WA;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EdgarJacob on 01/03/2015.
 */
public class ConversationData {
    private Map<String,Integer> participants = new HashMap<String, Integer>();
    private Map<Date,String> messages = new HashMap<Date, String>();
    private SortedMap<Date,Integer> days = new TreeMap<Date, Integer>();
    private Map<String,Integer> months = new HashMap<String, Integer>();

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
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
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
        float avg;
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

    public Dataset getLinearDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Date iterator : days.keySet()) {
            //System.out.println(days.get(iterator) + iterator.toString());
            dataset.addValue(days.get(iterator),"Mensajes por dia",sdf.format(iterator));
        }
        return dataset;
    }
}
