import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.*;

public class LevelScreen extends BaseScreen
{
    private Label messageLabel;
    private ScrollPane2 scrollPane;
    private float scaleFactorX;
    private float scaleFactorY;
    
    public void initialize() 
    {        
        BaseActor background = new BaseActor(0,0,mainStage);
        background.loadTexture("assets/background.png");
        BaseActor.setWorldBounds(background);
        int numberRows = 4;
        int numberCols = 4;
        
        Texture texture = new Texture(Gdx.files.internal("assets/vorlage400x400.png"),true);
        int imageHeight = texture.getHeight();
        int imageWidth = texture.getWidth();
        int pieceWidth = imageWidth / numberCols;
        int pieceHeight = imageHeight / numberRows;
        
        int regionWidth = pieceWidth;
        int regionHeight = pieceHeight;
        int Offset = pieceWidth;
        int overlapping = pieceWidth/4;
        
        BaseActor helperImage = new BaseActor(0,0,mainStage);
        helperImage.loadTexture("assets/vorlage400x400.png");
        helperImage.setOpacity(0.1f);
        helperImage.centerAtPosition(640,490);
        
        int helperPlaceX=640-200;
        int helperPlaceY=590;
        Table scrollTable = new Table();
        
        TextureRegion[][] temp = TextureRegion.split(texture,pieceWidth,pieceHeight);
        ArrayList<PuzzlePieceImage> listOfUIPieces = new ArrayList<>();
        for(int r = 0; r < numberRows; r++)
        {
            for( int c = 0; c < numberCols; c++)
            {
               String textureName = Integer.toString(r)+'_'+ Integer.toString(c);
               int pieceX = MathUtils.random(0,400 - pieceWidth);
               int pieceY = MathUtils.random(0,800 - pieceHeight);
               PuzzlePiece pp = new PuzzlePiece(-100,-100,mainStage);
               PuzzlePieceImage ppi = new PuzzlePieceImage(pieceX,pieceY,mainStage);
               
               String maskPath="";
               if (r==0 && c == 0)
              {
                  TextureRegion cornerUpperLeft = new TextureRegion(texture, 0, 0, regionWidth, regionHeight);
                  maskPath="assets/puzzlePiece_five.png";
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,cornerUpperLeft));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              if (r==0 && c == numberCols-1)
              {
                  TextureRegion cornerUpperRight = new TextureRegion(texture, c*Offset-overlapping, 0, regionWidth, regionHeight);
                  maskPath="assets/puzzlePiece_12.png";
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,cornerUpperRight));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX-overlapping);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if(r==numberRows-1 && c == 0)
              {
                regionHeight+=overlapping;
                 TextureRegion cornerBottomLeft = new TextureRegion(texture,0 , r*Offset-overlapping, regionWidth, regionHeight);
                 maskPath="assets/puzzlePiece_13.png";
                 Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,cornerBottomLeft));
                 pp.setAnimation(animPiece);
                 ppi.setAnimation(animPiece);
                 pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX);
                 pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if (r== numberRows-1 && c == numberCols-1)
              {
                regionHeight+=overlapping;
                TextureRegion cornerBottomRight = new TextureRegion(texture, c*Offset-overlapping, r*Offset-overlapping, regionWidth, regionHeight);
                maskPath="assets/puzzlePiece_eleven.png";
                Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,cornerBottomRight));
                pp.setAnimation(animPiece);
                ppi.setAnimation(animPiece);
                pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX-overlapping);
                pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if (r==0 && c>0 && !(c==numberCols-1))
              {
                  regionWidth+=overlapping;
                  TextureRegion upperPiece = new TextureRegion(texture, c*Offset-overlapping,0, regionWidth, regionHeight);
                  maskPath="assets/puzzlePiece_six.png";
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,upperPiece));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX-overlapping);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if (r==numberRows-1 && c>0 && !(c==numberCols-1))
              {
                  regionWidth+=overlapping;
                  TextureRegion bottomPiece = new TextureRegion(texture, c*Offset-overlapping, r*Offset-overlapping, regionWidth, regionHeight);
                  maskPath="assets/puzzlePiece_ten.png";
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,bottomPiece));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX-overlapping);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if (r>0 && c==0 && !(r==numberRows-1))
              {
                  regionHeight+=overlapping;
                  TextureRegion leftPiece = new TextureRegion(texture, 0, r*Offset-overlapping, regionWidth, regionHeight);
                  maskPath="assets/puzzlePiece_nine.png";
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,leftPiece));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if (r>0 && c==numberCols-1 && !(r==numberRows-1))
              {
                  regionHeight += overlapping;
                  regionWidth += overlapping;
                  TextureRegion rightPiece = new TextureRegion(texture, c*Offset-overlapping, r*Offset-overlapping, regionWidth, regionHeight);
                  maskPath="assets/puzzlePiece_14.png";
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,rightPiece));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX-overlapping);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }
              
              if (r>0 && c>0 && !(r==numberRows-1) && !(c==numberCols-1))
              {
                  regionHeight += overlapping;
                  regionWidth += overlapping;
                  TextureRegion middlePiece = new TextureRegion(texture, c*Offset-overlapping, r*Offset-overlapping, regionWidth, regionHeight);
                  System.out.println(Integer.toString(middlePiece.getRegionWidth()) );
                  System.out.println(Integer.toString(middlePiece.getRegionHeight()) );
                  maskPath="assets/puzzlePiece_four.png";
                  //System.out.println("test in Middle");
                  Animation<TextureRegion> animPiece =pp.loadTexture(pp.setupPuzzlePiece(maskPath,middlePiece));
                  pp.setAnimation(animPiece);
                  ppi.setAnimation(animPiece);
                  pp.setRightPlaceAtStageX((c*(pieceWidth))+helperPlaceX-overlapping);
                  pp.setRightPlaceAtStageY(helperPlaceY-(r*pieceHeight));
              }


               //scaleFactorX=(100.0f/pieceWidth);   
               //scaleFactorY=(100.0f/pieceHeight);
               ppi.setDragAndDropActor(pp);
               ppi.setSize(100,100);
               ppi.setTable(scrollTable);
               listOfUIPieces.add(ppi);
               
            }
        }
        
        Collections.shuffle(listOfUIPieces);
        for (PuzzlePieceImage element: listOfUIPieces)
        {
             scrollTable.add(element).pad(10);
        }
        
        ScrollPane2 scroller = new ScrollPane2(scrollTable);
        
        uiTable.add(scroller).expandX().expandY().bottom().pad(15); 
        messageLabel = new Label("...",BaseGame.labelStyle);
        messageLabel.setColor(Color.CYAN);
        uiTable.add(messageLabel).expandX().expandY().top().pad(50);
        messageLabel.setVisible(false);

    }

    public void update(float dt)
    {
       boolean solved = true;
       for(BaseActor actor : BaseActor.getList(mainStage,"PuzzlePiece"))
       {
           PuzzlePiece pp = (PuzzlePiece) actor;
           
           if(!pp.isCorrectlyPlaced())
               solved = false;
       }
       
       if(solved)
       {
            messageLabel.setText("You win");
            messageLabel.setVisible(true);
       }
       else
       {
           messageLabel.setText("...");
           messageLabel.setVisible(false);
       }
    }
    
    
    //tests zum Zoom
    public boolean zoom (float originalDistance, float currentDistance){
       ((OrthographicCamera)mainStage.getCamera()).zoom += 0.02f;
       return true;
   }
   
   public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer)
   {
       ((OrthographicCamera)mainStage.getCamera()).zoom += 0.02f;        
       return true;
   }
   
   
}