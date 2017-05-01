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
		OutputStream out=null;
		try {
			while(true) {
				audio = new BufferedInputStream(socket.getInputStream());
				
				while(audio.available()==0); //wait until data is available
				
				System.out.println("Sending file...");
				
				fileLock.writeLock().lock();
				
				byte[] bytes = new byte[2048];
				
				out = new FileOutputStream(file); //file output stream
				
				//num of bytes read
				int count;
				
				while ((count = audio.read(bytes)) >0) {
					out.write(bytes,0,count);
				}
				
				out.flush();
				fileLock.writeLock().unlock();
			}

		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public threadedSender(Socket s, File f, ReentrantReadWriteLock l) {
		this.file = f;
		this.socket = s;
		this.fileLock = l;
	}

};
