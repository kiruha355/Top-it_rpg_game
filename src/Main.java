import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing надо создавать в потоке интерфейса
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Топ топ айти");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // по кнопке "Играть" меню заменяется на игру
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
