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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class audioserver {

    public static void main(String args[]) {
		//create server instance
		ServerSocket ss = null;
		try{
			ss = new ServerSocket(8888);

			//read in audio on first connected client
			InputStream audio;
			try (Socket socket = ss.accept()) { //localhost, later sub for AWS IP
				if (socket.isConnected()) {
					audio = new BufferedInputStream(socket.getInputStream());
				}
			}

			//create thread pool of 10 threads
			ExecutorService p = Executors.newFixedThreadPool(10);

			//loop and create worker threads for connections
			while(true){
				Socket s = ss.accept();
				Thread w;
				w = new threadedReceiver(s, audio);
				p.execute(w);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(ss != null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
