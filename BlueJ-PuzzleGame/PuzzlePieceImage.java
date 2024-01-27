import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 * Beschreiben Sie hier die Klasse PuzzlePieceImage.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class PuzzlePieceImage extends DragAndDropActorUI
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int row;
    private int col;
    
    private PuzzleArea puzzleArea;

    /**
     * Konstruktor für Objekte der Klasse PuzzlePiece
     */
    public PuzzlePieceImage(float x, float y, Stage s)
    {
        super(x,y,s);
    }


   public void setRow(int r)
    {
        row = r;
    }
    
    public void setCol(int c)
    {
        col = c;
    }
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public void onDragStart()
    {
        
    }
    
    public void onDrop()
    {

    }
    
   
}
