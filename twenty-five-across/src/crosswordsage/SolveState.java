package crosswordsage;

import java.util.ArrayList;

public class SolveState implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	ArrayList squares;
	
	SolveState() {
	}
	
	public SolveState(int width, int height) {
		squares = new ArrayList();
		for (int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				squares.add(new SquareState(i, j));
			}
		}
	}
	
	public void setState(int x, int y, String letter) {
		SquareState s;
		for (int i=0; i<squares.size(); i++) {
			s = (SquareState) squares.get(i);
			if(s.posx == x && s.posy == y) {
				s.letter = letter;
			}
		}
	}
	
	public String getLetter(int x, int y) {
		SquareState s;
		for (int i=0; i<squares.size(); i++) {
			s = (SquareState) squares.get(i);
			if(s.posx == x && s.posy == y) {
				return s.letter;
			}
		}
		return "";
	}
	
	public String toString() {
		String it = "";
		for (int i=0; i<squares.size(); i++) {
			it = it + squares.get(i).toString();
		}
		return it;
	}
	
	public class SquareState implements java.io.Serializable {
		String letter;
		int posx;
		int posy;
		// Perhaps later: who entered it?
		
		SquareState() {
			letter = "";
		}
		
		SquareState(int x, int y) {
			posx = x;
			posy = y;
			letter = "";
		}
		
		SquareState(int x, int y, String letter) {
			posx = x;
			posy = y;
			if(letter.length() == 1) {
				this.letter = letter;
			} else {
				this.letter = "";
			}
		}
		
		public String toString() {
			return "x: " + posx + " y: " + posy + " l: " + letter;
		}
		
	}
}
