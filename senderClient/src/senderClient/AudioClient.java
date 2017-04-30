package senderClient;

import java.io.*;
import java.net.*;
import javax.sound.sampled.*;


public class AudioClient {
	public static void main(String[] args) throws Exception {
		// play soundfile from server
		System.out.println("Reading");
		try (Socket socket = new Socket("34.200.251.30", 8888)) { //AWS IP
			if (socket.isConnected()) {
				InputStream in = new BufferedInputStream(socket.getInputStream());
				play(in);
			}
		}


		System.out.println("Done");
	}


	private static synchronized void play(final InputStream in) throws Exception {
		AudioInputStream input = AudioSystem.getAudioInputStream(in);
		try (Clip clip = AudioSystem.getClip()) {
			clip.open(input);
			clip.start();
			Thread.sleep(100); // given clip.drain a chance to start
			clip.drain();
		}
	}
}
