import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {
	public static void main(String[] args) {
		Serializer serializer = new Serializer();
		String serverUrl = "localhost";
		int port = 4444;
		
//		try {
//			Socket socket = new Socket(serverUrl, port);
//			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			
//			Object1 obj1 = new Object1();
//			Object2 obj2 = new Object2();
//			Object3 obj3 = new Object3();
//			Object4 obj4 = new Object4();
//			Object5 obj5 = new Object5();
			
//			serializer.outputXml(serializer.serialize(obj1));
//			serializer.outputXml(serializer.serialize(obj2));
//			serializer.outputXml(serializer.serialize(obj3));
//			serializer.outputXml(serializer.serialize(obj4));
//			serializer.outputXml(serializer.serialize(obj5));
			serializer.outputXml(serializer.serialize(new char[]{ 's','m','i','t','h' }));
			
			// outputStream.writeObject("Received object: " + obj);
			
//			outputStream.flush();
//			socket.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
