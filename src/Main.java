import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Топ топ айти");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setContentPane(new MenuPanel(() -> {
                GamePanel game = new GamePanel();
                window.setContentPane(game);
                window.revalidate();
                window.repaint();
                game.requestFocusInWindow();
            }));
            window.pack();
            window.setResizable(false);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
