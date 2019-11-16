import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Serializer serializer = new Serializer();
		String serverUrl;
		int port;
		
		// Get socket information
		System.out.println("Enter the server URL of your receiver: ");
		serverUrl = scanner.nextLine();
		System.out.println("Enter the port number: ");
		port = scanner.nextInt();
		
		// Create the object
		ObjectCreator creator = new ObjectCreator(scanner);
		Object obj = creator.createObject();
		
		try {
			Socket socket = new Socket(serverUrl, port);
			if (socket.isConnected()) {
				System.out.println("\nConnection has been established");
			}
			
			// Serialize
			Document doc = serializer.serialize(obj);
			
			// Show what will be sent
			XMLOutputter xo = new XMLOutputter();
			xo.setFormat(Format.getPrettyFormat());
			System.out.println("\nThe following XML will be sent over the network:");
			System.out.println(xo.outputString(doc));
			
			// Send serialized object over network
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(doc);
			System.out.println("\nObject sent");

			outputStream.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
