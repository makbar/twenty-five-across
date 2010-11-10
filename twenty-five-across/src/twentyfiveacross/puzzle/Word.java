package twentyfiveacross.puzzle;

public class Word {
    private String word;
    private String clue;
    private int number;
//    private boolean isAcross;


    public Word(String word, String clue, int number) {
        this.word = word;
        this.clue = clue;
        this.number = number;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getClue() {
        return clue;
    }
    public void setClue(String clue) {
        this.clue = clue;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
