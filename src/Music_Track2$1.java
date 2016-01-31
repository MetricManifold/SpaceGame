/*     */ package layout;
/*     */ 
/*     */ import java.io.File;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioFormat.Encoding;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.BooleanControl;
/*     */ import javax.sound.sampled.BooleanControl.Type;
/*     */ import javax.sound.sampled.DataLine.Info;
/*     */ import javax.sound.sampled.FloatControl;
/*     */ import javax.sound.sampled.FloatControl.Type;
/*     */ import javax.sound.sampled.SourceDataLine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Music_Track2$1
/*     */   implements Runnable
/*     */ {
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  37 */       if (Music_Track2.sound.getName().toLowerCase().contains(".wav"))
/*     */       {
/*  39 */         AudioInputStream stream = AudioSystem.getAudioInputStream(Music_Track2.sound);
/*     */         
/*  41 */         AudioFormat format = stream.getFormat();
/*     */         
/*  43 */         if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
/*     */         {
/*  45 */           format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
/*  46 */             format.getSampleRate(), 
/*  47 */             format.getSampleSizeInBits() * 2, 
/*  48 */             format.getChannels(), 
/*  49 */             format.getFrameSize() * 2, 
/*  50 */             format.getFrameRate(), 
/*  51 */             true);
/*     */           
/*  53 */           stream = AudioSystem.getAudioInputStream(format, stream);
/*     */         }
/*     */         
/*  56 */         DataLine.Info info = new DataLine.Info(
/*  57 */           SourceDataLine.class, 
/*  58 */           stream.getFormat(), 
/*  59 */           (int)(stream.getFrameLength() * format.getFrameSize()));
/*     */         
/*  61 */         SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
/*  62 */         line.open(stream.getFormat());
/*  63 */         line.start();
/*     */         
/*     */ 
/*  66 */         FloatControl volume_control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
/*  67 */         volume_control.setValue((float)(Math.log(Music_Track2.volume / 100.0F) / Math.log(10.0D) * 20.0D));
/*     */         
/*     */ 
/*  70 */         BooleanControl mute_control = (BooleanControl)line.getControl(BooleanControl.Type.MUTE);
/*  71 */         mute_control.setValue(Music_Track2.muted);
/*     */         
/*  73 */         FloatControl pan_control = (FloatControl)line.getControl(FloatControl.Type.PAN);
/*  74 */         pan_control.setValue(Music_Track2.pan);
/*     */         
/*  76 */         long last_update = System.currentTimeMillis();
/*  77 */         double since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
/*     */         
/*     */ 
/*     */ 
/*  81 */         while (since_last_update < Music_Track2.seconds)
/*     */         {
/*  83 */           since_last_update = (System.currentTimeMillis() - last_update) / 1000.0D;
/*     */         }
/*     */         
/*     */ 
/*  87 */         int num_read = 0;
/*  88 */         byte[] buf = new byte[line.getBufferSize()];
/*     */         int offset;
/*  90 */         for (; (num_read = stream.read(buf, 0, buf.length)) >= 0; 
/*     */             
/*     */ 
/*     */ 
/*  94 */             offset < num_read)
/*     */         {
/*  92 */           offset = 0;
/*     */           
/*  94 */           continue;
/*     */           
/*  96 */           offset += line.write(buf, offset, num_read - offset);
/*     */         }
/*     */         
/*     */ 
/* 100 */         line.drain();
/* 101 */         line.stop();
/*     */         
/*     */ 
/* 104 */         if (Music_Track2.looped_forever)
/*     */         {
/* 106 */           new Thread(Music_Track2.play).start();
/*     */         }
/* 108 */         else if (Music_Track2.loops_done < Music_Track2.loop_times)
/*     */         {
/* 110 */           Music_Track2.loops_done += 1;
/* 111 */           new Thread(Music_Track2.play).start();
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 115 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Music_Track2$1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */