package grapher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Grapher extends Application {
    

    
@Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
    
        Scene scene = new Scene(root, 1000, 600);
    
        stage.setTitle("Grapher");
        scene.getStylesheets().add("GrapherStyle.css");
        stage.setScene(scene);
        stage.show();
        
    }
    
    

}