package Game;

import Flappy.Bird;
import Movement.DownMover;
import Movement.ScreenMover;
import Movement.UpMover;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {

    private static TextCharacter brick=new TextCharacter('#', TextColor.ANSI.GREEN,TextColor.ANSI.GREEN);
    private static TextCharacter sky=new TextCharacter(' ', TextColor.ANSI.BLACK,TextColor.ANSI.BLACK);

    private Terminal terminal;

    public Game(Terminal terminal)
    {
        this.terminal=terminal;
    }

    public int  play() throws IOException {

        Screen screen=initScreen(terminal);

        AtomicBoolean flag=new AtomicBoolean(false);
        Bird bird=new Bird(20,screen);

        screen.refresh();

        UpMover upmover=new UpMover(screen,bird,flag);
        DownMover downmover=new DownMover(bird,flag);
        ScreenMover screenMover=new ScreenMover(screen,flag,bird);

        Thread t1=new Thread(upmover);
        Thread t2=new Thread(downmover);
        Thread t3=new Thread(screenMover);

        t1.start();
        t2.start();
        t3.start();

        screen.startScreen();

        try{
            t1.join();
            t2.join();
            t3.join();

            Thread.sleep(1000);
        } catch (InterruptedException ex)
        {
            System.out.println("Finalazing game error");
            ex.printStackTrace();
        }

        return screenMover.getScore();
    }

    private Screen initScreen(Terminal terminal) throws IOException
    {
        int col=terminal.getTerminalSize().getColumns();
        int row=terminal.getTerminalSize().getRows();

        Screen screen=new TerminalScreen(terminal);
        screen.setCursorPosition(null);

        for (int i=0;i<col;i++)
            for (int j=0;j<row;j++)
                screen.setCharacter(i,j,sky);

        for (int i=0;i<col;i++){
            screen.setCharacter(i,0,brick);
            screen.setCharacter(i,27,brick);
        }

        return screen;
    }
}
