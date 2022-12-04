

import java.awt.Rectangle;

/*
 	1. Create the new class skeleton
 	2. identify all class attributes (data members)
 	3. getters and setters (accessors, mutators)
 	4. default constructor
 	5. secondary constructors
 	6. print/display function
 	7. any other code required for objects
 	8. run in an application
 */

public class Sprite {
	protected int x, y; // upper left, top position
	protected int height, width; 
	protected String image;
	protected Rectangle rect;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		this.updateRectangleposition();
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.updateRectangleposition();
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		updateRectanglesize();
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		updateRectanglesize();
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Sprite() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.image = "";
		this.rect = new Rectangle();
	}
	
	public void display() {
		System.out.println("x, y: "+ this.x + "," + this.y);
		System.out.println("height, width: "+ this.height + "," + this.width);
		System.out.println("image: " + this.image);
	}
	public void updateRectangleposition( ) { 
		this.rect.y = this.y;
		this.rect.x = this.x;
	}
	public void updateRectanglesize() {
		this.rect.width = width;
		this.rect.height = height;
	}
	public Rectangle GetRectangle() {
		return this.rect;
		
	}
}
