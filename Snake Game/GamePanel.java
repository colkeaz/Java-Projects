import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel; 
import javax.swing.JButton;

public class GamePanel extends JPanel implements ActionListener {
    JButton playAgainButton;
    JButton exitButton;

    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 900;
    static final int UNIT_SIZE = 25; // size of each unit in the game
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100; // speed of the game

    final int x[] = new int[GAME_UNITS]; // x coordinates of snake body parts
    final int y[] = new int[GAME_UNITS]; // y coordinates of snake body parts
    int bodyParts = 5;  // initial body parts of the snake
    int unitsEaten;
    int appleX; // x coordinate of the apple
    int appleY; // y coordinate of the apple
    char direction = 'R'; // initial direction of the snake
    boolean running = false;
    Timer timer;
    Random random; 

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        playAgainButton = new JButton("Play Again");
        exitButton = new JButton("Exit");
        playAgainButton.setFocusable(false);
        exitButton.setFocusable(false);

        playAgainButton.addActionListener(_ -> restartGame());
        exitButton.addActionListener(_ -> System.exit(0));
    }
    public void startGame(){
        newApple();
        running=true;
        timer = new Timer(DELAY, this);
        timer.start();  
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(running){
            /*(
            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i=0; i<bodyParts; i++){
                if(i==0){
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }else {
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Times New Roman", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+ unitsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ unitsEaten))/2, g.getFont().getSize());    

        }else{
            gameOver(g);
        }
    }

    public void newApple(){
         appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
         appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        for(int i=bodyParts; i>0; i--){
            x[i]= x[i-1];
            y[i]= y[i-1];
        }

        switch(direction){
            case 'U':
                y[0]= y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]= y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]= x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
            
        }

        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH - UNIT_SIZE;
        } else if (x[0] >= SCREEN_WIDTH) {
            x[0] = 0;
        }
        if (y[0] < 0) {
            y[0] = SCREEN_HEIGHT - UNIT_SIZE;
        } else if (y[0] >= SCREEN_HEIGHT) {
            y[0] = 0;
        }
    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
           bodyParts++;
           unitsEaten++;
           newApple();
        }
    }

    public void checkCollisions(){
        // check if head collides with body
        for(int i=bodyParts; i>0; i--){
            if((x[0]==x[i]) && (y[0]==y[i])){
                running = false;
            }
        }


        if(!running){
            timer.stop(); 
        }

    }

    public void gameOver(Graphics g){
        //Gamve Over text
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        //Score
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        int scoreY = SCREEN_HEIGHT/2 + metrics.getHeight() + -10; // 20 pixels below "Game Over"
        g.drawString("Score: " + unitsEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + unitsEaten))/2, scoreY);

        //show options
        this.setLayout(null); // Use absolute positioning
        int buttonYStart = scoreY + 30; // 30 pixels below the score
        playAgainButton.setBounds(SCREEN_WIDTH/2 - 100, buttonYStart, 200, 40);
        exitButton.setBounds(SCREEN_WIDTH/2 - 100, buttonYStart + 50, 200, 40);
        if (playAgainButton.getParent() == null) {
            this.add(playAgainButton);
            this.add(exitButton);
        }
        this.repaint();
    }   

    public void restartGame(){
        //Remove buttons
        this.remove(playAgainButton);
        this.remove(exitButton);

        //Reset game
        bodyParts = 5;
        unitsEaten = 0;
        direction = 'R';

        for(int i =0; i< bodyParts; i++){
            x[i] = 0;
            y[i] = 0;
        }
        startGame();
        this.requestFocusInWindow(); // Refocus on the game panel
        repaint();    
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
        
    }

    

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_A:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                 case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

 
}        
