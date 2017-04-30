package senderClient;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFileFormat.Type;

import java.net.*;
import java.io.*;

public class sender {

	public static final String IP = "localhost";
	public static final int PORT = 8080;
	
	public static void main(String[] args){
		/*
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		
		int i = 1;
		for (Mixer.Info info: mixerInfos){
			System.out.println("Mixer: " + i + " " + info);
			Mixer m = AudioSystem.getMixer(info);
			Line.Info[] lineInfos = m.getTargetLineInfo();
			int b = 1;
			for (Line.Info lineInfo:lineInfos){
				System.out.println("Target Line: " + b);
				System.out.println (m+"---"+lineInfo);
				Line line = null;
				try {
					line = m.getLine(lineInfo);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("\t-----"+line);
				b++;
			}
			i++;
			System.out.println();

		}
		*/
		
		
		//System.out.println("\n\n\n");
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		Mixer.Info info = mixers[3];
		System.out.println("Mixer: " + info);
		Mixer m = AudioSystem.getMixer(info);
		Line.Info[] lineInfos = m.getTargetLineInfo();
		Line.Info lineInfo = lineInfos[0];
		System.out.println("Line: " + lineInfo);
		Line line = null;
		try {
			line = m.getLine(lineInfo);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TargetDataLine target = (TargetDataLine)line;
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		byte[] buffer = new byte[8192];
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		
		AudioInputStream stream = new AudioInputStream(input, format, buffer.length);
		
		try {
		    target.open(format);
		    target.start();
		} catch (LineUnavailableException ex) {
			//TODO: Handle Error
		}
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(IP);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//stream forever
		int i = 0;
		File file = new File("test.wav");
		try {
			AudioSystem.write(stream, Type.WAVE, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
