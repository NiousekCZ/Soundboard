/**
 * Class to play audio.
 *
 * @author JavaZOOM 1999-2005
 * https://web.archive.org/web/20200624143310/http://www.javazoom.net/mp3spi/documents.html
 * 
 * @modified by NiousekCZ
 */

package sb;

import javazoom.spi.mpeg.sampled.convert.DecodedMpegAudioInputStream;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import javax.sound.sampled.*;
import java.io.*;

public class Player implements Runnable{//extends Thread{
    node a = null;
    
    Player(node in){
        //Empty constructor
        a = in;//OK, not so empty
    }
    
    
    @Override
    public void run(){
        play(a.PATH);
    }
    
    void play(String filename){
        try {
            File file = new File(filename);
            AudioInputStream in= AudioSystem.getAudioInputStream(file);
            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                        baseFormat.getSampleRate(),
                                                        16,
                                                        baseFormat.getChannels(),
                                                        baseFormat.getChannels() * 2,
                                                        baseFormat.getSampleRate(),
                                                        false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            // Play now.
            rawplay(decodedFormat, din);
            in.close();
        } catch (Exception e){
            //Handle exception.
        }
    }
    
    private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException {
        byte[] data = new byte[4096];
        SourceDataLine line = getLine(targetFormat);
        if (line != null){
            // Start
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1) {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
            }
            // Stop
            line.drain();
            line.stop();
            line.close();
            din.close();
        }
    }

    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    } 
}
