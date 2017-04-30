package senderClient;
import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class threadedReceiver extends Thread{

	Socket s;
	File audio;

	public void run() {
		try {
			OutputStream out = s.getOutputStream();
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
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	//threaded receiver constructor
	public threadedReceiver(Socket socket, File input){
		this.s = socket;
		this.audio = input;
	}


};
