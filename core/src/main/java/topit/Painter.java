package topit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

// общий SpriteBatch и белый пиксель, из которого растягиваются прямоугольники любого цвета
public class Painter implements Disposable {
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture pixel;

    public Painter() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public static Color rgb(int red, int green, int blue) { return new Color(red / 255f, green / 255f, blue / 255f, 1); }
    public SpriteBatch batch() { return batch; }

    // рисовать можно только между batch.begin() и batch.end()
    public void fillRect(float x, float y, float width, float height, Color color) {
        batch.setColor(color);
        batch.draw(pixel, x, y, width, height);
        batch.setColor(Color.WHITE);
    }

    @Override public void dispose() {
        batch.dispose();
        pixel.dispose();
    }
}
