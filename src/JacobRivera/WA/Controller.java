package JacobRivera.WA;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Set;

public class Controller{

    @FXML
    private Button myButton;
    @FXML
    private GridPane myGrid;
    @FXML
    private GridPane myGrid2;
    @FXML
    private Label daysLabel;

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
                int i = 0;
                Set<String> partSet = data.getParticipants();
                for (String s : partSet) {
                    myGrid.add(new Label(s),0,i);
                    myGrid.add(new Label(data.getParticipantCount(s) + ""),1,i);
                    myGrid.add(new Label(String.format("%.2f", data.getParticipantShare(s) ) + "%"), 2, i);
                    i++;

                }
                myGrid2.add(new Label(data.getDailyAvg() + ""),0,1);
                myGrid2.add(new Label(data.getTotalDailyAvg() + ""),1,1);
                myGrid2.add(new Label(dayF.format(data.getMostTalkedDay())),0,3);
                myGrid2.add(new Label(data.getDayData(data.getMostTalkedDay()) + ""),1,3);
                myGrid2.add(new Label(data.getMostTalkedMonth()),0,5);
                myGrid2.add(new Label(data.getMonthData(data.getMostTalkedMonth()) + ""), 1, 5);
                daysLabel.setText("Dias desde que se inicio el chat: " + data.getDaysTalked() );

                JFreeChart days = ChartFactory.createLineChart("Mensajes por dia","Fechas","mensajes", (CategoryDataset) data.getDaysDataSet());
                JFreeChart totaldays = ChartFactory.createLineChart("Mensajes por dia", "Fechas", "mensajes", (CategoryDataset) data.getTotalDaysDataSet());
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
        });
    }


    private ConversationData openFile(File arg) {
        BufferedReader bf = null;
        try{
            bf = new BufferedReader (new InputStreamReader (new FileInputStream (arg), "utf-8"));
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
        catch (Exception e){

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
                    data.addData(analyzer.getParticipant(line), analyzer.getMessage(line), analyzer.getDate(line));
            }
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
        return data;
    }
}
