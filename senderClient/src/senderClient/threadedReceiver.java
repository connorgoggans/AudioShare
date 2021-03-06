package senderClient;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class threadedReceiver extends Thread{

	Socket s;
	File audio;
	ReentrantReadWriteLock fileLock;

	public void run() {

		try {
			OutputStream out = s.getOutputStream();
			fileLock.readLock().lock();
			System.out.println("Playing file...");
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
			fileLock.readLock().unlock();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	//threaded receiver constructor
	public threadedReceiver(Socket socket, File input, ReentrantReadWriteLock lock){
		this.s = socket;
		this.audio = input;
		this.fileLock = lock;
	}


};
