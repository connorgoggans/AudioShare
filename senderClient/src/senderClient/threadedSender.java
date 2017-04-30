package senderClient;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;

public class threadedSender extends Thread {

	File file;
	InputStream audio;
	Socket socket;
	ReentrantReadWriteLock fileLock;

	public void run() {

		try {
			boolean connect = socket.isConnected();
			System.out.println(connect);
			System.out.println("Sending file...");
			fileLock.writeLock().lock();
			//    file = new File("audioFile.wav");
			byte[] bytes = new byte[2048];
			audio = new BufferedInputStream(socket.getInputStream());
			OutputStream out = new FileOutputStream(file);
			int count;
			//write out data to the file
			while ((count = audio.read(bytes)) >0) {
				out.write(bytes,0,count);
			}
			out.close();
			fileLock.writeLock().unlock();
			System.out.println(file);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public threadedSender(File f, InputStream a, Socket s, ReentrantReadWriteLock l) {
		this.file = f;
		this.audio = a;
		this.socket = s;
		this.fileLock = l;
	}

	//public static threadedSender getSender(File f, InputStream a) {
	//	if (instance == null) {
	//	    instance = new threadedSender();
	//	    instance.file = f;
	//	    instance.audio = a;
	//instance.socket = s;
	//	}
	//	return instance;
	//}

};
