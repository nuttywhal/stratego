package edu.asu.stratego.media;

import javafx.scene.media.AudioClip;

import java.util.HashMap;

import edu.asu.stratego.media.SoundConstants;
import edu.asu.stratego.util.HashTables;

public class PlaySound {
	public static void play(String file) {
		AudioClip audio = HashTables.SOUND_MAP.get(file);
		audio.play();
	}
}