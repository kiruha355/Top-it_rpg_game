import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DialoguePanel extends JPanel {
    private static final Color BACKGROUND = new Color(14, 19, 28);
    private final PixelFont greeting = new PixelFont("Привет!");
    private final PixelButton next = new PixelButton("Далее");

    public DialoguePanel(Runnable onAdvance) {
        setLayout(null);
        setBackground(BACKGROUND);
        next.addActionListener(event -> onAdvance.run());
        add(next);
    }

    @Override public void doLayout() {
        next.setBounds(getWidth() - 184, getHeight() - 56, 168, 40);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), 3);
        g.fillRect(0, getHeight() - 3, getWidth(), 3);
        g.fillRect(0, 0, 3, getHeight());
        g.fillRect(getWidth() - 3, 0, 3, getHeight());
        g.fillRect(20, 25, 60, 60);
        g.setColor(Color.BLACK);
        g.fillRect(23, 28, 54, 54);
        g.setColor(Npc.COLOR);
        g.fillRect(32, 37, 36, 36);
        g.setColor(Color.WHITE);
        greeting.draw(g, 104, 37, 3);
    }
}
