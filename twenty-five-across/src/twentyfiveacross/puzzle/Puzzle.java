package twentyfiveacross.puzzle;

import java.io.Serializable;
import java.util.Vector;


public class Puzzle implements Serializable {
    private Square[][] squares;
    private Vector<Word> acrossWords;
    private Vector<Word> downWords;

    private int size;   /* Number of squares */

    private int playerId;
    private int[] viewerIds;

    private static final long serialVersionUID = 1L;

    public Puzzle() {
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public Vector<Word> getAcrossWords() {
        return acrossWords;
    }

    public void setAcrossWords(Vector<Word> acrossWords) {
        this.acrossWords = acrossWords;
    }

    public Vector<Word> getDownWords() {
        return downWords;
    }

    public void setDownWords(Vector<Word> downWords) {
        this.downWords = downWords;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int[] getViewerIds() {
        return viewerIds;
    }

    public void setViewerIds(int[] viewerIds) {
        this.viewerIds = viewerIds;
    }

}
