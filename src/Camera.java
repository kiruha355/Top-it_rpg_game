import java.awt.Graphics2D;

public class Camera {
    private int x;
    private int y;

    public void follow(Player player, World world, int viewportWidth, int viewportHeight) {
        x = clamp((int) Math.round(player.x() - viewportWidth / 2.0), world.width() - viewportWidth);
        y = clamp((int) Math.round(player.y() - viewportHeight / 2.0), world.height() - viewportHeight);
    }

    private int clamp(int position, int maximum) { return Math.max(0, Math.min(position, Math.max(0, maximum))); }
    public int x() { return x; }
    public int y() { return y; }
    public void apply(Graphics2D g) { g.translate(-x, -y); }
}
