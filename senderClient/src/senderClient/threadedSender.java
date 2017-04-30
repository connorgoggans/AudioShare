package senderClient;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;

public class threadedSender extends Thread {

    private static threadedSender instance = null;
    File file = null;
    InputStream audio = null;
    //Socket socket = null;
    
    public void run() {

	try {
	    System.out.println("Sending file...");
	    file = new File("audioFile.wav");
	    byte[] bytes = new byte[2048];
	    //	    audio = new BufferedInputStream(socket.getInputStream());
	    OutputStream out = new FileOutputStream(file);
	    int count;
	    //write out data to the file
	    while ((count = audio.read(bytes)) >0) {
		out.write(bytes,0,count);
	    }
	    out.close();
	}catch(IOException e) {
	    e.printStackTrace();
	}
    }

    private threadedSender() {
    }

    public static threadedSender getSender(File f, InputStream a) {
	if (instance == null) {
	    instance = new threadedSender();
	    instance.file = f;
	    instance.audio = a;
	    //instance.socket = s;
	}
	return instance;
    }

};
