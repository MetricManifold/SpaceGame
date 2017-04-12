package layout;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class Music_Track2
{
	static java.io.File sound;

	public static void play()
	{
		sound = new java.io.File("Greatest Battle Music Of All Times_ Dragon Rider.wav");
		new Thread(play).start();
	}

	static boolean muted = false;
	static float volume = 20.0F;
	static float pan = 0.0F;

	static double seconds = 0.0D;

	static boolean looped_forever = true;

	static int loop_times = 0;
	static int loops_done = 0;

	static final Runnable play = new Runnable()
   {
 
     public void run()
     {
       try
       {
         if (Music_Track2.sound.getName().toLowerCase().contains(".wav"))
         {
           AudioInputStream stream = javax.sound.sampled.AudioSystem.getAudioInputStream(Music_Track2.sound);
           
           AudioFormat format = stream.getFormat();
           
           if (format.getEncoding() != javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED)
           {
             format = new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, 
               format.getSampleRate(), 
               format.getSampleSizeInBits() * 2, 
               format.getChannels(), 
               format.getFrameSize() * 2, 
               format.getFrameRate(), 
               true);
             
             stream = javax.sound.sampled.AudioSystem.getAudioInputStream(format, stream);
           }
           
           javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(
             SourceDataLine.class, 
             stream.getFormat(), 
             (int)(stream.getFrameLength() * format.getFrameSize()));
           
           SourceDataLine line = (SourceDataLine)javax.sound.sampled.AudioSystem.getLine(info);
           line.open(stream.getFormat());
           line.start();
           
 
           FloatControl volume_control = (FloatControl)line.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
           volume_control.setValue((float)(Math.log(Music_Track2.volume / 100.0F) / Math.log(10.0D) * 20.0D));
           
 
           javax.sound.sampled.BooleanControl mute_control = (javax.sound.sampled.BooleanControl)line.getControl(javax.sound.sampled.BooleanControl.Type.MUTE);
           mute_control.setValue(Music_Track2.muted);
           
           FloatControl pan_control = (FloatControl)line.getControl(javax.sound.sampled.FloatControl.Type.PAN);
           pan_control.setValue(Music_Track2.pan);
           
           long last_update = System.currentTimeMillis();
           double since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
           
 
 
           while (since_last_update < Music_Track2.seconds)
           {
             since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
           }
           
 
           int num_read = 0;
           byte[] buf = new byte[line.getBufferSize()];
           int offset;
           for (; (num_read = stream.read(buf, 0, buf.length)) >= 0; 
               
 
 
               offset < num_read)
           {
             offset = 0;
             
             continue;
             
             offset += line.write(buf, offset, num_read - offset);
           }
           
 
           line.drain();
           line.stop();
           
 
           if (Music_Track2.looped_forever)
           {
             new Thread(Music_Track2.play).start();
           }
           else if (Music_Track2.loops_done < Music_Track2.loop_times)
           {
             Music_Track2.loops_done += 1;
             new Thread(Music_Track2.play).start();
           }
         }
       } catch (Exception ex) {
         ex.printStackTrace();
       }
     }
   };
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\Music_Track2.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */