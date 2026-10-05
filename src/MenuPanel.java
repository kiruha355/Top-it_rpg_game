import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    private static final int TITLE_SCALE = 7;
    private static final Color TITLE_COLOR = new Color(230, 240, 250);
    private static final Color TITLE_SHADOW = new Color(56, 76, 99);
    private final PixelFont title = new PixelFont("Топ топ айти");

    public MenuPanel(Runnable onPlay) {
        setPreferredSize(new Dimension(800, 556));
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        PixelButton play = new PixelButton("Играть");
        play.addActionListener(event -> onPlay.run());
        add(play);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - title.width(TITLE_SCALE)) / 2;
        int y = getHeight() / 2 - 130;
        g.setColor(TITLE_SHADOW);
        title.draw(g, x + TITLE_SCALE, y + TITLE_SCALE, TITLE_SCALE);
        g.setColor(TITLE_COLOR);
        title.draw(g, x, y, TITLE_SCALE);
    }
}
