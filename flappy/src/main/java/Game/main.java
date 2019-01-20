package Game;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class main {

    public static void main(String[] args){

        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            terminal.setCursorVisible(false);
            TextGraphics textGraphics = terminal.newTextGraphics();
            KeyStroke key;

            textGraphics.putString(0, 0, "adjust screen size, then press 's'");
            terminal.flush();
            int r = 1, score = 0, best_score = 0;

            Game game = new Game(terminal);

            while (true) {
                key = terminal.readInput();
                if (key.getKeyType() == KeyType.Character)
                    if (key.getCharacter() == 's') {
                        if (terminal.getTerminalSize().getRows() < 28) {
                            textGraphics.putString(0, r++, "minimum Jpanel rows: 28");
                            terminal.flush();
                        } else {
                            score = game.play();
                            terminal.clearScreen();
                            r = 0;
                            best_score = Math.max(best_score, score);
                            textGraphics.putString(0, r++, "GAME OVER");
                            textGraphics.putString(0, r++, "Score: " + score);
                            textGraphics.putString(0, r++, "Best score: " + best_score);
                            textGraphics.putString(0, r++, "press 's' to try again or 'e' to exit");
                            terminal.flush();
                        }
                    } else if (key.getCharacter() == 'e')
                        break;
            }

            terminal.close();
        } catch (IOException ex)
        {
            System.out.println("initial terminal error");
            ex.printStackTrace();
        }

    }
}
