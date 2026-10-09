package topit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class DialogueBox extends Group {
    private static final Color BACKGROUND = Painter.rgb(14, 19, 28);
    private final PixelFont line = new PixelFont("Привет!");
    private final Painter painter;
    private final PixelButton next;

    public DialogueBox(Painter painter, Runnable onNext) {
        this.painter = painter;
        next = new PixelButton("Далее", painter, onNext);
        addActor(next);
        setTransform(false);
    }

    @Override protected void sizeChanged() { next.setBounds(getWidth() - 184, 16, 168, 40); }

    @Override public void draw(Batch batch, float parentAlpha) {
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        // белая рамка, слева портрет NPC, справа текст реплики
        painter.fillRect(x, y, width, height, BACKGROUND);
        painter.fillRect(x, y, width, 3, Color.WHITE);
        painter.fillRect(x, y + height - 3, width, 3, Color.WHITE);
        painter.fillRect(x, y, 3, height, Color.WHITE);
        painter.fillRect(x + width - 3, y, 3, height, Color.WHITE);
        painter.fillRect(x + 20, y + height - 85, 60, 60, Color.WHITE);
        painter.fillRect(x + 23, y + height - 82, 54, 54, Color.BLACK);
        painter.fillRect(x + 32, y + height - 73, 36, 36, WorldRenderer.NPC_COLOR);
        line.draw(painter, x + 104, y + height - 58, 3, Color.WHITE);
        super.draw(batch, parentAlpha);
    }
}
