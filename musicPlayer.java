//Imports libraries for playing audio and inputting files.
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

//Class that implements Runnable in order to run in the background.
public class musicPlayer implements Runnable{

    //String that will contain that requested music selection.
    private String fileLocation = "null";

    //Thread for pausing and looping the music.
    Thread t;

    //Constructor method.
    public musicPlayer() {
    }

    //Initializes the file path of the music and the thread.
    public void play(String name)
    {
        fileLocation = name;
        t = new Thread(this);
        t.start();
    }

    //Method that will run in the background.
    @Override
    public void run()
    {
        //Loops the audio.
        while(true) {
            //Calls the play sound method to play music in the background.
            playSound(fileLocation);
        }
    }

    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;



        //Play a given audio file.
        void playSound(String fileName){

            //Creates a file that has the requested audio.
            File audioFile = new File(fileName);
            fileLocation = fileName;

            //Obtains the audio, formats it, opens a data line  and starts the data line.
            //Then reads the bytes playing from the audio and if the bytes stop coming in, it closes and drains the audio line.
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

            //Error Messages.
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

    // for stopping the thread
    public void stop()
    {
        t.suspend();
    }
}
