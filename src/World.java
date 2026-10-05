import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class World {
    private final BufferedImage background;
    private final Rectangle[] obstacles = {
        new Rectangle(0, 0, 1536, 416),
        new Rectangle(502, 416, 496, 35),
        new Rectangle(50, 380, 244, 281),
        new Rectangle(1060, 354, 132, 190),
        new Rectangle(1156, 650, 344, 95),
        new Rectangle(1275, 476, 100, 394),
        new Rectangle(1209, 744, 245, 77)
    };

    public World() {
        Path imagePath = Path.of("assets", "maps", "university-courtyard.png");
        try {
            background = ImageIO.read(imagePath.toFile());
            if (background == null) throw new IOException("Unsupported image format");
        } catch (IOException error) {
            throw new IllegalStateException("Cannot load map: " + imagePath.toAbsolutePath(), error);
        }
    }

    public int width() { return background.getWidth(); }
    public int height() { return background.getHeight(); }
    public double spawnX() { return width() / 2.0; }
    public double spawnY() { return height() * 0.66; }

    public boolean canStand(double x, double y, int halfSize) {
        if (x - halfSize < 0 || y - halfSize < 0 || x + halfSize > width() || y + halfSize > height()) return false;
        for (Rectangle obstacle : obstacles) {
            if (obstacle.intersects(x - halfSize, y - halfSize, halfSize * 2, halfSize * 2)) return false;
        }
        return true;
    }

    public void draw(Graphics2D g) {
        g.drawImage(background, 0, 0, null);
    }
}
