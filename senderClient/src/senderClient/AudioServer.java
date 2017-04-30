package senderClient;

import java.io.*;
import java.net.*;

public class AudioServer {
	public static void main(String[] args) throws IOException {
		if (args.length == 0)
			throw new IllegalArgumentException("Expected Sound File");
		File soundFile = new File(args[0]);

		System.out.println("server: " + soundFile);

		Socket s = new Socket("34.200.251.30", 8888); //AWS IP

		OutputStream out = s.getOutputStream(); //Get the output stream for the socket

		FileInputStream in = new FileInputStream(soundFile); //input stream of the file

		byte buffer[] = new byte[2048]; 
		int count;
		count = in.read(buffer);
		//loop to write out the file
		while(count >= 0){
			out.write(buffer,0,count);
			count = in.read(buffer);	
		}

		in.close();
		out.close();
		s.close();
		System.out.println("Done");
	}
}