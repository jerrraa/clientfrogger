
import javax.swing.JLabel;

public class Log extends Sprite {
	private Boolean moving, direction;
	private JLabel Loglabel;
	private int speed;
	private Frog1 frog1;
	private JLabel froglabel;
	public Log () {
		this.speed = 0;
		this.moving = false;
		this.direction = false;
	}

	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	public void SetLogLabel(JLabel log) {
		this.Loglabel = log;
	}
	public void GrabPlayerFrog(Frog1 frog1) {
		this.frog1 = frog1;
	}
	public void GrabFrogLabel(JLabel temp) {
		this.froglabel = temp;
	}
	public void SetSpeed(int s) {
		this.speed = s;
	}
	public int GetSpeed() {
		return this.speed;
	}
	public void setDirection(Boolean temp) {
		this.direction = temp;
	}
	public Boolean GetDirection() {
		return this.direction;
	}
	public void StartMoving() {
		this.moving = false;
	}
	public void UpdateLog(int vert, int hort, int speed) {
		
	}
	public void detectLog() {
		if (this.rect.intersects(frog1.rect)) {
			if (GetDirection() == true) {
				froglabel.setLocation(frog1.getX()-this.GetSpeed(), frog1.getY());
				frog1.setX(frog1.getX()-this.GetSpeed());
			} else {
				froglabel.setLocation(frog1.getX()+this.GetSpeed(), frog1.getY());
					frog1.setX(frog1.getX()+this.GetSpeed());
			}
		}
	}
}
