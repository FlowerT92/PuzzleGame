import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Beschreiben Sie hier die Klasse PuzzlePiece.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class PuzzlePiece extends DragAndDropActor
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int row;
    private int col;
    private int rightPlaceAtStageX;
    private int rightPlaceAtStageY;
    private PuzzleArea puzzleArea;
    private boolean correctlyPlaced;
    private int tolerance;
    /**
     * Konstruktor für Objekte der Klasse PuzzlePiece
     */
    public PuzzlePiece(float x, float y, Stage s)
    {
        super(x,y,s);
        correctlyPlaced=false;
        tolerance=10;
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
    
    public void setPuzzleArea(PuzzleArea pa)
    {
        puzzleArea = pa;
    }
    
    public PuzzleArea getPuzzleArea()
    {
        return puzzleArea;
    }
    
    public void clearPuzzleArea()
    {
        puzzleArea = null;
    }
    
    public boolean hasPuzzleArea()
    {
        return puzzleArea != null;
    }
    
    public boolean isCorrectlyPlaced()
    {
        
        
        return correctlyPlaced;
        //return hasPuzzleArea() && this.getRow() ==puzzleArea.getRow() && this.getCol() == puzzleArea.getCol();
    }
    
    public void onDragStart()
    {
       if ( hasPuzzleArea() )
        {
            PuzzleArea pa = getPuzzleArea();
            pa.setTargetable(true);
            clearPuzzleArea();
        }
    }
    
    public void onDrop()
    {
         // System.out.println("X wert: "+ Float.toString(getX()));
         // System.out.println("X-right wert: "+ Float.toString(rightPlaceAtStageX));
         // System.out.println("Y wert: "+ Float.toString(getY()));
         // System.out.println("Y-right wert: "+ Float.toString(rightPlaceAtStageY));
        if ((Math.abs(getX()-rightPlaceAtStageX)<tolerance) && (Math.abs(getY()-rightPlaceAtStageY)<tolerance))
        {
            setDraggable(false);
            correctlyPlaced=true;
            setPosition(rightPlaceAtStageX,rightPlaceAtStageY);
        }
        
        if ( hasDropTarget() )
        {
            PuzzleArea pa = (PuzzleArea)getDropTarget();
            moveToActor(pa);
            setPuzzleArea(pa);
            pa.setTargetable(false);
        }
    }
    
    public void setRightPlaceAtStageX(int placeX)
    {
        rightPlaceAtStageX = placeX;
    }
    
    public void setRightPlaceAtStageY(int placeY)
    {
        rightPlaceAtStageY = placeY;
    }
    


}