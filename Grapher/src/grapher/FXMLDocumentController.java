/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//ASFGHGFSADFSHRGFSDF
package grapher;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortEvent;
import jssc.SerialPortList;
import java.util.Hashtable;
public class FXMLDocumentController {
@FXML
 public LineChart<Number, Number> chart;
@FXML
 NumberAxis xAxis;
@FXML
 NumberAxis yAxis;
@FXML
 ComboBox com;
@FXML
CheckBox chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9, chk10, chk11, chk12, chk13, chk14, chk15, chk16;

Scene scene;
public int test = 0;
Integer width = 1000;
Integer height = 500;   
String port = new String();
SerialPort serialPort;
 Series<Number, Number>[] series = new Series[15];
 Hashtable<Integer, CheckBox> check
     = new Hashtable<Integer, CheckBox>();
 
 public void initialize(){   
     chart.setPrefWidth(width);
      chart.setPrefHeight(height);
      xAxis.setUpperBound(900);
      yAxis.setUpperBound(1050);
       String[] portNames = SerialPortList.getPortNames();
       for(int i=0; i<portNames.length; i++){
        com.getItems().add(portNames[i]);
       }
        check.put(0, chk1); check.put(1, chk2); check.put(2, chk3); check.put(3, chk4); check.put(4, chk5); check.put(5, chk6); check.put(6, chk7); check.put(7, chk8); check.put(8, chk9); check.put(9, chk10); check.put(10, chk11); check.put(11, chk12); check.put(12, chk13); check.put(13, chk14); check.put(14, chk15); check.put(15, chk16);
       
}
public void select(){
            port = com.getValue().toString();
            serialPort = new SerialPort(port);
}

public void open(){

    try {
        serialPort.openPort();//Open serial port
        serialPort.setParams(9600, 8, 1, 0);//Set params.
    }
    catch (SerialPortException ex) {
        System.out.println(ex);
    }
}

public void close(){
    try {
        serialPort.closePort();//Close serial port
    }
    catch (SerialPortException ex) {
        System.out.println(ex);
    }
}

public void graph(){ 
try{
    /*for(int i=0; i<15; i++){
        if(check.get(i).isSelected())
        chart.getData().add(series[i]);
        else{
            chart.getData().remove(series[i]);
        }
    }*/
serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
      if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
                    try {
                        StringBuilder values = new StringBuilder();
                       String st = serialPort.readString();
                       values.append(st);
                       ArrayList<Integer> tograph = new ArrayList<Integer>();
                        //Update label in ui thread
                        
                      /*while (1==1){
                           if(values.length()>0){
                            if(values.indexOf(",") == -1) {
                                tograph.add(Integer.parseInt(values.toString()));
                                break;
                            }
                            tograph.add(Integer.parseInt(values.substring(0, values.indexOf(",") - 1)));
                            values.delete(0, values.indexOf(","));
                           }
                        }*/
                        System.out.println(values);
                           
                        Platform.runLater(() -> {
                           /* for(int i=0; i<15; i++){
                                series[i].getData().add(new XYChart.Data(tograph.get(0), tograph.get(i+1)));
                            }*/
                        });
                        
                    } catch (SerialPortException ex) {

                    }
      }
            });

}catch(SerialPortException ex){}
}
   public void zin(){
            width = width*5/4;
      height = height*5/4;
      chart.setPrefWidth(width);
      chart.setPrefHeight(height);    
   }
   
  public void zout(){
width = width*4/5;
height = height*4/5;
     chart.setPrefWidth(width);
      chart.setPrefHeight(height);
  }
    public void stopgraph(){

    }
public void save() {
    WritableImage image = chart.snapshot(new SnapshotParameters(), null);

    File file = new File("chart.png");

    try {
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    } catch (IOException e) {
        // TODO: handle exception here
    }
}
  
    }