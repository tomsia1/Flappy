package Movement;

import Flappy.Bird;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpMover implements Runnable {

    private Screen screen;
    private Bird bird;

    private AtomicBoolean flag;

    public UpMover(Screen screen,Bird bird,AtomicBoolean flag)
    {
        this.flag=flag;
        this.screen=screen;
        this.bird=bird;
    }

    public void run(){
        KeyStroke ks=null;

        while (!flag.get())
        {
            try {
                ks=screen.pollInput();
            } catch (IOException ex){
                System.out.println("input error in UpMover");
                ex.printStackTrace();
            }

            if (ks!=null && ks.getKeyType()== KeyType.ArrowUp)
                    if(bird.up())
                        flag.set(true);

        }
    }
}
