import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 *  Enables drag and drop functionality for actors in UI-elements.
 *  
 */
public class DragAndDropActorUI extends BaseActor
{
    private DragAndDropActorUI self;
    private DragAndDropActor other;
    private float grabOffsetX;
    private float grabOffsetY;

    private float startPositionX;
    private float startPositionY;
    private float positionX;
    private float positionY;
    private boolean draggable;
    private Table table;
    private DropTargetActor dropTarget;

    public DragAndDropActorUI(float x, float y, Stage s)
    {
        super(x,y,s);

        self = this;

        draggable = true;
        
        addListener(
            new InputListener() 
            {       
                public boolean touchDown(InputEvent event, float eventOffsetX, float eventOffsetY, int pointer, int button) 
                {                      
                    if ( !self.isDraggable() )
                        return false; 

                    self.grabOffsetX = eventOffsetX;
                    self.grabOffsetY = eventOffsetY;

                    // store original position
                    Vector2 stageCoordinates = self.localToStageCoordinates(new Vector2());
                    self.startPositionX =stageCoordinates.x;// self.getX();
                    self.startPositionY = stageCoordinates.y;
                    //System.out.println("StartPositionX_scroll: "+ Float.toString(self.startPositionX));
                    self.toFront();

                    //self.onDragStart();
                    
                    return true; // returning true indicates other touch methods are called
                }

                public void touchDragged(InputEvent event, float eventOffsetX, float eventOffsetY, int pointer) 
                {
                    
                    float deltaX = eventOffsetX - self.grabOffsetX;
                    float deltaY = eventOffsetY - self.grabOffsetY;
                    
                    positionX = deltaX;
                    positionY = deltaY;
 
                    //self.moveBy(deltaX, deltaY);
                }

                public void touchUp(InputEvent event, float eventOffsetX, float eventOffsetY, int pointer, int button) 
                {                    
                    //System.out.println("X drop Down: " + Float.toString(eventOffsetX));
                    //System.out.println("Y drop Down: " + Float.toString(eventOffsetY));
                    
                    if (positionY>table.getMinHeight())
                    {
                        other.setPosition(startPositionX+positionX,startPositionY+positionY);
                        removeFromTable();
                    }
                    
                    
                }
            }
        );

    }

    /**
     *  Set whether this actor can be dragged.
     */
    public void setDraggable(boolean d) 
    {  draggable = d;  }

    /**
     *  Check if this actor can be dragged.
     */
    public boolean isDraggable() 
    {  return draggable;  }

    /**
     *  Check if a drop target currently exists.
     */
    public boolean hasDropTarget()
    {  return (dropTarget != null);  }

    /**
     *  Automatically set when actor is dropped on a target.
     */
    public void setDropTarget(DropTargetActor a) 
    {  dropTarget = a;  }

    /**
     *  If this actor is dropped on a "targetable" actor, that actor can be obtained from this method.
     */
    public DropTargetActor getDropTarget() 
    {  return dropTarget;  }

    /**
     *  Called when drag begins; extending classes may override this method.
     */
    public void onDragStart()
    {    }

    /**
     *  Called when drop occurs; extending classes may override this method.
     */
    public void onDrop()
    {
        
    }

    /** 
     *  Slide this actor to the center of another actor.
     */
    public void moveToActor(BaseActor other)
    {
        float x = other.getX() + (other.getWidth()  - this.getWidth())  / 2;
        float y = other.getY() + (other.getHeight() - this.getHeight()) / 2;
        addAction( Actions.moveTo(x,y, 0.50f, Interpolation.pow3) );
    }

    /**
     *  Slide this actor back to its original position before it was dragged.
     */
    public void moveToStart()
    {
        addAction( Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3) );
    }

    public void act(float dt)
    {
        super.act(dt);
    }
    
    public DragAndDropActor getDragAndDropActor()
    {
        return other;
    }
    
    public void setDragAndDropActor(DragAndDropActor dr)
    {
        other = dr;
    }
    
    public void setTable(Table table)
    {
        this.table = table;
        
    }
    
    public void removeFromTable() 
    {
        table.removeActor(this);
        table.pack();  
    }
}