package si.model;

public enum AlienType {
	A(40, 40, 50), B(20, 20,100), C(10, 8, 200),D(40,20,500),E(20,10,1000);
	private int width;
	private int height;
	private int score;

	private AlienType(int w, int h, int s) {
		width = w;
		height = h;
		score = s;
	}

	public int getWidth() {
		return width;
	}

	public int getScore() {
		return score;
	}

	public int getHeight() {return height; }
}
