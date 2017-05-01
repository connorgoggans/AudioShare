package senderClient;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;

public class newServer {
	public static void main(String[] args) throws IOException {
		//initialize data
		ServerSocket ss = null;
		InputStream audio = null;
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
				//System.out.println("Waiting...");
				Socket s = ss.accept();
				buffIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String line = buffIn.readLine();
				System.out.println(line);
				System.out.println(file);
				Thread w;
				if (line.equals("sender")) {
				    System.out.println("in sender");
				     w = new threadedSender(file, s, fileLock);
				}
			        else {
				    System.out.println("in rcver");
				    w = new threadedReceiver(s, file, fileLock);
				}//handle the streaming to clients in individual threads
				System.out.println("Rcv wait 2");
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
