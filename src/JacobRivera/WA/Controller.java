package JacobRivera.WA;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

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
                //data.createTotalDaysData();
                //participants.setFill();
                SimpleDateFormat dayF = new SimpleDateFormat("dd-MM-yyyy");
                int i = 0;
                Set<String> partSet = data.getParticipants();
                for (String s : partSet) {
                    myGrid.add(new Label(s),0,i);
                    myGrid.add(new Label(data.getParticipantData(s) + ""),1,i);
                    myGrid.add(new Label(data.getParticipantShare(s) + "%"), 2, i);
                    //avg.setText(data.getParticipantShare(s));
                    i++;

                }
                myGrid2.add(new Label(data.getDailyAvg() + ""),0,1);
                myGrid2.add(new Label(""),1,1);
                myGrid2.add(new Label(dayF.format(data.getMostTalkedDay())),0,3);
                myGrid2.add(new Label(data.getDayData(data.getMostTalkedDay()) + ""),1,3);
                myGrid2.add(new Label(data.getMostTalkedMonth()),0,5);
                myGrid2.add(new Label(data.getMonthData(data.getMostTalkedMonth()) + ""),1,5);
                daysLabel.setText("Días desde que se inició el chat: " );
            }
        });
    }

//    @Override
//    public void handle(ActionEvent actionEvent) {
//        ConversationData data = null;
        /*fileChooser.setTitle("Dato");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            data = openFile(file);
        }

        if (data != null) {
            data.createMonthsData();
            //participants.setFill();
            SimpleDateFormat dayF = new SimpleDateFormat("dd-MM-yyyy");

            Set<String> partSet = data.getParticipants();
            //LinkedList<String> part = new LinkedList<String>();
            totalMessages.setText(data.getTotalMessages());
            for (String s : partSet) {
                name.setText(s);
                msg.setText(data.getParticipantData(s);
                avg.setText(data.getParticipantShare(s));

            }
            Date day = data.getMostTalkedDay();
            String mt = data.getMostTalkedMonth();
            dayTF.setText(dayF.format(day));
            dayMsg.setText(data.getDayData(day)+"");
            dayAvg.setText("Promedio diario: "+data.getDailyAvg()+"");
            monthTF.setText(mt);
            monthMsg.setText(data.getMonthData(mt)+"");

            JFreeChart line = ChartFactory.createLineChart("Mensajes por dia", "Fechas", "mensajes", (CategoryDataset) data.getLinearDataSet());
            int width = 2000; *//* Width of the image *//*
            int height = 1000; *//* Height of the image *//*
            File lineChart = new File( "char.jpeg" );

            SVGGraphics2D g2 = new SVGGraphics2D(2000, 1000);
            g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
            Rectangle r = new Rectangle(0, 0, 2000, 1000);
            line.draw(g2, r);
            File f = new File("char.svg");
            try {
                ChartUtilities.saveChartAsJPEG(lineChart, line, width, height);
                SVGUtils.writeToSVG(f, g2.getSVGElement());
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }*/
    //}

    private ConversationData openFile(File arg) {
        FileReader in = null;
        try{
            in = new FileReader(arg);
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }

        BufferedReader v;
        v = new BufferedReader(in);
        LineAnalyzer analyzer = new LineAnalyzer();
        ConversationData data = new ConversationData();
        String line;
        int i = 0;
        try{
            while ((line = v.readLine()) != null) {
                i++;
                //System.out.println(i);
                if (!analyzer.validLine(line))
                    System.out.println(i + " " + line);
                else
                    data.addData(analyzer.getParticipant(line), analyzer.getMessage(line), analyzer.getDate(line));
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return data;
    }
}
