package topit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter {
    private final World world = new World();
    private final Player player = new Player(world);
    private final Npc npc = new Npc(1010, 404);
    private final OrthographicCamera camera = new OrthographicCamera();
    private final FitViewport viewport = new FitViewport(TopItGame.WIDTH, TopItGame.HEIGHT, camera);
    private final WorldRenderer renderer;
    // интерфейс живёт в отдельной Stage со своей камерой, поэтому не двигается вместе с картой
    private final Stage ui;
    private final PixelButton talk;
    private final DialogueBox dialogueBox;
    private boolean dialogueActive; // true, пока идёт разговор, игрок в это время стоит

    public GameScreen(TopItGame game) {
        Painter painter = game.painter();
        renderer = new WorldRenderer(painter);
        ui = new Stage(new FitViewport(TopItGame.WIDTH, TopItGame.HEIGHT), painter.batch());
        talk = new PixelButton("Диалог", painter, this::openDialogue);
        talk.setBounds(TopItGame.WIDTH - 196, 20, 180, 44);
        dialogueBox = new DialogueBox(painter, this::closeDialogue);
        dialogueBox.setBounds(12, 12, TopItGame.WIDTH - 24, 132);
        ui.addActor(talk);
        ui.addActor(dialogueBox);
        updateInteraction();
    }

    @Override public void show() { Gdx.input.setInputProcessor(ui); }

    // LibGDX сам вызывает render() каждый кадр, это и есть игровой цикл
    @Override public void render(float delta) {
        update(Math.min(delta, 0.05f));
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        renderer.render(camera, world, npc, player, !dialogueActive && npc.isNear(player));
        ui.getViewport().apply();
        ui.act(delta);
        ui.draw();
    }

    // delta не больше 0.05, чтобы после подвисания игрок не проскочил сквозь стену
    private void update(float seconds) {
        if (!dialogueActive) {
            if (Gdx.input.isKeyJustPressed(Keys.R)) player.reset(world);
            // направление по каждой оси: -1, 0 или 1, ось y направлена вверх
            float dx = axis(Keys.D, Keys.RIGHT) - axis(Keys.A, Keys.LEFT);
            float dy = axis(Keys.W, Keys.UP) - axis(Keys.S, Keys.DOWN);
            player.move(world, dx, dy, seconds);
        }
        updateInteraction();
        followPlayer();
    }

    private float axis(int letter, int arrow) {
        return Gdx.input.isKeyPressed(letter) || Gdx.input.isKeyPressed(arrow) ? 1 : 0;
    }

    // игрок в центре экрана, но камера не выходит за края карты
    private void followPlayer() {
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;
        float x = MathUtils.clamp(player.x(), halfWidth, world.width() - halfWidth);
        float y = MathUtils.clamp(player.y(), halfHeight, world.height() - halfHeight);
        camera.position.set(Math.round(x), Math.round(y), 0);
        camera.update();
    }

    private void openDialogue() {
        if (!npc.isNear(player) || dialogueActive) return;
        dialogueActive = true;
        updateInteraction();
    }

    private void closeDialogue() {
        dialogueActive = false;
        updateInteraction();
    }

    private void updateInteraction() {
        talk.setVisible(!dialogueActive && npc.isNear(player));
        dialogueBox.setVisible(dialogueActive);
    }

    @Override public void resize(int width, int height) {
        viewport.update(width, height);
        ui.getViewport().update(width, height, true);
    }

    @Override public void dispose() {
        ui.dispose();
        world.dispose();
    }
}
