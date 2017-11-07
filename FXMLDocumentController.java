package grapher;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortEvent;
import jssc.SerialPortList;
import java.util.Hashtable;
import java.util.stream.Stream;
import javafx.scene.control.ScrollBar;
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
@FXML
CheckBox toggle;
@FXML
ScrollBar bar;
Scene scene;
public int test = 0;
Integer width = 800;
Integer height = 500;
String port = new String();
SerialPort serialPort;
 XYChart.Series<Number, Number>[] series = Stream.<XYChart.Series<Number, Number>>generate(XYChart.Series::new).limit(16).toArray(XYChart.Series[]::new);
 Hashtable<Integer, CheckBox> check
 	= new Hashtable<Integer, CheckBox>();
 StringBuilder values = new StringBuilder();
 StringBuilder bs = new StringBuilder();
 boolean receivingMessage = true;
 ArrayList <Double> tograph = new ArrayList(); //CHANGE TO DOUBLE
double maxtime = 900;
File graph = new File("graph.txt");
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
    	try{
    	for(int i=0; i<15; i++){
    	if(check.get(i).isSelected())
    	chart.getData().add(series[i]);
    	else{
        	chart.getData().remove(series[i]);
    	}
	}
	}catch(NullPointerException e){}
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
serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
  	if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
                	try {             
                   	byte buffer[] = serialPort.readBytes();
                   	for (byte b: buffer) {
            	if (b == '(') {
                	receivingMessage = true;
                	values.setLength(0);
                	tograph.clear();
            	}
            	else if (receivingMessage == true) {
                	if (b == ')') {
                    	receivingMessage = false;
                    	try{
while(1==1){
                                    
                               	if(values.indexOf(",")==-1){
                                  	tograph.add(Double.parseDouble(values.toString().trim()));
                                   	break;
                                	}
                               	tograph.add(Double.parseDouble(values.substring(0 , values.indexOf(","))));
                               	values.delete(0, values.indexOf(",")+1);
                               	}}catch(NumberFormatException e){}
                                //print out whats read

                                 
                    	Platform.runLater(new Runnable() {
                        	@Override public void run() {
                                    System.out.println(tograph);
                                try{
                                                               if(tograph.size()==17){
                                                               bs.append(tograph);
                        	for(int i=1; i<17; i++){
                                series[i-1].getData().add(new XYChart.Data(tograph.get(0), tograph.get(i)));     
                        	}
                           	}
                                
                                                       	if(toggle.isSelected()){
                                                               maxtime = tograph.get(0);
                           	xAxis.setUpperBound(bar.getMax() * (maxtime)/100+100);
                           	xAxis.setLowerBound(bar.getMax() * (maxtime)/100-800);
                       	}
                       	else{
                        	xAxis.setUpperBound(bar.getValue() * (maxtime)/100+100);
                       	xAxis.setLowerBound(bar.getValue() * (maxtime)/100-800);
                       	}     
                       	}catch(IndexOutOfBoundsException e){}
                       	}
                    	});
                	}
                	else {
                    	values.append((char)b);
                	}
            	}
        	}                

                	} catch (SerialPortException ex) {

                	}
  	}
        	});

}catch(SerialPortException ex){}
    	try {
        	Thread.sleep(100);
     	} catch (Exception e) {
        	System.out.println(e);
     	}
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
        try{
            FileWriter filewrite = new FileWriter(graph);
            filewrite.write(bs.toString());
            filewrite.close();
        }catch(IOException e){
        }
}
  
	}
