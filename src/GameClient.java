import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameClient implements Runnable{

	private Socket s;
	private Scanner in;

	public GameClient (Socket aSocket) {
		this.s = aSocket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		if ( command.equals("PLAYER")) {
			FroggerClient game = new FroggerClient();
			int playno = in.nextInt();
			String input = in.next();
			int playerX = in.nextInt();
			int playerY = in.nextInt();
			System.out.println("?SPlayer "+playno+" "+ input + " "+playerX+", "+playerY);
			game.UpdateFrog1(playerX, playerY);
		}
	}
	
	
}
