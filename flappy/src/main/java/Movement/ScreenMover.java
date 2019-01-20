package Movement;

import Flappy.Bird;
import WorldElements.Pipe;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenMover implements Runnable {

    private Screen screen;
    private TextGraphics textGraphics;
    private List<Pipe> pipes=new ArrayList<Pipe>();

    private int add_counter;
    private int score=0;

    private final int add_timer=14; //TODO set distance
    private Random generator=new Random();

    private AtomicBoolean flag;
    private Bird bird;

    public ScreenMover (Screen screen,AtomicBoolean flag,Bird bird) {
        this.bird = bird;
        this.screen = screen;
        add_counter = add_timer;
        this.flag=flag;
        textGraphics=screen.newTextGraphics();
    }

    public void run() {

        boolean check=false;
        while(!flag.get()) {
            if (add_counter == add_timer) {
                add_counter = 0;
                pipes.add(new Pipe(screen,generator,bird));
            } else
                add_counter++;

            for (Pipe pipe : pipes) {
                if (pipe.getBack()==bird.getColumn()-1)
                    score++;
                check |= pipe.movePipe();
            }

            if (check)
                flag.set(true);

            checkFirstPipe();

            textGraphics.putString(0,0,"score: "+score+" ");

            try {
                screen.refresh();
                Thread.sleep(50); //TODO set speed
            } catch (IOException ex) {
                System.out.println("Unable to refresh screen in ScreenMover");
                ex.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("ScreenMover interrupted");
                e.printStackTrace();
            }
        }

    };

    private void checkFirstPipe()
    {
        if (pipes.size()>0)
            if (pipes.get(0).getBack()<0)
                pipes.remove(0);
    }

    public int getScore()
    {
        return  score;
    }
}
