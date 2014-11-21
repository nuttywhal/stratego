package edu.asu.stratego.media;

import javafx.scene.media.AudioClip;

import edu.asu.stratego.util.HashTables;

public class PlaySound {
	public static void playMusic(String file, int Volume) {
		AudioClip music = HashTables.SOUND_MAP.get(file);
		music.setVolume(Volume);
		music.play();
		System.out.println("Played music (\"" + file + "\", " + Volume*100 + "% volume)");
	}
	public static void playEffect1(String file, int Volume) {
		AudioClip effect1 = HashTables.SOUND_MAP.get(file);
		effect1.setVolume(Volume);
		effect1.play();
	}
	public static void playEffect2(String file, int Volume) {
		AudioClip effect2 = HashTables.SOUND_MAP.get(file);
		effect2.setVolume(Volume);
		effect2.play();
	}
}