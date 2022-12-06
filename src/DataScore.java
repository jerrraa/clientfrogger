
public enum DataScore {
//singleton to store our data
	  INSTANCE;
	  private int score;
	  private int x;
	  private int y;
	  private DataScore(){
	    score = 0;
	    x = 0;
	  }
	  public int GetScore(){
	    return score;
	  }
	  public void SetScore(int score) {
		  this.score = score;
	  }
	  public void addScore(int score){
	    this.score += score;
	  }
	  public void MinusScore(int score) {
		  this.score -= score;
	  }
	  public int GetX() {
		  return this.x;
	  }
	  public void SetX(int x) {
		 this.x += x;
	  }
	  public int GetY() {
		  return this.y;
	  }
	  public void SetY(int y) {
		  this.y += y;
	  }
}		
