import java.awt.Graphics;
import java.util.Locale;
import java.util.Map;

public final class PixelFont {
    private static final int WIDTH = 5;
    private static final int HEIGHT = 7;
    // каждая буква это сетка 5x7, где 1 закрашенный пиксель. Здесь только буквы, которые сейчас нужны
    private static final Map<Character, String[]> GLYPHS = Map.ofEntries(
        Map.entry('И', new String[]{"10001", "10001", "10011", "10101", "11001", "10001", "10001"}),
        Map.entry('Г', new String[]{"11111", "10000", "10000", "10000", "10000", "10000", "10000"}),
        Map.entry('Р', new String[]{"11110", "10001", "10001", "11110", "10000", "10000", "10000"}),
        Map.entry('А', new String[]{"01110", "10001", "10001", "11111", "10001", "10001", "10001"}),
        Map.entry('Т', new String[]{"11111", "00100", "00100", "00100", "00100", "00100", "00100"}),
        Map.entry('Ь', new String[]{"10000", "10000", "10000", "11110", "10001", "10001", "11110"}),
        Map.entry('О', new String[]{"01110", "10001", "10001", "10001", "10001", "10001", "01110"}),
        Map.entry('П', new String[]{"11111", "10001", "10001", "10001", "10001", "10001", "10001"}),
        Map.entry('Й', new String[]{"01010", "00100", "10001", "10011", "10101", "11001", "10001"}),
        Map.entry(' ', new String[]{"00000", "00000", "00000", "00000", "00000", "00000", "00000"}),
        Map.entry('В', new String[]{"11110", "10001", "10001", "11110", "10001", "10001", "11110"}),
        Map.entry('Е', new String[]{"11111", "10000", "10000", "11110", "10000", "10000", "11111"}),
        Map.entry('Д', new String[]{"01110", "01010", "01010", "01010", "01010", "11111", "10001"}),
        Map.entry('Л', new String[]{"00111", "01001", "01001", "01001", "01001", "01001", "10001"}),
        Map.entry('!', new String[]{"00100", "00100", "00100", "00100", "00100", "00000", "00100"})
    );
    private final String[][] letters;

    public PixelFont(String text) {
        String uppercase = text.toUpperCase(Locale.ROOT);
        letters = new String[uppercase.length()][];
        for (int i = 0; i < uppercase.length(); i++) {
            letters[i] = GLYPHS.get(uppercase.charAt(i));
            if (letters[i] == null) throw new IllegalArgumentException("Unsupported glyph: " + uppercase.charAt(i));
        }
    }

    // на букву 5 пикселей и 1 на промежуток, после последней промежутка нет
    public int width(int scale) { return (letters.length * (WIDTH + 1) - 1) * scale; }
    public int height(int scale) { return HEIGHT * scale; }

    public void draw(Graphics g, int x, int y, int scale) {
        for (String[] letter : letters) {
            for (int row = 0; row < HEIGHT; row++) {
                for (int col = 0; col < WIDTH; col++) {
                    if (letter[row].charAt(col) == '1') g.fillRect(x + col * scale, y + row * scale, scale, scale);
                }
            }
            x += (WIDTH + 1) * scale;
        }
    }
}
