import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private final World world = new World();
    private final Player player = new Player(world);
    private final Camera camera = new Camera();
    private final Npc npc = new Npc(1010, 620);
    private final PixelButton talk = new PixelButton("Диалог");
    private final DialoguePanel dialoguePanel = new DialoguePanel(() -> closeDialogue());
    private boolean dialogueActive;
    private final Timer timer;
    private final Set<String> held = new HashSet<>();
    private long previousTime = System.nanoTime();

    public GamePanel() {
        setPreferredSize(new Dimension(800, 556));
        setBackground(Color.BLACK);
        setLayout(null);
        talk.addActionListener(event -> openDialogue());
        add(talk);
        add(dialoguePanel);
        updateInteraction();
        for (String key : new String[]{"W", "A", "S", "D", "UP", "LEFT", "DOWN", "RIGHT", "R"}) {
            bind(key, false);
            bind(key, true);
        }
        addFocusListener(new FocusAdapter() {
            @Override public void focusLost(FocusEvent event) { held.clear(); }
        });
        setFocusable(true);
        timer = new Timer(16, event -> update());
    }

    @Override public void doLayout() {
        talk.setBounds(getWidth() - 196, getHeight() - 64, 180, 44);
        dialoguePanel.setBounds(12, getHeight() - 144, getWidth() - 24, 132);
    }

    private void openDialogue() {
        if (!npc.isNear(player) || dialogueActive) return;
        dialogueActive = true;
        held.clear();
        updateInteraction();
        repaint();
    }

    private void closeDialogue() {
        dialogueActive = false;
        held.clear();
        updateInteraction();
        requestFocusInWindow();
        repaint();
    }

    private void updateInteraction() {
        talk.setVisible(!dialogueActive && npc.isNear(player));
        dialoguePanel.setVisible(dialogueActive);
    }

    @Override public void addNotify() {
        super.addNotify();
        previousTime = System.nanoTime();
        timer.start();
    }

    @Override public void removeNotify() {
        timer.stop();
        held.clear();
        super.removeNotify();
    }

    private void bind(String key, boolean released) {
        String action = (released ? "released " : "pressed ") + key;
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(action), action);
        getActionMap().put(action, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent event) {
                if (dialogueActive) {
                    held.remove(key);
                    return;
                }
                if (released) held.remove(key);
                else held.add(key);
                if (key.equals("R") && !released) player.reset(world);
            }
        });
    }

    private boolean down(String letter, String arrow) { return held.contains(letter) || held.contains(arrow); }

    private void update() {
        long now = System.nanoTime();
        double seconds = Math.min((now - previousTime) / 1_000_000_000.0, 0.05);
        previousTime = now;
        if (!isShowing() || !javax.swing.SwingUtilities.getWindowAncestor(this).isFocused()) held.clear();
        double dx = (down("D", "RIGHT") ? 1 : 0) - (down("A", "LEFT") ? 1 : 0);
        double dy = (down("S", "DOWN") ? 1 : 0) - (down("W", "UP") ? 1 : 0);
        if (!dialogueActive) player.move(world, dx, dy, seconds);
        updateInteraction();
        repaint();
    }

    @Override protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        camera.follow(player, world, getWidth(), getHeight());
        camera.apply(g);
        world.draw(g);
        npc.draw(g, !dialogueActive && npc.isNear(player));
        player.draw(g);
        g.dispose();
    }
}
