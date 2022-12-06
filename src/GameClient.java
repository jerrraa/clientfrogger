import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//processing routine on server (B)
public class GameClient implements Runnable {

	private Socket s;
	private Scanner in;

	public GameClient (Socket aSocket) {
		this.s = aSocket;
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
			int speed = in.nextInt();
			System.out.println("SOUP" + speed);
			DataScore.INSTANCE.SetY(speed);
		} else if (command.equals("SIDE")) {
			int speed = in.nextInt();
			System.out.println("PIZZA" + speed);
			DataScore.INSTANCE.SetX(speed);
		}
	}
}
