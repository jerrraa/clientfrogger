import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class FroggerClient extends JFrame implements KeyListener {

	//
	private Frog1 frog1;
	private Container content;
	private JLabel frog1Label;
	private ImageIcon frog1Image;
	private PanelData NewPanel = new PanelData();
	
	private JLabel VEHICLElabel, VEHICLElabel2, VEHICLElabel3;
	private ImageIcon vehicleicon = new ImageIcon(getClass().getResource("textures/car.png"));
	private ImageIcon vehicleicon2 = new ImageIcon(getClass().getResource("textures/car2.png"));
	private Vehicle vehiclelane[];
	private ReverseVehicle vehiclelane1[];
	private Vehicle vehiclelane2[];
	
	private JLabel LOGlabel, LOGlabel2, LOGlabel3, LOGlabel4, LOGlabel5;
	private Log LogLane[];
	private Log LogLane1[];
	private Log LogLane2[];
	private Log LogLane3[];
	private Log LogLane4[];
	private ImageIcon logicon = new ImageIcon(getClass().getResource("textures/log.png"));
	
	
	private int offset = 300;
	private int heightveh = 90;
	private int widthveh = 127;
	//life and score
	private JLabel LifeText, ScoreText;
	private int lifes = 3;
	private int score = 50;
	
	private int xreset = 400;
	private int yreset = 914;
	
	public static void main(String[] args) throws IOException {
		FroggerClient game = new FroggerClient();
		game.setVisible(true);
	}
//connect and insert contents
	
	public FroggerClient() throws IOException {

		DisplayScreen();
		
		final int CLIENT_PORT = 5656;
		final int SERVER_PORT = 5556;	
		final ServerSocket client = new ServerSocket(CLIENT_PORT);
				
		//set up listening server
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
					
					System.out.println("Waiting for server responses...");
					while(true) {
						Socket s2;
						try {
							s2 = client.accept();
							GameClient myService = new GameClient (s2);
							Thread t = new Thread(myService);
							t.start();
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("client connected");
						
					}

				}
			}
		});
		t1.start( );

		//set up a communication socket
		Socket s = new Socket("localhost", SERVER_PORT);
		
		
		
		

	}
	public void Resetfrogger() throws IOException {
		// add a life counter and update position
		lifes--;
		frog1.SetLives(lifes);
		DataScore.INSTANCE.MinusScore(score);
		NewPanel.SetNewScore(DataScore.INSTANCE.GetScore());
		LifeText.setText("Lifes: " + frog1.GetLives());
		ScoreText.setText("Score: " + DataScore.INSTANCE.GetScore());
		frog1.setX(xreset); frog1.setY(yreset);
		frog1Label.setLocation(frog1.getX(), frog1.getY());
		//if it reaches 0 we kill the frog
		if (frog1.GetLives() <= 0) {
			DataScore.INSTANCE.SetScore(0);
			Restartgame();
		}
	}
	public void AddToScore() {
		// scoring system
		// for our scoring system we use a singleton instance
		DataScore.INSTANCE.addScore(score);
		ScoreText.setText("Score: " + DataScore.INSTANCE.GetScore());
		NewPanel.GeneratePanel();
		NewPanel.ResetFunction(this);
		NewPanel.SetNewScore(DataScore.INSTANCE.GetScore());
		
	}
	public void Restartgame() throws IOException {
		//resets game
		FroggerClient.this.setVisible(false);
		FroggerClient.this.dispose();
		new FroggerClient();
		FroggerClient.main(null);
	}
	public void DisplayScreen() {
		//declare player 1 frog
				frog1 = new Frog1();
				frog1.setX(460); frog1.setY(914);
				frog1.setWidth(67); frog1.setHeight(55);
				frog1.setImage("greenfrog.png");
				frog1.SetLives(lifes);
				frog1.GetGame(this);
				frog1.setMoving(true);
				//set up screen
				setSize(Gameproperties.SCREEN_WIDTH, Gameproperties.SCREEN_HEIGHT+45);
				content = getContentPane();
				content.setBackground(Color.white);
				setLayout(null);
				
				//frog label
				frog1Label = new JLabel();
				frog1Image = new ImageIcon(getClass().getResource("textures/greenfrog.png"));
				frog1Label.setIcon(frog1Image);
				frog1Label.setSize(frog1.getWidth(), frog1.getHeight());
				frog1Label.setLocation(frog1.getX(), frog1.getY());
				// score label
				LifeText = new JLabel("");
				LifeText.setFont(new Font("Calibri", Font.BOLD, 30));
				LifeText.setText("Lifes: " + lifes);
				LifeText.setForeground(Color.WHITE);
				LifeText.setSize(200, 200);
				LifeText.setLocation(10, -56);
				//score label
				ScoreText = new JLabel("");
				ScoreText.setFont(new Font("Calibri", Font.BOLD, 30));
				ScoreText.setText("Score: " + DataScore.INSTANCE.GetScore());
				ScoreText.setForeground(Color.WHITE);
				ScoreText.setSize(200, 200);
				ScoreText.setLocation(10, -27);
				//background of panel
				JLabel Backgroundlab = new JLabel();
				ImageIcon Backgroundimg = new ImageIcon(getClass().getResource("textures/background.png"));
				Backgroundlab.setIcon(Backgroundimg);
				Backgroundlab.setSize(Gameproperties.SCREEN_WIDTH, Gameproperties.SCREEN_HEIGHT);
				Backgroundlab.setLocation(0,5);
				setLocationRelativeTo(null);  
				//insert all functions
				add(LifeText);
				add(ScoreText);
				//InsertVehicleRows();
				//InsertVehicleRows1();
				//InsertVehicleRows2();
				add(frog1Label);
				//InsertLogRows();
				//InsertLogRows2();
				//InsertLogRows3();
				//InsertLogRows4();
				//InsertLogRows5();
				//ThreadForLanes();
				add(Backgroundlab);
				//user input
				content.addKeyListener(this);
				content.setFocusable(true);
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
	
	public void DetectBorder(int x, int y) {
		//right side of the screen detection
		if (x >= 973) {
			x = 933;
		} 
		//bottom side of the screen
		if (y >= 1000) {
			y = 914;
		}
		//left side of the screen
		if (x <= -30) {
			x = -10;
		}
		//top side of the screen
		if (y <= -50) {
			y = 14;
		}
		frog1.setX(x);
		frog1.setY(y);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int x = frog1.getX(); int y = frog1.getY();
		if (frog1.getMoving() == true) {
		//modify position
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			y -= Gameproperties.CHARACTER_STEP;

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			y += Gameproperties.CHARACTER_STEP;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			x -= Gameproperties.CHARACTER_STEP-49;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			x += Gameproperties.CHARACTER_STEP-49;
		} else {
			System.out.println("invalid operation");
		}
		//check if it reaches out of bounds
		DetectBorder(x, y);
		frog1Label.setLocation(frog1.getX(), frog1.getY());
		//a function in frog1 class to check if it interacts with top grass
		frog1.CheckforTop();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
