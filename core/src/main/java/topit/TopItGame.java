package topit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class TopItGame extends Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 556;
    private Painter painter;

    @Override public void create() {
        painter = new Painter();
        setScreen(new MenuScreen(this));
    }

    public Painter painter() { return painter; }

    public void startGame() {
        Screen menu = getScreen();
        setScreen(new GameScreen(this));
        menu.dispose();
    }

    @Override public void dispose() {
        super.dispose();
        if (getScreen() != null) getScreen().dispose();
        painter.dispose();
    }
}
