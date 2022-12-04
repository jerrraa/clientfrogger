
public enum DataScore {
//singleton to store our data
	  INSTANCE;
	  private int score;
	  private DataScore(){
	    score = 0;
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
}
