package topit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable {
    private final Texture background = new Texture("maps/university-courtyard.png");
    // стены подобраны вручную по картинке: здание, деревья, самолёт
    // в LibGDX y растёт вверх, поэтому y здесь отсчитывается от нижнего края карты
    private final Rectangle[] obstacles = {
        new Rectangle(0, 608, 1536, 416),
        new Rectangle(502, 573, 496, 35),
        new Rectangle(50, 363, 244, 281),
        new Rectangle(1060, 480, 132, 190),
        new Rectangle(1156, 279, 344, 95),
        new Rectangle(1275, 154, 100, 394),
        new Rectangle(1209, 203, 245, 77)
    };
    private final Rectangle body = new Rectangle();

    public int width() { return background.getWidth(); }
    public int height() { return background.getHeight(); }
    public float spawnX() { return width() / 2f; }
    public float spawnY() { return height() * 0.34f; }
    public Texture background() { return background; }

    // можно ли поставить квадрат с центром (x, y): не вылезает за карту и не задевает стены
    public boolean canStand(float x, float y, float halfSize) {
        if (x - halfSize < 0 || y - halfSize < 0 || x + halfSize > width() || y + halfSize > height()) return false;
        body.set(x - halfSize, y - halfSize, halfSize * 2, halfSize * 2);
        for (Rectangle obstacle : obstacles) {
            if (obstacle.overlaps(body)) return false;
        }
        return true;
    }

    @Override public void dispose() { background.dispose(); }
}
