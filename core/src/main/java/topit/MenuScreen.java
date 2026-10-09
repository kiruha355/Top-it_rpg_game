package topit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends ScreenAdapter {
    private static final int TITLE_SCALE = 7;
    private static final Color TITLE_COLOR = Painter.rgb(230, 240, 250);
    private static final Color TITLE_SHADOW = Painter.rgb(56, 76, 99);
    private final PixelFont title = new PixelFont("Топ топ айти");
    private final Painter painter;
    private final Stage stage;

    public MenuScreen(TopItGame game) {
        painter = game.painter();
        stage = new Stage(new FitViewport(TopItGame.WIDTH, TopItGame.HEIGHT), painter.batch());
        // экран меняем в начале следующего кадра, а не посреди обработки клика
        PixelButton play = new PixelButton("Играть", painter, () -> Gdx.app.postRunnable(game::startGame));
        play.setPosition((TopItGame.WIDTH - play.getWidth()) / 2, (TopItGame.HEIGHT - play.getHeight()) / 2);
        stage.addActor(play);
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }

    @Override public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.getViewport().apply();
        stage.act(delta);
        drawTitle();
        stage.draw();
    }

    private void drawTitle() {
        SpriteBatch batch = painter.batch();
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        int x = (TopItGame.WIDTH - title.width(TITLE_SCALE)) / 2;
        int y = TopItGame.HEIGHT / 2 + 130 - title.height(TITLE_SCALE);
        // сначала тень со сдвигом, потом сам текст поверх
        title.draw(painter, x + TITLE_SCALE, y - TITLE_SCALE, TITLE_SCALE, TITLE_SHADOW);
        title.draw(painter, x, y, TITLE_SCALE, TITLE_COLOR);
        batch.end();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void dispose() { stage.dispose(); }
}
