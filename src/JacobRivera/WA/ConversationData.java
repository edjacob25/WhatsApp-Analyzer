package JacobRivera.WA;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EdgarJacob on 01/03/2015.
 * Copyright Jacob Rivera 2015
 */
public class ConversationData {
    private Map<String,Integer> participants = new HashMap<String, Integer>();
    private Map<Date,String> messages = new HashMap<Date, String>();
    private SortedMap<Date,Integer> days = new TreeMap<Date, Integer>();
    private SortedMap<Date,Integer> totalDays = new TreeMap<Date, Integer>();
    private Map<String,Integer> months = new HashMap<String, Integer>();

    public void addData(String participant, String message, Date date){
        Integer numMess = participants.get(participant);
        participants.put(participant,(numMess == null) ? 1: numMess + 1);

        Integer numDay = days.get(date);
        days.put(date,(numDay == null) ? 1: numDay + 1);

        messages.put(date,message);
    }

    public void createMonthsData() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Integer numMonth;
        for (Date iterator : days.keySet()){
            numMonth = months.get(sdf.format(iterator));
            months.put(sdf.format(iterator),(numMonth == null) ? days.get(iterator) : numMonth + days.get(iterator));
        }
    }

    public void createTotalDaysData() {
        Date iterator = new Date(days.firstKey().getTime());
        Date last = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(iterator);
        for (; iterator.before(last); calendar.add(Calendar.DAY_OF_YEAR, 1)) {
            iterator = calendar.getTime();
            totalDays.put(iterator, (days.containsKey(iterator)) ? days.get(iterator) : 0);
        }
    }

    public Set<String> getParticipants(){
        return participants.keySet();
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

    public Date getLastTalkedDay() {
        Date date = null;
        for (Date iterator : days.keySet()){
            date = iterator;
        }
        return date;
    }

    public String getMostTalkedMonth() {
        String mostalkedMonth = "";
        int msg = 0;

        for (String mt : months.keySet()) {
            System.out.println(mt + ": " + months.get(mt));
            if (months.get(mt) > msg) {
                msg = months.get(mt);
                mostalkedMonth = mt;
            }
        }
        return mostalkedMonth;
    }

    public int getDayData(Date date) {
        return days.get(date);
    }

    public int getMonthData(String month) {
        return months.get(month);
    }

    public int getParticipantCount(String pt) {
        return participants.get(pt);
    }

    public int getTotalMessages(){
        int total = 0;
        for (String iterator : participants.keySet())
            total = total + participants.get(iterator);
        return total;
    }

    public int getDaysTalked() {
        return totalDays.size();
    }

    public float getParticipantShare(String pt) {
        float avg;
        int tot = getTotalMessages();
        avg =(float) (participants.get(pt)*100.0)/tot;
        return avg;
    }

    public float getDailyAvg() {
        return (float) getTotalMessages() / days.size();
    }

    public float getTotalDailyAvg() {
        return (float) getTotalMessages() / totalDays.size();
    }

    public Dataset getDaysDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Date iterator : days.keySet()) {
            //System.out.println(days.get(iterator) + iterator.toString());
            dataset.addValue(days.get(iterator),"Mensajes por dia",sdf.format(iterator));
        }
        return dataset;
    }

    public Dataset getTotalDaysDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Date iterator : totalDays.keySet()) {
            dataset.addValue(totalDays.get(iterator),"Mensajes por dia",sdf.format(iterator));
        }
        return dataset;
    }

}
