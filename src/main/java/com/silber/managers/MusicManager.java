package com.silber.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MusicManager
{

	private int currentSong = 0;
	private String[] songNames = new String[] {
		"Greatest Battle Music Of All Times_ Dragon Rider.wav",
		"Greatest Battle Music Of All Times_ Immortal.wav"
	};

	protected ConfigManager CM;
	private MediaPlayer[] players;

	public MusicManager(ConfigManager CM)
	{
		this.CM = CM;
		int len = songNames.length;
		players = new MediaPlayer[len];

		for (int i = 0; i < len; i++)
		{
			players[i] = new MediaPlayer(new Media(MusicManager.class.getClassLoader().getResource(songNames[i]).toString()));
			players[i].setOnEndOfMedia(() -> nextSong());
		}
		
	}

	public void setup()
	{
		//play();
	}

	public void pause()
	{
		if (players[currentSong].getStatus() != Status.PAUSED)
		{
			players[currentSong].pause();
		}
	}

	public void play()
	{
		if (players[currentSong].getStatus() != Status.PLAYING)
		{
			players[currentSong].play();
		}
	}

	public void nextSong()
	{
		currentSong = (currentSong + 1) % songNames.length;
		play();
	}
	
	public boolean isPlaying()
	{
		return players[currentSong].getStatus() == Status.PLAYING;
	}
	
}
