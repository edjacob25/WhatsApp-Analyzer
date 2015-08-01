package JacobRivera.WA;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane layout;

    @Override
    public void start(final Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Whatsapp Analyzer");

        initLayout();
        initNewLayout();
    }

    public void initNewLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/view1.fxml"));
            AnchorPane ap = (AnchorPane) loader.load();

            layout.setCenter(ap);
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }

    public void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Main.fxml"));
            layout = (BorderPane) loader.load();

            Scene scene = new Scene(layout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
