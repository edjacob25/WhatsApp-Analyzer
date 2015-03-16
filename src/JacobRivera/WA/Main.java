package JacobRivera.WA;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception{
        GridPane root = new GridPane();
        Button btn = new Button();
        Text pptTitle = new Text("PARTICIPANTES");
        Text msgTitle = new Text("MENSAJES");
        Text dayTitle = new Text("DIAS");
        Text monthTitle = new Text("MES");

        final Text participant1TF = new Text();
        final Text participant2TF = new Text();

        final Text msgs = new Text();

        final Text msg1 = new Text();
        final Text msg2 = new Text();

        final Text avg1 = new Text();
        final Text avg2 = new Text();

        final Text dayTF = new Text();
        final Text dayMsg = new Text();
        final Text dayAvg = new Text();

        final Text monthTF = new Text();
        final Text monthMsg = new Text();

        Text title = new Text("Analizador de WhatsApp");
        final FileChooser fileChooser = new FileChooser();

        btn.setText("Archivo a analizar");
        btn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        ConversationData data = null;

                        fileChooser.setTitle("Dato");
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos", "*.txt"));
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            data = openFile(file);
                        }

                        if (data != null) {
                            data.createMonthsData();
                            //participants.setFill();
                            SimpleDateFormat dayF = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat mtF = new SimpleDateFormat("yyyy-MM");
                            String[] part = data.getParticipants();
                            participant1TF.setText(part[0]);
                            participant2TF.setText(part[1]);
                            msgs.setText(data.getTotalMessages()+"");
                            msg1.setText(data.getParticipantData(part[0])+"");
                            msg2.setText(data.getParticipantData(part[1])+"");
                            avg1.setText(data.getParticipantAverage(part[0])+"");
                            avg2.setText(data.getParticipantAverage(part[1])+"");

                            Date day = data.getMostTalkedDay();
                            String mt = data.getMostTalkedMonth();
                            dayTF.setText(dayF.format(day));
                            dayMsg.setText(data.getDayData(day)+"");
                            dayAvg.setText(data.getDailyAvg()+"");
                            monthTF.setText(mt);
                            monthMsg.setText(data.getMonthData(mt)+"");

                            JFreeChart line = ChartFactory.createLineChart("Mensajes por dia","LOl","algo", (CategoryDataset) data.getLinearDataSet());
                            int width = 2000; /* Width of the image */
                            int height = 1000; /* Height of the image */
                            File lineChart = new File( "char.jpeg" );

                            SVGGraphics2D g2 = new SVGGraphics2D(600, 400);
                            g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
                            Rectangle r = new Rectangle(0, 0, 600, 400);
                            line.draw(g2, r);
                            File f = new File("char.svg");
                            try {
                                ChartUtilities.saveChartAsJPEG(lineChart, line, width, height);
                                SVGUtils.writeToSVG(f, g2.getSVGElement());
                            }
                            catch (IOException e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
        );

        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25,25,25,25));

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        title.setId("title");
        root.add(title, 0, 0, 2, 1);

        root.add(btn,0,1,2,1);

        pptTitle.setId("smallTitle");
        msgTitle.setId("smallTitle");
        dayTitle.setId("smallTitle");
        monthTitle.setId("smallTitle");

        participant1TF.setId("result");
        participant2TF.setId("result");
        msgs.setId("result");
        msg1.setId("result");
        msg2.setId("result");
        avg1.setId("result");
        avg2.setId("result");
        dayTF.setId("result");
        dayMsg.setId("result");
        dayAvg.setId("result");
        monthTF.setId("result");
        monthMsg.setId("result");

        root.add(pptTitle,0,2,2,1);
        root.add(msgTitle,0,4,2,1);
        root.add(dayTitle,0,8,2,1);
        root.add(monthTitle,0,11,2,1);


        root.add(participant1TF,0,3);
        root.add(participant2TF,1,3);

        root.add(msgs,0,5,2,1);

        root.add(msg1,0,6);
        root.add(msg2,1,6);

        root.add(avg1,0,7);
        root.add(avg2,1,7);

        root.add(dayTF,0,9);
        root.add(dayMsg,1,9);

        root.add(dayAvg,0,10,2,1);

        root.add(monthTF,0,12);
        root.add(monthMsg,1,12);

        Scene scene = new Scene(root, 500, 450);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Analizador de Whatsapp");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        LineAnalyzer analizer = new LineAnalyzer();
        ConversationData data = new ConversationData();
        String line;
        int i = 0;
        try{
            while ((line = v.readLine()) != null) {
                i++;
                //System.out.println(i);
                if (!analizer.validLine(line))
                    System.out.println(i + " " + line);
                else
                    data.addData(analizer.getParticipant(line),analizer.getMessage(line),analizer.getDate(line));
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return data;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
