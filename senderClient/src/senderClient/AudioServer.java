package senderClient;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;

public class AudioServer {
	public static void main(String[] args) throws IOException {
		//initialize data
		ServerSocket ss = null;
		File file = null;
		ReentrantReadWriteLock fileLock = new ReentrantReadWriteLock();
		BufferedReader buffIn;

		try {
			ss = new ServerSocket(8888); //create a server socket

			//create thread pool of 10 threads
			ExecutorService p = Executors.newFixedThreadPool(10);
			file = new File("audioFile");

			//loop and create worker threads for connections
			while(true) {

				if(file == null) {
				}
				else {
					Socket s = ss.accept();
					
					buffIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
					
					//read if it's sender or receiver
					String line = buffIn.readLine();
					Thread w;
					
					if (line.equals("sender")) {
						w = new threadedSender(s, file, fileLock);
					}
					else {
						w = new threadedReceiver(s, file, fileLock);
					}//handle the streaming to clients in individual threads
					
					p.execute(w);
				}

			}

		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if (ss != null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
