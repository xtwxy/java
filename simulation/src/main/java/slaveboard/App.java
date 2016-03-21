package slaveboard;

import java.io.IOException;

import slaveboard.ctrlport.ControlPort;
import slaveboard.serialport.SerialPort;

public class App {
	public static void main(String[] args) throws IOException {
		ControlPort ctrlPort = new ControlPort(10005);
		ctrlPort.start();
		
		for(int i = 1; i < 5; ++i) {
			SerialPort port = new SerialPort(10000 + i);
			port.start();
		}
	}
}