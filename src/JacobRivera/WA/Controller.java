package JacobRivera.WA;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

public class Controller implements EventHandler<ActionEvent>{

    @Override
    public void handle(ActionEvent actionEvent) {
        ConversationData data = null;
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
                avg.setText(data.getParticipantAverage(s));

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
    }

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
