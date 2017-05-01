package senderClient;

import java.io.*;
import java.net.*;
import javax.sound.sampled.*;


public class AudioClient {
	public static void main(String[] args) throws Exception {
		// play soundfile from server
		System.out.println("Reading");
		while(true){
			try (Socket socket = new Socket("34.200.251.30", 8888)) { //AWS IP
				if (socket.isConnected()) { //while connected to the socket play the audio
					PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
					pw.println("receiver");
					InputStream in = new BufferedInputStream(socket.getInputStream());
					play(in);
				}
			}
		}
		//System.out.println("Done");
	}


	//Method for playing audio from an input stream
	private static synchronized void play(final InputStream in) throws Exception {
		AudioInputStream input = AudioSystem.getAudioInputStream(in);
		try (Clip clip = AudioSystem.getClip()) { //get clip of audio
			clip.open(input);
			clip.start(); //play the clip
			Thread.sleep(100); // given clip.drain a chance to start
			clip.drain();
		}
	}
}
