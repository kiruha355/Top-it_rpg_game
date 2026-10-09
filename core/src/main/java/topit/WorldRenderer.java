package topit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// рисует мир, сами World, Player и Npc про отрисовку ничего не знают
public class WorldRenderer {
    public static final Color NPC_COLOR = Painter.rgb(119, 187, 220);
    private final Painter painter;
    private final PixelFont greeting = new PixelFont("Привет!");

    public WorldRenderer(Painter painter) { this.painter = painter; }

    public void render(OrthographicCamera camera, World world, Npc npc, Player player, boolean showGreeting) {
        SpriteBatch batch = painter.batch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // порядок важен: что нарисовано позже, то сверху
        batch.draw(world.background(), 0, 0);
        drawNpc(npc, showGreeting);
        drawPlayer(player);
        batch.end();
    }

    private void drawPlayer(Player player) {
        int x = Math.round(player.x());
        int y = Math.round(player.y());
        int size = Player.HALF_SIZE * 2;
        painter.fillRect(x - Player.HALF_SIZE, y - Player.HALF_SIZE, size, size, Color.BLACK);
    }

    private void drawNpc(Npc npc, boolean showGreeting) {
        int x = Math.round(npc.x());
        int y = Math.round(npc.y());
        int half = Npc.SIZE / 2;
        painter.fillRect(x - half - 2, y - half - 2, Npc.SIZE + 4, Npc.SIZE + 4, Color.BLACK);
        painter.fillRect(x - half, y - half, Npc.SIZE, Npc.SIZE, NPC_COLOR);
        if (showGreeting) drawGreeting(x, y);
    }

    // облачко "Привет!" над головой
    private void drawGreeting(int x, int y) {
        int labelWidth = greeting.width(2);
        int left = x - labelWidth / 2 - 8;
        painter.fillRect(left - 2, y + 18, labelWidth + 20, 32, Color.WHITE);
        painter.fillRect(left, y + 20, labelWidth + 16, 28, Color.BLACK);
        painter.fillRect(x - 3, y + 14, 6, 6, Color.BLACK);
        greeting.draw(painter, left + 8, y + 27, 2, Color.WHITE);
    }
}
