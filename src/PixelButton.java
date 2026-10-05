import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;

public class PixelButton extends JButton {
    private static final Color FACE = new Color(76, 94, 115);
    private static final Color HOVER_FACE = new Color(99, 137, 174);
    private static final Color GRAIN = new Color(83, 101, 122);
    private static final Color HOVER_GRAIN = new Color(107, 145, 182);
    private static final Color LIGHT_EDGE = new Color(130, 151, 175);
    private static final Color HOVER_EDGE = new Color(177, 209, 236);
    private static final Color DARK_EDGE = new Color(35, 46, 61);
    private static final Color TEXT = new Color(233, 237, 242);
    private static final Color HOVER_TEXT = new Color(220, 245, 255);
    private static final Color TEXT_SHADOW = new Color(29, 38, 50);
    private static final int SCALE = 3;
    private final PixelFont label;

    public PixelButton(String text) {
        super(text);
        label = new PixelFont(text);
        setPreferredSize(new Dimension(360, 52));
        setRolloverEnabled(true);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override protected void paintComponent(Graphics g) {
        boolean highlighted = getModel().isRollover();
        boolean pressed = getModel().isPressed() && getModel().isArmed();
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(pressed ? DARK_EDGE : highlighted ? HOVER_EDGE : LIGHT_EDGE);
        g.fillRect(2, 2, width - 4, height - 4);
        g.setColor(pressed ? LIGHT_EDGE : DARK_EDGE);
        g.fillRect(4, height - 6, width - 6, 4);
        g.fillRect(width - 6, 4, 4, height - 6);
        g.setColor(highlighted ? HOVER_FACE : FACE);
        g.fillRect(6, 6, width - 12, height - 12);
        g.setColor(highlighted ? HOVER_GRAIN : GRAIN);
        for (int row = 0; row < (height - 12) / 4; row++) {
            for (int col = 0; col < (width - 12) / 4; col++) {
                if ((row * 13 + col * 7) % 11 < 2) g.fillRect(6 + col * 4, 6 + row * 4, 4, 4);
            }
        }
        int offset = pressed ? 2 : 0;
        int x = (width - label.width(SCALE)) / 2 + offset;
        int y = (height - label.height(SCALE)) / 2 + offset;
        g.setColor(TEXT_SHADOW);
        label.draw(g, x + SCALE, y + SCALE, SCALE);
        g.setColor(highlighted ? HOVER_TEXT : TEXT);
        label.draw(g, x, y, SCALE);
    }
}
