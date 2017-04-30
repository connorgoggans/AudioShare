package senderClient;
import java.io.InputStream;
import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;

public class threadedReceiver extends Thread{

    Socket s;
    InputStream audio;

    public void run(){
	try {
	    OutputStream out = s.getOutputStream();
	    int len;
	    while((len = audio.read()) != -1){
		out.write((byte)len);
		if(len == '\n'){
		    out.flush();
		}
	    }
	}catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public threadedReceiver(Socket socket, InputStream input){
        this.s = socket;
        this.audio = input;
    }


};
