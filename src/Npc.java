import java.awt.Color;
import java.awt.Graphics2D;

public class Npc {
    public static final Color COLOR = new Color(119, 187, 220);
    private static final int SIZE = 20;
    private static final int INTERACTION_RADIUS = 100;
    private final double x;
    private final double y;
    private final PixelFont greeting = new PixelFont("Привет!");

    public Npc(double x, double y) { this.x = x; this.y = y; }

    public boolean isNear(Player player) {
        double dx = player.x() - x;
        double dy = player.y() - y;
        return dx * dx + dy * dy <= INTERACTION_RADIUS * INTERACTION_RADIUS;
    }

    public void draw(Graphics2D g, boolean showGreeting) {
        int px = (int) Math.round(x);
        int py = (int) Math.round(y);
        g.setColor(Color.BLACK);
        g.fillRect(px - SIZE / 2 - 2, py - SIZE / 2 - 2, SIZE + 4, SIZE + 4);
        g.setColor(COLOR);
        g.fillRect(px - SIZE / 2, py - SIZE / 2, SIZE, SIZE);
        if (showGreeting) {
            int labelWidth = greeting.width(2);
            int left = px - labelWidth / 2 - 8;
            int top = py - 48;
            g.setColor(Color.WHITE);
            g.fillRect(left - 2, top - 2, labelWidth + 20, 32);
            g.setColor(Color.BLACK);
            g.fillRect(left, top, labelWidth + 16, 28);
            g.fillRect(px - 3, top + 28, 6, 6);
            g.setColor(Color.WHITE);
            greeting.draw(g, left + 8, top + 7, 2);
        }
    }
}
