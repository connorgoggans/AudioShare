package senderClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AudioServer {
	public static void main(String[] args) throws IOException {

		Scanner key = new Scanner(System.in); //Scanner for reading from keyboard

		Socket s = new Socket("34.200.251.30", 8888); //AWS IP

		Boolean done = false;
		
		OutputStream out = s.getOutputStream(); //Get the output stream for the socket

		while(!done){
			System.out.print("Sound file to stream (or exit to end): ");

			String file = key.nextLine();
			
			if(s.isClosed()){
				s = new Socket("34.200.251.30", 8888);
				out = s.getOutputStream();
			}
			
			PrintWriter pw = new PrintWriter(out, true);
			
			//Say that this is the sender
			pw.println("sender");

			if(file.equals("exit")){
				done = true;
			}else{
				File soundFile = new File(file);

				System.out.println("Streaming: " + soundFile);

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
				
			}
		}

		s.close();
		key.close();
		System.out.println("Done");
	}
}