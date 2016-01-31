/*    */ package layout;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.sound.sampled.AudioFormat;
/*    */ import javax.sound.sampled.AudioInputStream;
/*    */ import javax.sound.sampled.AudioSystem;
/*    */ import javax.sound.sampled.BooleanControl;
/*    */ import javax.sound.sampled.FloatControl;
/*    */ import javax.sound.sampled.SourceDataLine;
/*    */ 
/*    */ public class Music_Track1
/*    */ {
/* 13 */   static boolean playing = false;
/*    */   static File sound;
/*    */   
/*    */   public static void play()
/*    */   {
/* 18 */     sound = new File("Greatest Battle Music Of All Times_ Immortal.wav");
/* 19 */     new Thread(play).start();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 24 */   static boolean muted = false;
/* 25 */   static float volume = 20.0F;
/* 26 */   static float pan = 0.0F;
/*    */   
/* 28 */   static double seconds = 0.0D;
/*    */   
/* 30 */   static boolean looped_forever = true;
/*    */   
/* 32 */   static int loop_times = 0;
/* 33 */   static int loops_done = 0;
/*    */   
/* 35 */   static final Runnable play = new Runnable()
/*    */   {
/*    */ 
/*    */     public void run()
/*    */     {
/*    */ 
/*    */       try
/*    */       {
/* 43 */         if (Music_Track1.sound.getName().toLowerCase().contains(".wav"))
/*    */         {
/* 45 */           AudioInputStream stream = AudioSystem.getAudioInputStream(Music_Track1.sound);
/*    */           
/* 47 */           AudioFormat format = stream.getFormat();
/*    */           
/* 49 */           if (format.getEncoding() != javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED)
/*    */           {
/* 51 */             format = new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, 
/* 52 */               format.getSampleRate(), 
/* 53 */               format.getSampleSizeInBits() * 2, 
/* 54 */               format.getChannels(), 
/* 55 */               format.getFrameSize() * 2, 
/* 56 */               format.getFrameRate(), 
/* 57 */               true);
/*    */             
/* 59 */             stream = AudioSystem.getAudioInputStream(format, stream);
/*    */           }
/*    */           
/* 62 */           javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(
/* 63 */             SourceDataLine.class, 
/* 64 */             stream.getFormat(), 
/* 65 */             (int)(stream.getFrameLength() * format.getFrameSize()));
/*    */           
/* 67 */           SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
/* 68 */           line.open(stream.getFormat());
/* 69 */           line.start();
/*    */           
/*    */ 
/* 72 */           FloatControl volume_control = (FloatControl)line.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
/* 73 */           volume_control.setValue((float)(Math.log(Music_Track1.volume / 100.0F) / Math.log(10.0D) * 20.0D));
/*    */           
/*    */ 
/* 76 */           BooleanControl mute_control = (BooleanControl)line.getControl(javax.sound.sampled.BooleanControl.Type.MUTE);
/* 77 */           mute_control.setValue(Music_Track1.muted);
/*    */           
/* 79 */           FloatControl pan_control = (FloatControl)line.getControl(javax.sound.sampled.FloatControl.Type.PAN);
/* 80 */           pan_control.setValue(Music_Track1.pan);
/*    */           
/* 82 */           long last_update = System.currentTimeMillis();
/* 83 */           double since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
/*    */           
/*    */ 
/* 86 */           while (since_last_update < Music_Track1.seconds)
/*    */           {
/* 88 */             since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
/*    */           }
/*    */           
/* 91 */           int num_read = 0;
/* 92 */           byte[] buf = new byte[line.getBufferSize()];
/*    */           int offset;
/* 94 */           for (; (num_read = stream.read(buf, 0, buf.length)) >= 0; 
/*    */               
/*    */ 
/*    */ 
/* 98 */               offset < num_read)
/*    */           {
/* 96 */             offset = 0;
/*    */             
/* 98 */             continue;
/*    */             
/* :0 */             offset += line.write(buf, offset, num_read - offset);
/*    */           }
/*    */           
/*    */ 
/* :4 */           line.drain();
/* :5 */           line.stop();
/*    */           
/*    */ 
/* :8 */           if (Music_Track1.looped_forever)
/*    */           {
/* ;0 */             new Thread(Music_Track1.play).start();
/*    */           }
/* ;2 */           else if (Music_Track1.loops_done < Music_Track1.loop_times)
/*    */           {
/* ;4 */             Music_Track1.loops_done += 1;
/* ;5 */             new Thread(Music_Track1.play).start();
/*    */           }
/*    */         }
/*    */       } catch (Exception ex) {
/* ;9 */         ex.printStackTrace();
/*    */       }
/*    */     }
/*    */   };
/*    */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Music_Track1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */