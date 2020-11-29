import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Timer;
import java.util.TimerTask;

public class Music 
{
	private Clip audioClip;
	private File audioFile;
	
	public Music(String fileName)
	{
		audioFile = new File(fileName);
	}
	public void loop()
	{
		try
		{
			Timer timer = new Timer();
			audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(audioFile));
			
			long songTime = audioClip.getMicrosecondLength();
			TimerTask playSong = new TimerTask()
			{
				public void run()
				{
					try
					{
						audioClip.setMicrosecondPosition(-19252);
						audioClip.start();
					}
					catch(Exception e) 
					{	
						e.printStackTrace(System.out);	
						audioClip.start();
					}
					
				}
			};
			timer.scheduleAtFixedRate(playSong, 10L, songTime);
		}
		catch(Exception e) { }
		
	}
	
	public void play()
	{
	
		try
		{
			audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(audioFile));
			audioClip.start();
		}
		catch(Exception e) {	e.printStackTrace(System.out);	 }
		
	}
	
	public void endSong()
	{
		audioClip.stop();
		audioClip.flush();
	}
	
	public void reset()
	{
		endSong();
		audioClip.setMicrosecondPosition(0L);
		audioClip.start();
	}
}
