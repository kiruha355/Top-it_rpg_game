package topit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PixelButton extends Actor {
    private static final Color FACE = Painter.rgb(76, 94, 115);
    private static final Color HOVER_FACE = Painter.rgb(99, 137, 174);
    private static final Color GRAIN = Painter.rgb(83, 101, 122);
    private static final Color HOVER_GRAIN = Painter.rgb(107, 145, 182);
    private static final Color LIGHT_EDGE = Painter.rgb(130, 151, 175);
    private static final Color HOVER_EDGE = Painter.rgb(177, 209, 236);
    private static final Color DARK_EDGE = Painter.rgb(35, 46, 61);
    private static final Color TEXT = Painter.rgb(233, 237, 242);
    private static final Color HOVER_TEXT = Painter.rgb(220, 245, 255);
    private static final Color TEXT_SHADOW = Painter.rgb(29, 38, 50);
    private static final int SCALE = 3;
    private final PixelFont label;
    private final Painter painter;
    private final ClickListener clicks;

    public PixelButton(String text, Painter painter, Runnable onClick) {
        label = new PixelFont(text);
        this.painter = painter;
        // ClickListener сам следит за наведением мыши и нажатием
        clicks = new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) { onClick.run(); }
        };
        addListener(clicks);
        setSize(360, 52);
    }

    @Override public void draw(Batch batch, float parentAlpha) {
        boolean highlighted = clicks.isOver();
        boolean pressed = clicks.isPressed();
        float x = getX();
        float y = getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        // кнопка рисуется слоями: рамка, тень, лицо, узор, текст
        // при нажатии светлая и тёмная стороны меняются местами, и кнопка выглядит вдавленной
        painter.fillRect(x, y, width, height, Color.BLACK);
        painter.fillRect(x + 2, y + 2, width - 4, height - 4, pressed ? DARK_EDGE : highlighted ? HOVER_EDGE : LIGHT_EDGE);
        Color shadow = pressed ? LIGHT_EDGE : DARK_EDGE;
        painter.fillRect(x + 4, y + 2, width - 6, 4, shadow);
        painter.fillRect(x + width - 6, y + 2, 4, height - 6, shadow);
        painter.fillRect(x + 6, y + 6, width - 12, height - 12, highlighted ? HOVER_FACE : FACE);
        // узор по формуле, а не случайный, чтобы не мерцал между кадрами
        Color grain = highlighted ? HOVER_GRAIN : GRAIN;
        for (int row = 0; row < (height - 12) / 4; row++) {
            for (int col = 0; col < (width - 12) / 4; col++) {
                if ((row * 13 + col * 7) % 11 < 2) painter.fillRect(x + 6 + col * 4, y + height - 10 - row * 4, 4, 4, grain);
            }
        }
        int offset = pressed ? 2 : 0;
        float textX = x + (width - label.width(SCALE)) / 2 + offset;
        float textY = y + (height - label.height(SCALE)) / 2 - offset;
        label.draw(painter, textX + SCALE, textY - SCALE, SCALE, TEXT_SHADOW);
        label.draw(painter, textX, textY, SCALE, highlighted ? HOVER_TEXT : TEXT);
    }
}
