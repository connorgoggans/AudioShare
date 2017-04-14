package senderClient;

import java.io.*;
import java.net.*;

public class AudioServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            throw new IllegalArgumentException("Expected Sound File");
        File soundFile = new File(args[0]);

        System.out.println("server: " + soundFile);

        try (ServerSocket serverSocker = new ServerSocket(6666); //New Socket
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

        System.out.println("Done");
    }
}