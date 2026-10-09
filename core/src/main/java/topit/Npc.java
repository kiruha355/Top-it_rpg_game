package topit;

public class Npc {
    public static final int SIZE = 20;
    private static final int INTERACTION_RADIUS = 100;
    private final float x;
    private final float y;

    public Npc(float x, float y) { this.x = x; this.y = y; }
    public float x() { return x; }
    public float y() { return y; }

    // сравниваем квадраты расстояний, так не нужно считать корень
    public boolean isNear(Player player) {
        float dx = player.x() - x;
        float dy = player.y() - y;
        return dx * dx + dy * dy <= INTERACTION_RADIUS * INTERACTION_RADIUS;
    }
}
