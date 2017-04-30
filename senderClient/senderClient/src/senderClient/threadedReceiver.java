package senderClient;
import java.io.InputStream;
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
        FileInputStream in = new FileInputStream(audio);

        byte buffer[] = new byte[2048];
        int count;
        count = in.read(buffer);
        while(count >= 0){
        		System.out.print(count + " ");
            	out.write(buffer,0,count);
            	count = in.read(buffer);
        }
	    }catch (IOException e) {
		e.printStackTrace();
	    }
	}

	public threadedReceiver(Socket socket, File input){
		this.s = socket;
		this.audio = input;
	}


};
