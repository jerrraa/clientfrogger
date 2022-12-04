import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class FroggerClient extends JFrame implements KeyListener {

	
	public FroggerClient() throws IOException {

		
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
	
	
	
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
