package senderClient;

import javax.sound.sampled.*;

public class sender {

	public static void main(String[] args) throws LineUnavailableException {
		
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		int i = 1;
		for (Mixer.Info info: mixerInfos){
			System.out.println("Mixer: " + i);
			Mixer m = AudioSystem.getMixer(info);
			Line.Info[] lineInfos = m.getSourceLineInfo();
			int a = 1;
			for (Line.Info lineInfo:lineInfos){
				System.out.println("Source Line: " + a);
				System.out.println (info.getName()+"---"+lineInfo);
				Line line = m.getLine(lineInfo);
				System.out.println("\t-----"+line);
				a++;
			}
			lineInfos = m.getTargetLineInfo();
			int b = 1;
			for (Line.Info lineInfo:lineInfos){
				System.out.println("Target Line: " + b);
				System.out.println (m+"---"+lineInfo);
				Line line = m.getLine(lineInfo);
				System.out.println("\t-----"+line);
				b++;
			}
			i++;
			System.out.println();

		}

	}

}