import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {
	
	public static void main(String[] args) {
		try {
			int port = 4444;
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			Object obj = inputStream.readObject();
			System.out.println(obj.toString());
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
