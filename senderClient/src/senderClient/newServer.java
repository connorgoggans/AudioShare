package senderClient;
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
		
		try {
			ss = new ServerSocket(8888); //create a server socket

			//create thread pool of 10 threads
			ExecutorService p = Executors.newFixedThreadPool(10);
			
			//accept and handle the hosting connection
			try(Socket socket = ss.accept()) {
				//if the socket is connected send the file
				if(socket.isConnected()) {
				    audio = new BufferedInputStream(socket.getInputStream());
		     		    Thread w = threadedSender.getSender(file, audio);
				    p.execute(w);
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
				}
			}

			//create thread pool of 10 threads
			//ExecutorService p = Executors.newFixedThreadPool(10);

			//loop and create worker threads for connections
			while(true) {
				System.out.println("Waiting...");
				Socket s = ss.accept();
				Thread w = new threadedReceiver(s, file);
				//handle the streaming to clients in individual threads
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
