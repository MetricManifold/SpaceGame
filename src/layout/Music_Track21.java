package layout;


import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.BooleanControl.Type;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import javax.sound.sampled.SourceDataLine;

class Music_Track21
		implements Runnable
{
	public void run()
   {
     try
     {
       if (Music_Track2.sound.getName().toLowerCase().contains(".wav"))
       {
         AudioInputStream stream = AudioSystem.getAudioInputStream(Music_Track2.sound);
         
         AudioFormat format = stream.getFormat();
         
         if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
         {
           format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
             format.getSampleRate(), 
             format.getSampleSizeInBits() * 2, 
             format.getChannels(), 
             format.getFrameSize() * 2, 
             format.getFrameRate(), 
             true);
           
           stream = AudioSystem.getAudioInputStream(format, stream);
         }
         
         DataLine.Info info = new DataLine.Info(
           SourceDataLine.class, 
           stream.getFormat(), 
           (int)(stream.getFrameLength() * format.getFrameSize()));
         
         SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
         line.open(stream.getFormat());
         line.start();
         
 
         FloatControl volume_control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
         volume_control.setValue((float)(Math.log(Music_Track2.volume / 100.0F) / Math.log(10.0D) * 20.0D));
         
 
         BooleanControl mute_control = (BooleanControl)line.getControl(BooleanControl.Type.MUTE);
         mute_control.setValue(Music_Track2.muted);
         
         FloatControl pan_control = (FloatControl)line.getControl(FloatControl.Type.PAN);
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
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\Music_Track2$1.class Java compiler version: 6 (50.0)
 * JD-Core Version: 0.7.1
 */