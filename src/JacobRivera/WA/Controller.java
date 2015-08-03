package JacobRivera.WA;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.font.scale.LinearFontScalar;
import wordcloud.nlp.FrequencyAnalizer;
import wordcloud.palette.ColorPalette;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Controller{

    @FXML
    private Button myButton;
    @FXML
    private GridPane myGrid;
    @FXML
    private GridPane myGrid2;
    @FXML
    private LineChart daysGraph;
    @FXML
    private LineChart allDaysGraph;
    @FXML
    private PieChart percentageGraph;
    @FXML
    private PieChart hoursGraph;
    @FXML
    private ImageView words;

    @FXML
    private void initialize() {
        myButton.setOnAction((event) -> {
            ConversationData data = null;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Dato");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos", "*.txt"));
            File file = fileChooser.showOpenDialog(myButton.getScene().getWindow());
            if (file != null) {
                data = openFile(file);
            }

            if (data != null) {
                data.createMonthsData();
                data.createTotalDaysData();
                SimpleDateFormat dayF = new SimpleDateFormat("dd-MM-yyyy");

                /* Remove data from old operation*/
                myGrid.getChildren().remove(0,myGrid.getChildren().size());

                /* Add new data*/
                Set<String> partSet = data.getParticipants();
                int i = 0;
                for (String s : partSet) {
                    myGrid.add(new Label(s),0,i);
                    myGrid.add(new Label(data.getParticipantCount(s) + ""),1,i);
                    myGrid.add(new Label(String.format("%.2f", data.getParticipantShare(s)) + "%"), 2, i);
                    myGrid.add(new Label(String.format("%.2f", data.getWordsAvg(s))), 3, i);
                    i++;
                }

                /* Remove data from old operation */
                myGrid2.getChildren().remove(8, myGrid2.getChildren().size());

                /* Add new Data */
                myGrid2.add(new Label(data.getDaysTalked() + ""), 0, 1);
                myGrid2.add(new Label(data.getTotalMessages() + ""), 1, 1);
                myGrid2.add(new Label(data.getDailyAvg() + ""),0,3);
                myGrid2.add(new Label(data.getTotalDailyAvg() + ""),1,3);
                myGrid2.add(new Label(dayF.format(data.getMostTalkedDay())),0,5);
                myGrid2.add(new Label(data.getDayData(data.getMostTalkedDay()) + ""), 1, 5);
                myGrid2.add(new Label(data.getMostTalkedMonth()), 0, 7);
                myGrid2.add(new Label(data.getMonthData(data.getMostTalkedMonth()) + ""), 1, 7);

                createCharts(data);

                createWordCloud(data, file.getName());
            }
        });
    }

    private ConversationData openFile(File arg) {
        BufferedReader bf = null;
        try{
            bf = new BufferedReader (new InputStreamReader (new FileInputStream (arg), "utf-8"));
        }
        catch (Exception e){
            System.err.println(e);
        }

        LineAnalyzer analyzer = new LineAnalyzer();
        ConversationData data = new ConversationData();
        String line;
        int i = 0;
        try{
            while ((line = bf.readLine()) != null) {
                i++;
                if (!analyzer.validLine(line))
                    System.out.println(i + " " + line);
                else
                    data.addData(analyzer.getParticipant(line), analyzer.getMessage(line), analyzer.getDate(line), analyzer.getTime(line));
            }
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
        return data;
    }

    private void createCharts(ConversationData data){
        daysGraph.setTitle("Mensajes por d\u00EDa");
        daysGraph.getData().remove(0,daysGraph.getData().size());
        XYChart.Series days = new XYChart.Series();
        days.setName("Mensajes");
        for (XYChart.Data<String, Integer> integerData : data.getChartDaysData()) {
            days.getData().add(integerData);
        }
        daysGraph.getData().add(days);


        allDaysGraph.setTitle("Mensajes por d\u00EDa(incluyendo d\u00EDas sin hablar)");
        allDaysGraph.getData().remove(0, allDaysGraph.getData().size());
        XYChart.Series allDays = new XYChart.Series();
        allDays.setName("Mensajes");
        for (XYChart.Data<String, Integer> integerData : data.getChartAllDaysData()) {
            allDays.getData().add(integerData);
        }
        allDaysGraph.getData().add(allDays);


        percentageGraph.setTitle("Porcentaje de mensajes");
        percentageGraph.getData().remove(0, percentageGraph.getData().size());
        for (PieChart.Data data1 : data.getChartParticipantsData()) {
            percentageGraph.getData().add(data1);
        }


        hoursGraph.setTitle("Tiempo del d\u00EDa");
        hoursGraph.getData().remove(0, hoursGraph.getData().size());
        for (PieChart.Data data1 : data.getChartTimeData()) {
            hoursGraph.getData().add(data1);
        }
    }

    private void createExternalCharts(ConversationData data){
        JFreeChart days = ChartFactory.createLineChart("Mensajes por dia","Fechas","mensajes", (CategoryDataset) data.getDaysDataSet());
        JFreeChart totaldays = ChartFactory.createLineChart("Mensajes por dia entre", "Fechas", "mensajes", (CategoryDataset) data.getTotalDaysDataSet());
        int width = 2000; /* Width of the image */
        int height = 1000; /* Height of the image */
        File daysChart = new File( "dias.jpeg" );
        File todosChart = new File( "todosdias.jpeg" );

        try {
            ChartUtilities.saveChartAsJPEG(daysChart, days, width, height);
            ChartUtilities.saveChartAsJPEG(todosChart, totaldays, width, height);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    private void createWordCloud(ConversationData data, String name){
        name = name.split("\\.")[0];
        System.out.println(name);
        File image = new File(name + ".png");

        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(100);
        frequencyAnalizer.setMinWordLength(4);
        List<String> texts = data.getMessagesContent();
        List<WordFrequency> wordFrequencies = frequencyAnalizer.load(texts);

        final WordCloud wordCloud = new WordCloud(400, 400, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new CircleBackground(200));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));
        wordCloud.setColorPalette(buildRandomColorPallete(20));
        wordCloud.setFontScalar(new LinearFontScalar(30, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(name + ".png");

        words.setImage(new Image("file:" + name + ".png"));

        image.delete();
    }

    private static ColorPalette buildRandomColorPallete(int n) {
        Random random = new Random();
        final Color[] colors = new Color[n];
        for(int i = 0; i < colors.length; i++) {
            colors[i] = new Color(random.nextInt(230) + 25, random.nextInt(230) + 25, random.nextInt(230) + 25);
        }
        return new ColorPalette(colors);
    }
}
