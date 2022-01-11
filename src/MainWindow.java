import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 640);
        setLocation(400, 400);
        setResizable(false);
        add(new Game());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
