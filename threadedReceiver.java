import java.io.InputStream;
import java.net.Socket;

import org.omg.CORBA_2_3.portable.OutputStream;

public class threadedReceiver extends Thread{

    Socket s;
    InputStream audio;

    public void run(){
        OutputStream out = s.getOutputStream();
        while(len = audio.read() != -1){
            out.write((byte)len);
            if(len == "\n"){
                out.flush();
            }
        }
    }

    public threadedReceiver(socket, input){
        this.s = socket;
        this.audio = input;
    }


};