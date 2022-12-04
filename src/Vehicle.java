

import javax.swing.JLabel;

public class Vehicle extends Sprite {
	private Frog1 frog1;
	private FroggerClient gamekill;
	private Boolean moving;
	private Thread newt;
	private JLabel vehicleLabel;
	private int speed;
	public Vehicle() {
		this.moving = false;
		this.speed = 0;
	}

	public Boolean getMoving() {
		return moving;
	}
	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	public void SetSpeed(int s) {
		this.speed = s;
	}
	public int GetSpeed() {
		return speed;
	}
	public void StartMoving() {
		this.moving = false;
	}
	public void SetVehicleLabel(JLabel temp) {
		this.vehicleLabel = temp;
	}
	public void GrabFrog1(Frog1 temp) {
		this.frog1 = temp;
	}
	public void GrabGame(FroggerClient gameprep) {
		this.gamekill = gameprep;
		
	}
	
	public void detectCollision() {
		if (this.rect.intersects(frog1.rect)) {
			System.out.println("touched");
			//gamekill.Resetfrogger();
		}
	}
}
//_______________________________________
//_______________________________________
//_______________________________________
//_______________________________________

class ReverseVehicle extends Sprite {
	private Boolean moving;
	private Thread fast;
	private JLabel vehicleLabel;
	private Frog1 frog1;
	private FroggerClient gamekill;
	private int speed;
	public ReverseVehicle() {
		this.moving = false;
		this.speed = 0;
	}
	public Boolean getMoving() {
		return moving;
	}
	public void SetSpeed(int s) {
		this.speed = s;
	}
	public int GetSpeed() {
		return speed;
	}
	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	public void GrabFrog1(Frog1 temp) {
		this.frog1 = temp;
	}
	public void GrabGame(FroggerClient gameprep) {
		this.gamekill = gameprep;
		
	}
	public void SetVehicleLabel(JLabel temp) {
		this.vehicleLabel = temp;
	}
	public void detectCollision() {
		if (this.rect.intersects(frog1.rect)) {
			System.out.println("touched");
			//gamekill.Resetfrogger();
		}
	}
}
//_______________________________________
//_______________________________________
//_______________________________________
//_______________________________________

