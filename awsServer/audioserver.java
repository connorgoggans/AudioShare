package tcpserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class AudioServer {

    public static void main(String args[]) {
	ServerSocket ss = null;
	Socket socket = null;
	Socket clientSocket = null;
	try {
	    ss = new ServerSocket(8888); //check port
	    while(true) {
		socket = ss.accept();

		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;
		while((len = is.read()) !=  -1) {
		    baos.write((byte)len);
		}
		baos.flush();

		//The following will be in a for loop in the future depending on how many
		//IP Addresses we're outputting the data to

		InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
		clientSocket = new Socket("localhost", 8888); //will pull IP Address from input stream in future
		OutputStream output = clientSocket.getOutputStream();
		whie((len = is1.read())!= -1) {
		    output.write((byte)len);
		    if (len == '\n') {
			output.flush();
		    }
		}
	    
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (ss != null) {
		try {
		    ss.close();
		    socket.close();
		    clientSocket.close();
		} catch (IOException ignored) {
		}
	    }
	}
    }
}
