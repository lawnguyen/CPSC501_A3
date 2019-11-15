import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Sender {
	public static void main(String[] args) {
		Serializer serializer = new Serializer();
		String serverUrl = "localhost";
		int port = 4444;
		
		try {
//			Socket socket = new Socket(serverUrl, port);
//			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			
//			Object1 obj1 = new Object1(4);
//			Object2 obj2 = new Object2(4.20, new Object2b(new Object2()));
//			Object3 obj3 = new Object3(new int[] { 1,2,3,4,5,6,7,8,9,0 });
//			Object4 obj4 = new Object4(new Object1[] { new Object1(42), new Object1(777), new Object1(88) });
//			ArrayList<Object1> al = new ArrayList<Object1>();
//			al.add(new Object1(42)); 
//			al.add(new Object1(777)); 
//			al.add(new Object1(88));
//			Object5 obj5 = new Object5(al);
			
//			serializer.outputXml(serializer.serialize(obj1));
//			serializer.outputXml(serializer.serialize(obj2));
//			serializer.outputXml(serializer.serialize(obj3));
//			serializer.outputXml(serializer.serialize(obj4));
//			serializer.outputXml(serializer.serialize(obj5));
			serializer.outputXml(serializer.serialize(new char[]{ 's','m','i','t','h' }));
			
//			outputStream.writeObject(serializer.serialize(obj2));
//			
//			outputStream.flush();
//			socket.close();
		} catch (/*IO*/Exception e) {
			e.printStackTrace();
		}
	}

}
