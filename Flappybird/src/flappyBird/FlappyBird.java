package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener
{

	public static FlappyBird flappybird;

	public final int WIDTH = 1000;

	public final int HEIGHT = 800;

	public Renderer renderer;

	public Rectangle bird;

	public int ticks, yMotion, score;

	public ArrayList<Rectangle> columns;

	public boolean gameOver, started;

	public Random rand;

	public FlappyBird()
	{
		JFrame jframe = new JFrame();

		renderer = new Renderer();
		rand = new Random();

		Timer timer = new Timer(20, this);

		jframe.add(renderer);
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addKeyListener(this);
		jframe.setVisible(true);
		jframe.setTitle("Flappy Bird");
		jframe.setResizable(false);

		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();
		
		System.out.println("git works");

	}

	public void addColumn(boolean start)
	{
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);

		if (start)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		} else
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	public void paintColumn(Graphics g, Rectangle column)
	{
		g.setColor(Color.blue);
		g.fillRect(column.x, column.y, column.width, column.height);
	}

	public void jump()
	{
		if (gameOver)
		{
			bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;	

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
		}
		
		if (!started)
		{
			started = true;
		}
		else if (!gameOver)
		{
			if (yMotion > 0)
			{
				yMotion = 0; 
			}
			else yMotion -= 10;
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int speed = 5;

		ticks++;

		if (started)
		{

			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);
				column.x -= speed;
			}

			if (ticks % 2 == 0 && yMotion < 15)
			{
				yMotion += 2;
			}

			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);
				if (column.x + column.width < 0)
				{
					columns.remove(column);

					if (column.y == 0)
					{
						addColumn(false);
					}
				}
			}

			bird.y += yMotion + 6;

			for (Rectangle column : columns)
			{
				if(column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 -5 && bird.x + bird.width / 2 < column.x + column.width / 2 + 5)
				{
					score++;
				}
				if (column.intersects(bird))
				{
					gameOver = true;
					
					bird.x = column.x - bird.width;
				}
			}

			if (bird.y > HEIGHT - 160 || bird.y < 0)
			{
				gameOver = true;
			}
			
			if (bird.y + yMotion >= HEIGHT - 160)
			{
				bird.y = HEIGHT - 160 - bird.height;
			}
		}

		renderer.repaint();
	}

	public void repaint(Graphics g)
	{
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.green.darker());
		g.fillRect(0, HEIGHT - 150, WIDTH, 150);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 150, WIDTH, 20);

		g.setColor(Color.RED);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		for (Rectangle column : columns)
		{
			paintColumn(g, column);
		}
		
		g.setColor(Color.darkGray);
		g.setFont(new Font ("Arial", 1, 100));
		
		if (!started)
		{
			g.drawString("Click to start", 100, HEIGHT / 2 - 100);
		}
		else
		{
			g.drawString("",0,0);
		}
		
		if (gameOver)
		{
			g.drawString("Game over! You're bad!", 40, HEIGHT / 2 - 100);
		}
		
		if(!gameOver && started)
		{
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}

	}

	private char[] valueof(int score2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args)
	{
		flappybird = new FlappyBird();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{	
	}

	@Override
	public void mousePressed(MouseEvent e)
	{	
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{	
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
