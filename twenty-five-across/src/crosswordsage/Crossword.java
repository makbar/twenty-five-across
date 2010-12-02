package crosswordsage;

import java.util.ArrayList;
import java.io.*;

public class Crossword implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4238375454185622658L;

	public Crossword()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private ArrayList words;
    private int width;  //number of squares
    private int height; //number of squares
    private boolean isEditable;

    public Crossword(int width, int height)
    {
        isEditable = true;
        this.width = width;
        this.height = height;
        words = new ArrayList();
    }
    
    public Crossword copy() {
    // Note this method does not do a deep copy yet; the words
    // are not value-copied, only references to them are.
    	Crossword thecopy = new Crossword(this.width, this.height);
    	thecopy.setIsEditable(this.getIsEditable());
    	ArrayList oldwords = this.getWords();
    	for (int i=0; i<oldwords.size(); i++) {
    		Word w = (Word) oldwords.get(i);
    		thecopy.addWord(w);
    	}
    	return thecopy;
    }

    public boolean getIsEditable()
    {
        return isEditable;
    }

    public void setIsEditable(boolean b)
    {
        isEditable = b;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public void addWord(Word w)
    {
        words.add(w);
    }

    public ArrayList getWords()
    {
        return words;
    }

    public void setWords(ArrayList al)
    {
        words = al;
    }

    private void jbInit()
            throws Exception
    {
    }
}
