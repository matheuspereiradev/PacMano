package com.matheus.game;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sons {

	private AudioClip song;
	public static final Sons musica=new Sons("/songs/Windless Slopes.wav");
	public static final Sons danoSong=new Sons("/songs/hurt.wav");
	public static final Sons danoInimigoSong=new Sons("/songs/dano_inimigo.wav");
	public static final Sons bipMenu=new Sons("/songs/bip.wav");
	public static final Sons vidaSong=new Sons("/songs/vida.wav");
	public static final Sons tiroSong=new Sons("/songs/tiro.wav");
	public static final Sons naoPodeSong=new Sons("/songs/nao_pode.wav");
	public static final Sons proxFase=new Sons("/songs/nextLevel.wav");
	public static final Sons lava=new Sons("/songs/lava.wav");
	

	public Sons(String path) {
		try {
			song = Applet.newAudioClip(getClass().getResource(path));
		} catch (Throwable e) {
			System.out.println(e);
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					song.play();
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					song.loop();
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	public void stop() {
		song.stop();
	}
	
}
