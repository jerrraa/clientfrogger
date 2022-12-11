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
	
	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;
	
	Socket s = new Socket("localhost", SERVER_PORT);
	//Initialize data stream to send data out
	OutputStream outstream = s.getOutputStream();
	PrintWriter out = new PrintWriter(outstream);

	public static void main(String[] args) throws IOException {
		FroggerClient game = new FroggerClient();
		game.setVisible(true);
	}
//connect and insert contents
	
	public FroggerClient() throws IOException {
		DisplayScreen();
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
								GameClient myService = new GameClient (s2, frog1);
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
				InsertVehicleRows();
				InsertVehicleRows1();
				InsertVehicleRows2();
				add(frog1Label);
				InsertLogRows();
				InsertLogRows2();
				InsertLogRows3();
				InsertLogRows4();
				InsertLogRows5();
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
		if (frog1.getMoving() == true) {
		//modify position
		String command = null;
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			command = "VERT U ";
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			command = "VERT D ";
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			command = "SIDE L ";
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			command = "SIDE R ";

		} else {
			System.out.println("invalid operation");
		}
		
		out.println(command);
		out.flush();
		frog1Label.setLocation(frog1.getX(), frog1.getY());
		}
}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void InsertVehicleRows() {
		vehiclelane = new Vehicle[4];
		int Xoffset = 0;
		for (int i = 0; i<4; i++ ) {
			vehiclelane[i] = new Vehicle();
			VEHICLElabel = new JLabel(); 
			VEHICLElabel.setIcon(vehicleicon);
			VEHICLElabel.setSize(widthveh, heightveh);
			
			vehiclelane[i].SetVehicleLabel(VEHICLElabel);
			vehiclelane[i].setHeight(heightveh); vehiclelane[i].setWidth(widthveh);
			vehiclelane[i].setX(vehiclelane[i].getX() + Xoffset);
			vehiclelane[i].setY(710);
			vehiclelane[i].SetSpeed(Gameproperties.CHARACTER_STEP-60);
			VEHICLElabel.setLocation(vehiclelane[i].getX(), vehiclelane[i].getY());
			
			add(VEHICLElabel);
			Xoffset += offset-20;
		}
	}
	//this belongs to top row
	public void InsertVehicleRows1(){
		vehiclelane1 = new ReverseVehicle[4];
		int Xoffset = 0;
		for (int i = 0; i<4; i++ ) {
			vehiclelane1[i] = new ReverseVehicle();
			VEHICLElabel2 = new JLabel(); 
			VEHICLElabel2.setIcon(vehicleicon2);
			VEHICLElabel2.setSize(widthveh, heightveh);
			vehiclelane1[i].SetVehicleLabel(VEHICLElabel2);
			vehiclelane1[i].setHeight(heightveh); vehiclelane1[i].setWidth(widthveh);
			vehiclelane1[i].setX(vehiclelane1[i].getX() + Xoffset);
			vehiclelane1[i].setY(620);
			vehiclelane1[i].SetSpeed(Gameproperties.CHARACTER_STEP-80);
			VEHICLElabel2.setLocation(vehiclelane1[i].getX(), vehiclelane1[i].getY());
			add(VEHICLElabel2);
			Xoffset += offset+20;
		}
	}
	//this belongs to bottom row
	public void InsertVehicleRows2(){
		vehiclelane2 = new Vehicle[4];
		int Xoffset = 0;
		for (int i = 0; i<4; i++ ) {
			vehiclelane2[i] = new Vehicle();
			vehiclelane2[i].GrabFrog1(frog1);
			vehiclelane2[i].GrabGame(this);
			VEHICLElabel3 = new JLabel(); 
			VEHICLElabel3.setIcon(vehicleicon);
			VEHICLElabel3.setSize(widthveh, heightveh);
			vehiclelane2[i].SetVehicleLabel(VEHICLElabel3);
			vehiclelane2[i].setHeight(heightveh); vehiclelane2[i].setWidth(widthveh);
			vehiclelane2[i].setX(vehiclelane2[i].getX() + Xoffset);
			vehiclelane2[i].setY(800);
			vehiclelane2[i].SetSpeed(Gameproperties.CHARACTER_STEP-40);
			VEHICLElabel3.setLocation(vehiclelane2[i].getX(), vehiclelane2[i].getY());
			add(VEHICLElabel3);
			Xoffset += offset+50;
		}
	}
	public void InsertLogRows() {
		LogLane = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane[i] = new Log();
			LOGlabel = new JLabel();
			LOGlabel.setIcon(logicon);
			LOGlabel.setSize(200, 80);
			LogLane[i].setHeight(90);
			LogLane[i].setWidth(160);
			LogLane[i].setX(LogLane[i].getX() + Loffset+70);
			LogLane[i].setY(454);
			LogLane[i].SetSpeed(19);
			LogLane[i].setDirection(true);
			LOGlabel.setLocation(LogLane[i].getX(), LogLane[i].getY());
			LogLane[i].SetLogLabel(LOGlabel);
			add(LOGlabel);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows2() {
		LogLane1 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane1[i] = new Log();
			LOGlabel2 = new JLabel();
			LOGlabel2.setIcon(logicon);
			LOGlabel2.setSize(200, 80);
			LogLane1[i].setHeight(95);
			LogLane1[i].setWidth(160);
			LogLane1[i].setX(LogLane1[i].getX() + Loffset);
			LogLane1[i].setY(364);
			LogLane1[i].SetSpeed(15);
			LOGlabel2.setLocation(LogLane1[i].getX(), LogLane1[i].getY());
			LogLane1[i].SetLogLabel(LOGlabel2);
			add(LOGlabel2);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows3() {
		LogLane2 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane2[i] = new Log();
			LOGlabel3 = new JLabel();
			LOGlabel3.setIcon(logicon);
			LOGlabel3.setSize(200, 80);
			LogLane2[i].setHeight(95);
			LogLane2[i].setWidth(160);
			LogLane2[i].setX(LogLane2[i].getX() + Loffset-40);
			LogLane2[i].setY(274);
			LogLane2[i].SetSpeed(30);
			LOGlabel3.setLocation(LogLane2[i].getX(), LogLane2[i].getY());
			LogLane2[i].SetLogLabel(LOGlabel3);
			add(LOGlabel3);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows4() {
		LogLane3 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane3[i] = new Log();
			LOGlabel4 = new JLabel();
			LOGlabel4.setIcon(logicon);
			LOGlabel4.setSize(200, 80);
			LogLane3[i].setHeight(95);
			LogLane3[i].setWidth(160);
			LogLane3[i].setDirection(true);
			LogLane3[i].setX(LogLane3[i].getX() + Loffset+50);
			LogLane3[i].setY(184);
			LogLane3[i].SetSpeed(10);
			
			LOGlabel4.setLocation(LogLane3[i].getX(), LogLane3[i].getY());
			LogLane3[i].SetLogLabel(LOGlabel4);
			add(LOGlabel4);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows5() {
		LogLane4 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane4[i] = new Log();
			LOGlabel5 = new JLabel();
			LOGlabel5.setIcon(logicon);
			LOGlabel5.setSize(200, 80);
			LogLane4[i].setHeight(95);
			LogLane4[i].setWidth(160);
			LogLane4[i].setX(LogLane4[i].getX() + Loffset);
			LogLane4[i].setY(94);
			LogLane4[i].SetSpeed(40);
			LOGlabel5.setLocation(LogLane4[i].getX(), LogLane4[i].getY());
			LogLane4[i].SetLogLabel(LOGlabel5);
			add(LOGlabel5);
			Loffset += offset+80;
		}
	}
	public void UpdateFrog1() {
		DetectBorder(DataScore.INSTANCE.GetX(),DataScore.INSTANCE.GetY());
		frog1Label.setLocation(frog1.getX()+DataScore.INSTANCE.GetX(), frog1.getY()+DataScore.INSTANCE.GetY());
		
	}

}
