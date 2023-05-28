import javax.swing.*;
import java.awt.*;

public class BouncingBall extends JPanel {
    private int ballSize = 100; // Diameter of the ball
    private int originalSize = ballSize;
    private int ballY; // Y position of the ball
    private int direction = 1; // 1 for moving down, -1 for moving up
    private int height = 600; // Height of the panel
    private int speed = 3;
    private boolean hitFrame = false, animated = true, halfAnimated = false;
    private int xCoef = 0, yCoef = 0;



    void moveBall() {
        if(hitFrame && !animated) {
            if(direction == -1) { //ball must go up now
                if(!halfAnimated) {//if not animated to half
                    ballSize-=speed; //decrease size
                    xCoef+=speed;
                    yCoef+=speed;
                }
                else {
                    ballSize+=speed; //if size is half of original then go back
                    xCoef-=speed;
                    yCoef-=speed;
                }
                if(ballSize <= originalSize/2) { //if animated halfly
                    halfAnimated = true;
                }
                if(ballSize >= originalSize) {
                    ballSize = originalSize;
                    animated = true;
                    halfAnimated = false;
                }
            } else { //direction =1, ball hit top and must go down
                if(!halfAnimated) {//if not animated to half
                    ballSize-=speed; //decrease size
                    xCoef+=speed;
                    yCoef-=speed/4;
                }
                else {
                    ballSize+=speed; //if size is half of original then go back
                    xCoef-=speed;
                    yCoef+=speed/4;
                }
                if(ballSize <= originalSize/2) { //if animated halfly
                    halfAnimated = true;
                }
                if(ballSize >= originalSize) {
                    ballSize = originalSize;
                    animated = true;
                    halfAnimated = false;
                    xCoef = 0;
                    yCoef = 0;
                }
            }
            return;
        }
        if (direction == 1) { // Ball moving down
            if (ballY + ballSize >= height) {
                // Ball touches the bottom, change direction
                direction = -1;
                hitFrame = true;
                animated = false;
                ballY = height - ballSize;
                xCoef = 0;
                yCoef = 0;
            } else {
                // Move ball down
                ballY += direction*speed;
            }
        } else if (direction == -1) { // Ball moving up
            if (ballY <= 0) {
                // Ball reaches the top, change direction
                ballY = 0;
                xCoef = 0;
                yCoef = 0;
                direction = 1;
                hitFrame = true;
                animated = false;
            } else {
                // Move ball up
                ballY += direction*speed;
            }
        }
    }

    public BouncingBall() {
        ballY = ballSize; // Initialize ball position at the top of the panel
        Timer timer = new Timer(10, e -> {
            Toolkit.getDefaultToolkit().sync();
            moveBall();
            repaint(); // Redraw the panel
        });
        timer.start(); // Start the animation timer
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval((getWidth()/2-ballSize/2)-xCoef/2, ballY+yCoef, originalSize, ballSize); // Draw the ball
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, height); // Set the panel size
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ball Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BouncingBall());
        frame.pack();
        frame.setVisible(true);
    }
}