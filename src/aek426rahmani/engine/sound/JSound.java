/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

// WORK IN PROGRESS

public class JSound {

	private Clip clip;
	private FloatControl gainControl;

	public JSound(String path) {
		try {
			InputStream audioSrc = JSound.class.getResourceAsStream(path);
			InputStream bufferedStream =  new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedStream);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					                                   baseFormat.getSampleRate(),
													   16,
													   baseFormat.getChannels(),
					                                   baseFormat.getChannels() * 2,
													   baseFormat.getSampleRate(),
													  false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);

			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if(clip == null)
			return;
		clip.setFramePosition(0);
		clip.start();
	}
}
