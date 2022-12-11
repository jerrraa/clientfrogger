import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//processing routine on server (B)
public class GameClient implements Runnable {

	private Socket s;
	private Scanner in;
	private Frog1 frog;
	public GameClient (Socket aSocket, Frog1 frog1) {
		this.s = aSocket;
		this.frog = frog1;
	}
	public void run() {
		
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			}
	//processing the requests
	public void processRequest () throws IOException {
		//if next request is empty then return
		while(true) {
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}
	public void executeCommand(String command) throws IOException{
		if ( command.equals("VERT")) {
			int speed = 0;
			String direction = in.next();
			switch(direction) {
			case "U":
				speed += 90;
				break;
			case "D":
				speed -= 90;
				break;
			}
			this.frog.setY(speed);
		} else if (command.equals("SIDE")) {
			String direction = in.next();
			int speed = 0;
			switch(direction) {
			case "L":
				speed += 90;
				break;
			case "R":
				speed -= 90;
				break;
			}
			this.frog.setX(speed);
			
		}
	}
}
