package senderClient;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class newServer {
	public static void main(String[] args) throws IOException {
		//initialize data
		ServerSocket ss = null;
		InputStream audio = null;
		File file = null;
		ReentrantReadWriteLock fileLock = new ReentrantReadWriteLock();
		
		try {
			ss = new ServerSocket(8888); //create a server socket

			//create thread pool of 10 threads
			ExecutorService p = Executors.newFixedThreadPool(10);
			Socket socket = ss.accept();
			//accept and handle the hosting connection
			//try {
				//if the socket is connected send the file
			    //if(socket.isConnected()) {
				    //audio = new BufferedInputStream(socket.getInputStream());
			Thread send = new threadedSender(file, audio, socket, fileLock);
				    p.execute(send);
				    System.out.println(file);
				    //temporary file to store data in
				    //				    	file = new File("audioFile.wav");
				    //	byte[] bytes = new byte[2048];
				    //	audio = new BufferedInputStream(socket.getInputStream());
				    //	OutputStream out = new FileOutputStream(file);
				    //	int count;
				    //	//write out data to the file
					//		while ((count = audio.read(bytes)) >0) {
					//	out.write(bytes,0,count);
				    	//}
				   	//out.close();
				    //}
				    //}catch(IOException e){
				    //e.printStackTrace();
				    //}

			//create thread pool of 10 threads
			//ExecutorService p = Executors.newFixedThreadPool(10);

			//loop and create worker threads for connections
			while(true) {
				System.out.println("Waiting...");
				Socket s = ss.accept();
				System.out.println("Rcv wait 1");
				Thread w = new threadedReceiver(s, file, fileLock);
				//handle the streaming to clients in individual threads
				System.out.println("Rcv wait 2");
				p.execute(w);
				
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
