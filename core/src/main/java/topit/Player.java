package topit;

public class Player {
    public static final int HALF_SIZE = 8;
    private static final float SPEED = 190; // пикселей в секунду
    private float x; // центр игрока на карте
    private float y;

    public Player(World world) { reset(world); }
    public float x() { return x; }
    public float y() { return y; }

    public void move(World world, float dx, float dy, float seconds) {
        float length = (float) Math.hypot(dx, dy);
        if (length == 0) return;
        // делим на длину, чтобы по диагонали не ходить быстрее
        float nextX = x + dx / length * SPEED * seconds;
        float nextY = y + dy / length * SPEED * seconds;
        // оси проверяем отдельно, тогда у стены игрок скользит вдоль неё, а не застревает
        if (world.canStand(nextX, y, HALF_SIZE)) x = nextX;
        if (world.canStand(x, nextY, HALF_SIZE)) y = nextY;
    }

    public void reset(World world) { x = world.spawnX(); y = world.spawnY(); }
}
