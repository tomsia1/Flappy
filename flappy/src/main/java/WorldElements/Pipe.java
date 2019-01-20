package WorldElements;

import Flappy.Bird;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.Random;

public class Pipe {

    private static final int gap=8;
    private static final int width=4;
    private static final int vertical=26;

    private int front;
    private int back;
    private int high;
    private int low;

    private int edge;
    private Screen screen;

    private Bird bird;

    private TextCharacter brick=new TextCharacter('#', TextColor.ANSI.GREEN,TextColor.ANSI.GREEN);
    private TextCharacter sky=new TextCharacter(' ', TextColor.ANSI.BLACK,TextColor.ANSI.BLACK);

    private boolean fillColumn(Screen screen,int col)
    {
        boolean collision=false;
        for (int i=vertical;i>0;i--)
            if (i>=low || i<=high) {
                if (bird.getColumn()+1==col && (bird.getRow()==i || bird.getRow()-1==i))
                    collision = true;
                else
                    screen.setCharacter(col, i, brick);
            }
        return collision;
    }

    private void deleteColumn(Screen screen,int col)
    {
        for (int i=1;i<=vertical;i++)
            if (i<=high || i>=low)
                screen.setCharacter(col,i,sky);
    }

    public Pipe(Screen screen,Random generator, Bird bird)
    {
        this.screen=screen;
        this.bird=bird;

        edge=screen.getTerminalSize().getColumns();

        int rand=generator.nextInt(9)+14;
        low=rand;
        high=rand-gap;

        front=edge;
        back=edge+width;
    }

    public boolean movePipe()
    {
        boolean collision=false;
        if (back<edge)
            deleteColumn(screen,back);
        if (front>0)
            collision|=fillColumn(screen,front-1);

        back--;
        front--;
        return collision;
    }

    public int getBack()
    {
        return back;
    }



}
