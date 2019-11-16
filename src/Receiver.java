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
		Inspector inspector = new Inspector();
		int port = 4444;
		
		try {
			// Accept socket connection 
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			
			// Consume incoming Object
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			Object obj = inputStream.readObject();
			
			// Show incoming Object
			XMLOutputter xo = new XMLOutputter();
			xo.setFormat(Format.getPrettyFormat());
			System.out.println("Incoming XML document:");
			System.out.println(xo.outputString((Document) obj));
			
			// Deserialize Object
			Object deserialized = deserializer.deserialize((Document) obj);
			System.out.println("\nObject deserialization finished");
			
			// Inspect Object
			System.out.println("\nInspecting deserialized object...");
			inspector.inspect(deserialized, true);

			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
