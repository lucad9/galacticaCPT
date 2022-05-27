import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
public class musicPlayer implements Runnable{


    Thread t;

    musicPlayer()
    {
        t = new Thread(this);
        t.start(); // Starting the thread
    }

    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;

    //Play a given audio file.
    void play(String audioFilePath) {

        File audioFile = new File(audioFilePath);
        while (true) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                AudioFormat format = audioStream.getFormat();

                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

                audioLine.open(format);

                audioLine.start();

                byte[] bytesBuffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                    audioLine.write(bytesBuffer, 0, bytesRead);
                }

                //stops audio.
                audioLine.drain();
                audioLine.stop();
                audioLine.close();

            } catch (UnsupportedAudioFileException ex) {
                System.out.println("The specified audio file is not supported.");
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                System.out.println("Audio line for playing back is unavailable.");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Error playing the audio file.");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
    }
}