package senderClient;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class newServer {
    public static void main(String[] args) throws IOException {
	ServerSocket ss = null;
	InputStream audio = null;
	File file = null;
	try {
	    ss = new ServerSocket(8888);
	    try(Socket socket = ss.accept()) {
		if(socket.isConnected()) {
		    file = new File("audioFile.wav");
		    byte[] bytes = new byte[16*1024];
		    audio = new BufferedInputStream(socket.getInputStream());
		    OutputStream out = new FileOutputStream(file);
		    int count;
		    while ((count = audio.read(bytes)) >0) {
			out.write(bytes,0,count);
			System.out.print(count + " ");
		    }
		}
	    }

	    //create thread pool of 10 threads
	    ExecutorService p = Executors.newFixedThreadPool(10);

	    //loop and create worker threads for connections
	    while(true) {
		System.out.println("Waiting...");
		Socket s = ss.accept();
		Thread w = new threadedReceiver(s, file);
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
