/*package grapher;
	

import java.util.ArrayList;
import jssc.SerialPort; 
import jssc.SerialPortException;
public class SerialTest{
public static ArrayList<String> tograph = new ArrayList<String>();
    public static void main(String[] args) {
SerialPort serialPort = new SerialPort("/dev/tty/acm0");
    try {
        serialPort.openPort();//Open serial port
        serialPort.setParams(9600, 8, 1, 0);//Set params.
        byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
        serialPort.closePort();//Close serial port
    }
    catch (SerialPortException ex) {
        System.out.println(ex);
    }
    }

}*/