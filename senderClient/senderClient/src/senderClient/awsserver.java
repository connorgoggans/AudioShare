package senderClient;
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
import java.io.BufferedInputStream;
import java.io.*;
import java.net.*;

public class awsserver {

    public static void main(String args[]) {
		//create server instance
		ServerSocket ss = null;
		try{
			ss = new ServerSocket(8888);

			//read in audio on first connected client
			InputStream audio =null;
			File audioFile = new File("audioFile.wav");
			try (Socket socket = ss.accept()) { //localhost, later sub for AWS IP
			    System.out.println("hey it's the sender!");
				if (socket.isConnected()) {
				    audio = new BufferedInputStream(socket.getInputStream());
				    byte[] buffer = new byte[2048];
				    OutputStream outStream = new FileOutputStream(audioFile);
				    int current = 0;
				    int bytesRead = audio.read(buffer, 0, buffer.length);
				    while(bytesRead>-1) {
				       bytesRead = audio.read(buffer, current, (buffer.length-current));
				       outStream.write(buffer);
				       if(bytesRead >= 0) {
				       }
				       System.out.print(audio.available() + " ");
				    }
				    System.out.println("done");
				}
			}

			//create thread pool of 10 threads
			ExecutorService p = Executors.newFixedThreadPool(10);

			//loop and create worker threads for connections
			while(true){
			    System.out.println("Waiting...");
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
