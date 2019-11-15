import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public class Receiver {
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		try {
			int port = 4444;
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			Object obj = inputStream.readObject();
			
//	        SAXBuilder builder = new SAXBuilder();
//	        Document doc = builder.build(obj);
//			deserializer.deserialize(doc);
			
			System.out.println(obj.toString());
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
