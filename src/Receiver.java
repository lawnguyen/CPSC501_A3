import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Receiver {
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		try {
			int port = 4444;
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			Object obj = inputStream.readObject();
			
			Object deserialized = deserializer.deserialize((Document) obj);
//			XMLOutputter xo = new XMLOutputter();
//			xo.setFormat(Format.getCompactFormat());
//			
//			System.out.println(xo.outputString((Document) obj));
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
