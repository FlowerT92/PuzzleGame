import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Blending;

/**
 *  Enables drag and drop functionality for actors.
 *  
 */
public class DragAndDropActor extends BaseActor
{
    private DragAndDropActor self;

    private float grabOffsetX;
    private float grabOffsetY;

    private float startPositionX;
    private float startPositionY;

    private boolean draggable;

    private DropTargetActor dropTarget;
    
    private Texture mask;
    private Pixmap pixmap;

    public DragAndDropActor(float x, float y, Stage s)
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
                    Vector2 stageCoordinates = self.localToStageCoordinates(new Vector2());
                    // store original position
                    // in case actor needs to return to it later
                    self.startPositionX = self.getX();
                    self.startPositionY = self.getY();

                    self.toFront();

                    //self.onDragStart();
                    float deltaX = Math.abs(x - self.getX());
                    float deltaY = Math.abs(y - self.getY());

                    return true; // returning true indicates other touch methods are called
                }

                public void touchDragged(InputEvent event, float eventOffsetX, float eventOffsetY, int pointer) 
                {
                    float deltaX = eventOffsetX - self.grabOffsetX;
                    float deltaY = eventOffsetY - self.grabOffsetY;

                    self.moveBy(deltaX, deltaY);
                }

                public void touchUp(InputEvent event, float eventOffsetX, float eventOffsetY, int pointer, int button) 
                {
                    self.setDropTarget(null);
                    self.onDrop();
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
    {    }

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
    
    public void setMask(Texture mask)
    {
        this.mask = mask;
    }
    
    public Pixmap applyMask(Pixmap source,Pixmap mask) 
    {
        /* Create a Pixmap to store the mask information, at the end it will
         * contain the result. */
        //System.out.println("resulthoehe: " + Integer.toString(source.getWidth()));
        Pixmap result = new Pixmap(mask.getWidth(), mask.getHeight(), Pixmap.Format.RGBA8888);
        
        for (int x = 0; x < result.getWidth(); x++) 
        {
            for (int y = 0; y < result.getHeight(); y++) 
            {
                    //pixmap vorbereiten
                    result.setColor(0, 0, 0, 0);
                    result.drawPixel(x, y);
            }
        }
        /* This setting lets us overwrite the pixels' transparency. */
        result.setBlending(Pixmap.Blending.None);

        for (int x = 0; x < result.getWidth(); x++) 
        {
            for (int y = 0; y < result.getHeight(); y++) 
            {
                Color maskColor = new Color(mask.getPixel(x, y));
                // Check if the mask pixel is white
                if (maskColor.a > 0.5 ) {
                    // Get the color of the content pixel
                    Color contentColor = new Color(source.getPixel(x, y));

                    // Set the result pixel color to the content color
                    result.setColor(contentColor);
                    result.drawPixel(x, y);
                } 
            }
        }
    
        return result;
    }

    public Pixmap getPixMapFromRegion(TextureRegion region)
    {
    
        Texture texture = region.getTexture();
        TextureData data = texture.getTextureData();
        if (!data.isPrepared()) {
            data.prepare();
        }
        Pixmap pixmap = data.consumePixmap();
        int width = region.getRegionWidth();
        System.out.println("width: "+ Integer.toString(width));
        int height = region.getRegionHeight();
        System.out.println("height: "+ Integer.toString(height));
        Pixmap px = new Pixmap(width, height, Format.RGBA8888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int colorInt = pixmap.getPixel(region.getRegionX() + x, region.getRegionY() + y);
                px.setColor(colorInt);
                px.drawPixel(x, y);
            }
        }
        return px;
    }
    
    public Texture setupPuzzlePiece(String filenameMask, String filenameContent)
    {
        FileHandle imagePathContent = new FileHandle(filenameContent);
        FileHandle imagePathMask = new FileHandle(filenameMask);
        /* Load the pixels of our image into a Pixmap. */
        Pixmap pixmapContent = new Pixmap(imagePathContent);
        Pixmap pixmapMask = new Pixmap(imagePathMask);
        pixmapContent = applyMask(pixmapContent, pixmapMask);
        Texture masked = new Texture(pixmapContent);
        return masked;
    }
    
        public Texture setupPuzzlePiece(String filenameMask, TextureRegion content)
    {
        FileHandle imagePathMask = new FileHandle(filenameMask);
        /* Load the pixels of our image into a Pixmap. */
        Pixmap pixmapContent = getPixMapFromRegion(content);
        Pixmap pixmapMask = new Pixmap(imagePathMask);
        pixmapContent = applyMask(pixmapContent, pixmapMask);
        Texture masked = new Texture(pixmapContent);
        return masked;
    }
}