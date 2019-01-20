package Flappy;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Bird {

    private TextCharacter body=new TextCharacter('#',TextColor.ANSI.RED, TextColor.ANSI.RED);
    private TextCharacter sky=new TextCharacter(' ', TextColor.ANSI.BLACK,TextColor.ANSI.BLACK);

    private Screen screen;
    private int column,row=14;

    private boolean moved=true;

    public Bird(int column, Screen screen) throws IOException
    {
        this.column=column;
        this.screen=screen;

        for (int i=column;i<=column+1;i++)
            for (int j=row;j>=row-1;j--)
                screen.setCharacter(i,j,body);

        screen.refresh();
    }

    public boolean up1() throws IOException
    {
        boolean collision = false;
            moved = true;
            screen.setCharacter(column, row, sky);
            screen.setCharacter(column + 1, row, sky);

            row--;

            collision |= screen.getFrontCharacter(column, row - 1).getCharacter() == '#';
            collision |= screen.getFrontCharacter(column + 1, row - 1).getCharacter() == '#';

            screen.setCharacter(column, row - 1, body);
            screen.setCharacter(column + 1, row - 1, body);

            screen.refresh();
        return collision;
    }

    public boolean up()
    {
        boolean collision=false;
        synchronized (this){
            try{
                collision|=up1();
                Thread.sleep(50);
                if (!collision)
                    collision|=up1();
                Thread.sleep(50);
                if (!collision)
                    collision|=up1();

            } catch (IOException ex1)
            {
                ex1.printStackTrace();
            } catch (InterruptedException ex2)
            {
                ex2.printStackTrace();
            }
        }

        return collision;
    }

    public boolean down() throws IOException
    {
        boolean collision=false;
        synchronized (this){
            screen.setCharacter(column,row-1,sky);
            screen.setCharacter(column+1,row-1,sky);

            row++;

            collision|=screen.getFrontCharacter(column,row).getCharacter()=='#';
            collision|=screen.getFrontCharacter(column+1,row).getCharacter()=='#';

            screen.setCharacter(column,row,body);
            screen.setCharacter(column+1,row,body);

            screen.refresh();
        }

        return collision;
    }

    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }

    public boolean getSetMoved()
    {
        if (moved)
        {
            moved=false;
            return true;
        }
        else
            return false;
    }


}
