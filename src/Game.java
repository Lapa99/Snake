import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

public class Game extends JPanel implements ActionListener{
    private final int SIZE = 640;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private Image background;
    private Image bomb;
    private int appleX;
    private int appleY;
    private int[] bombX = new int[4];
    private int[] bombY = new int[4];
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private JButton button;

    public Game() {
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        setBackground(Color.white);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 96 - i * DOT_SIZE;
            y[i] = 96;
        }
        timer = new Timer(125,this);
        timer.start();
        createApple();
        createBomb();
    }

    public void loadImages() {
        ImageIcon imageIconBackground = new ImageIcon("background.jpg");
        background = imageIconBackground.getImage();
        ImageIcon imageIconApple = new ImageIcon("apple.png");
        apple = imageIconApple.getImage();
        ImageIcon imageIconBomb = new ImageIcon("bomb.png");
        bomb = imageIconBomb.getImage();
        ImageIcon imageIconDot = new ImageIcon("dot.png");
        dot = imageIconDot.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        if (inGame) {
            for (int i = 0; i < 4; i++) {
                g.drawImage(bomb, bombX[i], bombY[i], this);
            }
            g.drawImage(apple, appleX, appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i],this);
            }
        } else {
            String str = "Game Over";
            Font font = new Font("Font", 12, 45);
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(str,200, SIZE/2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
            createBomb();
        }
    }

    public void checkBomb() {
        for (int i = 0; i < 4; i++) {
            if (x[0] == bombX[i] && y[0] == bombY[i]) {
                inGame = false;
            }
        }
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void createBomb() {
        for (int i = 0; i < 4; i++) {
            bombX[i] = new Random().nextInt(20) * DOT_SIZE;
            bombY[i] = new Random().nextInt(20) * DOT_SIZE;
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE){
            inGame = false;
        }
        if (x[0] < 0){
            inGame = false;
        }
        if (y[0] > SIZE){
            inGame = false;
        }
        if (y[0] < 0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkBomb();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }


}
