package senderClient;

import java.io.*;
import java.net.*;

public class AudioServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            throw new IllegalArgumentException("Expected Sound File");
        File soundFile = new File(args[0]);

        System.out.println("server: " + soundFile);

        /*
        try (ServerSocket serverSocker = new ServerSocket(8888); //New Socket
            FileInputStream in = new FileInputStream(soundFile)) {
            if (serverSocker.isBound()) { //if connected, write the file to the output
                Socket client = serverSocker.accept();
                OutputStream out = client.getOutputStream();

                byte buffer[] = new byte[2048];
                int count;
                while ((count = in.read(buffer)) != -1)
                    out.write(buffer, 0, count);
            }
        }
        */

        Socket s = new Socket("34.200.251.30", 8888);
        OutputStream out = s.getOutputStream();
        FileInputStream in = new FileInputStream(soundFile);
        BufferedInputStream buf = new BufferedInputStream(in);
   
        byte buffer[] = new byte[2048];
        int count;
        count = in.read(buffer);
        while(count >= 0){
        		System.out.print(count + " ");
            	out.write(buffer,0,count);
            	count = in.read(buffer);	
        }
        
        /*
        int count;
        while((count = buf.available()) > 0){
        	byte[] buffer = new byte[2048];
        	in.read(buffer, 0, count);
        	out.write(buffer, 0, count);
        }
        
        byte[] buffer = new byte[(int)soundFile.length()];
        buf.read(buffer, 0, buffer.length);
        out.write(buffer, 0, buffer.length);
        out.flush();
        System.out.println("Done");
        buf.close();
        out.close();
        s.close();
		*/
        System.out.println("Done");
    }
}