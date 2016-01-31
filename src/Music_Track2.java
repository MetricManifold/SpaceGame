/*    */ package layout;
/*    */ 
/*    */ import javax.sound.sampled.AudioFormat;
/*    */ import javax.sound.sampled.AudioInputStream;
/*    */ import javax.sound.sampled.FloatControl;
/*    */ import javax.sound.sampled.SourceDataLine;
/*    */ 
/*    */ public class Music_Track2
/*    */ {
/*    */   static java.io.File sound;
/*    */   
/*    */   public static void play()
/*    */   {
/* 14 */     sound = new java.io.File("Greatest Battle Music Of All Times_ Dragon Rider.wav");
/* 15 */     new Thread(play).start();
/*    */   }
/*    */   
/*    */ 
/* 19 */   static boolean muted = false;
/* 20 */   static float volume = 20.0F;
/* 21 */   static float pan = 0.0F;
/*    */   
/* 23 */   static double seconds = 0.0D;
/*    */   
/* 25 */   static boolean looped_forever = true;
/*    */   
/* 27 */   static int loop_times = 0;
/* 28 */   static int loops_done = 0;
/*    */   
/* 30 */   static final Runnable play = new Runnable()
/*    */   {
/*    */ 
/*    */     public void run()
/*    */     {
/*    */       try
/*    */       {
/* 37 */         if (Music_Track2.sound.getName().toLowerCase().contains(".wav"))
/*    */         {
/* 39 */           AudioInputStream stream = javax.sound.sampled.AudioSystem.getAudioInputStream(Music_Track2.sound);
/*    */           
/* 41 */           AudioFormat format = stream.getFormat();
/*    */           
/* 43 */           if (format.getEncoding() != javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED)
/*    */           {
/* 45 */             format = new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, 
/* 46 */               format.getSampleRate(), 
/* 47 */               format.getSampleSizeInBits() * 2, 
/* 48 */               format.getChannels(), 
/* 49 */               format.getFrameSize() * 2, 
/* 50 */               format.getFrameRate(), 
/* 51 */               true);
/*    */             
/* 53 */             stream = javax.sound.sampled.AudioSystem.getAudioInputStream(format, stream);
/*    */           }
/*    */           
/* 56 */           javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(
/* 57 */             SourceDataLine.class, 
/* 58 */             stream.getFormat(), 
/* 59 */             (int)(stream.getFrameLength() * format.getFrameSize()));
/*    */           
/* 61 */           SourceDataLine line = (SourceDataLine)javax.sound.sampled.AudioSystem.getLine(info);
/* 62 */           line.open(stream.getFormat());
/* 63 */           line.start();
/*    */           
/*    */ 
/* 66 */           FloatControl volume_control = (FloatControl)line.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
/* 67 */           volume_control.setValue((float)(Math.log(Music_Track2.volume / 100.0F) / Math.log(10.0D) * 20.0D));
/*    */           
/*    */ 
/* 70 */           javax.sound.sampled.BooleanControl mute_control = (javax.sound.sampled.BooleanControl)line.getControl(javax.sound.sampled.BooleanControl.Type.MUTE);
/* 71 */           mute_control.setValue(Music_Track2.muted);
/*    */           
/* 73 */           FloatControl pan_control = (FloatControl)line.getControl(javax.sound.sampled.FloatControl.Type.PAN);
/* 74 */           pan_control.setValue(Music_Track2.pan);
/*    */           
/* 76 */           long last_update = System.currentTimeMillis();
/* 77 */           double since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
/*    */           
/*    */ 
/*    */ 
/* 81 */           while (since_last_update < Music_Track2.seconds)
/*    */           {
/* 83 */             since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
/*    */           }
/*    */           
/*    */ 
/* 87 */           int num_read = 0;
/* 88 */           byte[] buf = new byte[line.getBufferSize()];
/*    */           int offset;
/* 90 */           for (; (num_read = stream.read(buf, 0, buf.length)) >= 0; 
/*    */               
/*    */ 
/*    */ 
/* 94 */               offset < num_read)
/*    */           {
/* 92 */             offset = 0;
/*    */             
/* 94 */             continue;
/*    */             
/* 96 */             offset += line.write(buf, offset, num_read - offset);
/*    */           }
/*    */           
/*    */ 
/* :0 */           line.drain();
/* :1 */           line.stop();
/*    */           
/*    */ 
/* :4 */           if (Music_Track2.looped_forever)
/*    */           {
/* :6 */             new Thread(Music_Track2.play).start();
/*    */           }
/* :8 */           else if (Music_Track2.loops_done < Music_Track2.loop_times)
/*    */           {
/* ;0 */             Music_Track2.loops_done += 1;
/* ;1 */             new Thread(Music_Track2.play).start();
/*    */           }
/*    */         }
/*    */       } catch (Exception ex) {
/* ;5 */         ex.printStackTrace();
/*    */       }
/*    */     }
/*    */   };
/*    */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Music_Track2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */