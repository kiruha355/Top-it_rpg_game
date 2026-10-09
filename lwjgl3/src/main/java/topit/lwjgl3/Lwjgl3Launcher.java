package topit.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import topit.TopItGame;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Топ топ айти");
        config.setWindowedMode(TopItGame.WIDTH, TopItGame.HEIGHT);
        config.setResizable(false);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new TopItGame(), config);
    }
}
