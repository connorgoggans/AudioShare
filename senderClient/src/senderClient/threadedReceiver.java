package senderClient;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class threadedReceiver extends Thread{

	Socket s;
	File audio;
    ReentrantReadWriteLock fileLock;

	public void run() {
		while(true){
			try {
				OutputStream out = s.getOutputStream();
				fileLock.readlock().lock();
				FileInputStream in = new FileInputStream(audio); //input stream for audio file

				byte buffer[] = new byte[2048];
				int count;
				count = in.read(buffer);
				
				//write out audio to client
				while(count >= 0){ 
					out.write(buffer,0,count);
					count = in.read(buffer);
				}
				
				in.close();
				fileLock.readlock().unlock();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//threaded receiver constructor
    public threadedReceiver(Socket socket, File input, ReentrantReadWriteLock lock){
		this.s = socket;
		this.audio = input;
		this.fileLock = lock;
	}


};
