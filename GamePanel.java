import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; // unit size for all items in side grid example the size of apple
	static final int  GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 150;  //it will slow the game if u increase delay
	final int[] x = new int[GAME_UNITS];  //this is x axis body part of snake
	final int[] y = new int[GAME_UNITS];  //this is y axis body part of snake
	int bodyParts = 6;   //It is  the initial body part size of Snake
	int applesEaten;  //this is initial no of apple eaten
	int appleX;  //this is the apples appears on x axis randomly
	int appleY;  //this is the apples appears on y axis randomly
	char directon = 'R'; //initially the direction of snake is Right
	boolean running = false;
    Timer timer;
    Random random;

    GamePanel()
    {
    	random = new Random();
    	this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
    	this.setBackground(Color.black);
    	this.setFocusable(true);
    	this.addKeyListener(new MyKeyAdapter());
    	startGame();
    	
	  
	}
    public void startGame()
    {
    	newApple();
    	running = true;
    	timer = new Timer(DELAY, this);
    	timer.start();
    	
    }
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	draw(g); 
    	
    }
    public void draw(Graphics g)
    {
    	if(running)
    	{
    		for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++)  //the purpose of this to make grid to get better understanding of units
        	{
        		g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
        		g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
        	}
        	g.setColor(Color.red);
        	g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //dimentions of apple
        	
        	//draw
        	for(int i=0; i<bodyParts; i++)
        	{
        		if(i==0)//head of snake
        		{
        			g.setColor(Color.green);
        			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        		}
        		else 
        		{
        			g.setColor(new Color(45,180,0));
        			g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        		}
        	}
        	g.setColor(Color.red);
        	g.setFont(new Font("Ink Free",Font.BOLD,40));
        	FontMetrics metrics = getFontMetrics(g.getFont());
        	g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
    	}
    	else
    	{
    		gameOver(g);
    	}
    	
    }
    public void newApple()
    {
    	appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    	appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move()
    {
    	for(int i = bodyParts; i>0; i--)
    	{
    		//Sift the bodyPart of Snake one by one
    		x[i] = x[i-1];
    		y[i] = y[i-1];
    		
    	}
    	
    	//directios of snake
    	switch(directon)
    	{
    	   case 'U':
    		   y[0] = y[0] - UNIT_SIZE;
    		   break;
    	   case 'D':
    		   y[0] = y[0] + UNIT_SIZE;
    		   break;
    	   case 'L':
    		   x[0] = x[0] - UNIT_SIZE;
    		   break;
    	   case 'R':
    		   x[0] = x[0] + UNIT_SIZE;
    		   break;
    		   
    	}
    }
    public void checkApple()
    {
    	if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
    }
    public void checkCollisions()
    {
    	//checks if head of Snake collides with its body
    	for(int i=-bodyParts; i>0; i--)
    	{
    		if((x[0]==x[i]) && (y[0]==y[i]))
    		{
    			running = false;
    		}
    	}
    	//check if head collides with left border
    	if(x[0] < 0)
    	{
    		running = false;
    	}
    	//check if head collides with right border
    	if(x[0] > SCREEN_WIDTH)
    	{
    		running = false;
    	}
    	//check if head collides with top border
    	if(y[0] < 0)
    	{
    		running = false;
    	}
    	//check if head collides with bottom border
    	if(y[0] > SCREEN_HEIGHT)
    	{
    		running = false;
    	}
    	
    	if(!running)
    	{
    		timer.stop();
    	}
    }
    public void gameOver(Graphics g)
    {
    	//Game Over notification
    	g.setColor(Color.red);
    	g.setFont(new Font("Ink Free",Font.BOLD,75));
    	FontMetrics metrics = getFontMetrics(g.getFont());
    	g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    	
    	
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			    case KeyEvent.VK_LEFT:
			    {
			    	if(directon != 'R')
			    	{
			    		directon = 'L';
			    	}
			    }
			    break;
			    case KeyEvent.VK_RIGHT:
			    {
			    	if(directon != 'L')
			    	{
			    		directon = 'R';
			    	}
			    }
			    break;
			    case KeyEvent.VK_UP:
			    {
			    	if(directon != 'D')
			    	{
			    		directon = 'U';
			    	}
			    }
			    break;
			    case KeyEvent.VK_DOWN:
			    {
			    	if(directon != 'U')
			    	{
			    		directon = 'D';
			    	}
			    }
			    break;
			}
		}
	}

}
