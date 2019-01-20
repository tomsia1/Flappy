package Movement;

import Flappy.Bird;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownMover implements Runnable {

    private Bird bird;
    private AtomicBoolean flag;

    private Double base_v=8.0; //todo set falling speed
    private Double max_v=20.0;
    private Double v=base_v;
    private Double a=3.0;

    public DownMover(Bird bird,AtomicBoolean flag)
    {
        this.flag=flag;
        this.bird=bird;
    }

    public void run()
    {
        Double sleep;

        while (!flag.get())
        {
            try{
                if (bird.getSetMoved()){
                    v=base_v;
                    Thread.sleep(150);
                    continue;
                }
                else
                    v=Math.min(max_v,v+a);

                sleep=1000.0/v;


                        if(bird.down())
                            flag.set(true);
                    Thread.sleep(sleep.intValue());
            } catch (IOException ex)
            {
                System.out.println("Unable to refresh screen in DownMover");
                ex.printStackTrace();
            } catch (InterruptedException ex2)
            {
                System.out.println("DownMover interrupted");
                ex2.printStackTrace();
            }

        }
    }
}
