import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
    private double x; // центр игрока на карте
    private double y;
    private static final int HALF_SIZE = 8;
    private static final double SPEED = 190; // пикселей в секунду

    public Player(World world) { reset(world); }
    public double x() { return x; }
    public double y() { return y; }

    public void move(World world, double dx, double dy, double seconds) {
        double length = Math.hypot(dx, dy);
        if (length == 0) return;
        // делим на длину, чтобы по диагонали не ходить быстрее
        double nextX = x + dx / length * SPEED * seconds;
        double nextY = y + dy / length * SPEED * seconds;
        // оси проверяем отдельно, тогда у стены игрок скользит вдоль неё, а не застревает
        if (world.canStand(nextX, y, HALF_SIZE)) x = nextX;
        if (world.canStand(x, nextY, HALF_SIZE)) y = nextY;
    }

    public void reset(World world) { x = world.spawnX(); y = world.spawnY(); }

    public void draw(Graphics2D g) {
        int px = (int) Math.round(x);
        int py = (int) Math.round(y);
        g.setColor(Color.BLACK);
        g.fillRect(px - HALF_SIZE, py - HALF_SIZE, HALF_SIZE * 2, HALF_SIZE * 2);
    }
}
