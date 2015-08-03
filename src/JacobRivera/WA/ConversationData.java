package JacobRivera.WA;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EdgarJacob on 01/03/2015.
 * Copyright Jacob Rivera 2015
 */
public class ConversationData {
    private Map<String,Integer> participants = new HashMap<String, Integer>();
    private Map<String, Integer> participantsWords = new HashMap<String, Integer>();
    private List<String> messages = new ArrayList<String>();
    private SortedMap<Date,Integer> days = new TreeMap<Date, Integer>();
    private SortedMap<Date,Integer> totalDays = new TreeMap<Date, Integer>();
    private Map<String,Integer> months = new HashMap<String, Integer>();
    private Map<String, Integer> timeofDay = new HashMap<String, Integer>();

    public void addData(String participant, String message, Date date, String time){
        Integer numMess = participants.get(participant);
        participants.put(participant,(numMess == null) ? 1: numMess + 1);

        Integer numDay = days.get(date);
        days.put(date,(numDay == null) ? 1: numDay + 1);

        /* -1 added for the first space at the start */
        Integer wordsAcc = participantsWords.get(participant);
        int words = message.split(" ").length - 1;
        participantsWords.put(participant,(wordsAcc == null) ? words : wordsAcc + words);

        messages.add(message);

        Integer timeCount = timeofDay.get(time);
        timeofDay.put(time,(timeCount == null) ? 1 :timeCount + 1 );
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

    public int getTotalMessages(){
        int total = 0;
        for (String iterator : participants.keySet())
            total = total + participants.get(iterator);
        return total;
    }

    public int getDaysTalked() {
        return totalDays.size();
    }

    public int getParticipantCount(String pt) {
        return participants.get(pt);
    }

    public float getParticipantShare(String pt) {
        float avg;
        int tot = getTotalMessages();
        avg =(float) (participants.get(pt)*100.0)/tot;
        return avg;
    }

    public float getWordsAvg(String pt) {
        return (float) participantsWords.get(pt) / participants.get(pt);
    }

    public float getDailyAvg() {
        return (float) getTotalMessages() / days.size();
    }

    public float getTotalDailyAvg() {
        return (float) getTotalMessages() / totalDays.size();
    }

    public List<String> getMessagesContent(){
        return messages;
    }

    public List<XYChart.Data<String, Integer>> getChartDaysData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<XYChart.Data<String, Integer>> data = new ArrayList<>();
        for (Date date : days.keySet()) {
            data.add(new XYChart.Data(sdf.format(date),days.get(date)));
        }
        return data;
    }

    public List<XYChart.Data<String, Integer>> getChartAllDaysData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<XYChart.Data<String, Integer>> data = new ArrayList<>();
        for (Date date : totalDays.keySet()) {
            data.add(new XYChart.Data(sdf.format(date), totalDays.get(date)));
        }
        return data;
    }

    public List<PieChart.Data> getChartParticipantsData(){
        List<PieChart.Data> data = new ArrayList<>();
        for (String s : participants.keySet()) {
            data.add(new PieChart.Data(s, getParticipantShare(s)));
        }
        return data;
    }

    public List<PieChart.Data> getChartTimeData(){
        List<PieChart.Data> data = new ArrayList<>();
        for (String s : timeofDay.keySet()) {
            data.add(new PieChart.Data(s, timeofDay.get(s)));
        }
        return data;
    }

    public Dataset getDaysDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Date iterator : days.keySet()) {
            dataset.addValue(days.get(iterator), "Mensajes por dia", sdf.format(iterator));
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
