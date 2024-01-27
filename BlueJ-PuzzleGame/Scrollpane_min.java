import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
/**
 * Beschreiben Sie hier die Klasse Scrollpane_min.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Scrollpane_min extends ScrollPane
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
private ScrollPaneStyle style;
    private Actor actor;

    final Rectangle actorArea = new Rectangle();
    final Rectangle hScrollBounds = new Rectangle(), hKnobBounds = new Rectangle();
    final Rectangle vScrollBounds = new Rectangle(), vKnobBounds = new Rectangle();
    private final Rectangle actorCullingArea = new Rectangle();
    private ActorGestureListener flickScrollListener;

    boolean scrollX, scrollY;
    boolean vScrollOnRight = true, hScrollOnBottom = true;
    float amountX, amountY;
    float visualAmountX, visualAmountY;
    float maxX, maxY;
    boolean touchScrollH, touchScrollV;
    final Vector2 lastPoint = new Vector2();
    boolean fadeScrollBars = true, smoothScrolling = true, scrollBarTouch = true;
    float fadeAlpha, fadeAlphaSeconds = 1, fadeDelay, fadeDelaySeconds = 1;
    boolean cancelTouchFocus = true;

    boolean flickScroll = true;
    float flingTime = 1f, flingTimer, velocityX, velocityY;
    private boolean overscrollX = true, overscrollY = true;
    private float overscrollDistance = 50, overscrollSpeedMin = 30, overscrollSpeedMax = 200;
    private boolean forceScrollX, forceScrollY;
    boolean disableX, disableY;
    private boolean clamp = true;
    private boolean scrollbarsOnTop;
    private boolean variableSizeKnobs = true;
    int draggingPointer = -1;

    /**
     * Konstruktor für Objekte der Klasse Scrollpane_min
     */
    public Scrollpane_min(Actor actor, Skin skin)
    {
        super(actor,skin);
    }
    
    public Scrollpane_min (Actor actor) {
        super(actor, new ScrollPaneStyle());
    }

    protected void addCaptureListener () {
        addCaptureListener(new InputListener() {
            private float handlePosition;

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (draggingPointer != -1) return false;
                if (pointer == 0 && button != 0) return false;
                if (getStage() != null) getStage().setScrollFocus(Scrollpane_min.this);

                if (!flickScroll) setScrollbarsVisible(true);

                if (fadeAlpha == 0) return false;

                if (scrollBarTouch && scrollX && hScrollBounds.contains(x, y)) {
                    // event.stop();
                    // setScrollbarsVisible(true);
                    // if (hKnobBounds.contains(x, y)) {
                        // lastPoint.set(x, y);
                        // handlePosition = hKnobBounds.x;
                        // touchScrollH = true;
                        // draggingPointer = pointer;
                        // return true;
                    // }
                    // setScrollX(amountX + actorArea.width * (x < hKnobBounds.x ? -1 : 1));
                    // return true;
                }
                if (scrollBarTouch && scrollY && vScrollBounds.contains(x, y)) {
                    // event.stop();
                    // setScrollbarsVisible(true);
                    // if (vKnobBounds.contains(x, y)) {
                        // lastPoint.set(x, y);
                        // handlePosition = vKnobBounds.y;
                        // touchScrollV = true;
                        // draggingPointer = pointer;
                        // return true;
                    // }
                    // setScrollY(amountY + actorArea.height * (y < vKnobBounds.y ? 1 : -1));
                    // return true;
                }
                return false;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != draggingPointer) return;
                cancel();
            }

            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                if (pointer != draggingPointer) return;
                if (touchScrollH) {
                    // float delta = x - lastPoint.x;
                    // float scrollH = handlePosition + delta;
                    // handlePosition = scrollH;
                    // scrollH = Math.max(hScrollBounds.x, scrollH);
                    // scrollH = Math.min(hScrollBounds.x + hScrollBounds.width - hKnobBounds.width, scrollH);
                    // float total = hScrollBounds.width - hKnobBounds.width;
                    // if (total != 0) setScrollPercentX((scrollH - hScrollBounds.x) / total);
                    // if(!hScrollBounds.contains(x, y))
                            // return;
                        // lastPoint.set(x, y);
                } else if (touchScrollV) {
                    // float delta = y - lastPoint.y;
                    // float scrollV = handlePosition + delta;
                    // handlePosition = scrollV;
                    // scrollV = Math.max(vScrollBounds.y, scrollV);
                    // scrollV = Math.min(vScrollBounds.y + vScrollBounds.height - vKnobBounds.height, scrollV);
                    // float total = vScrollBounds.height - vKnobBounds.height;
                    // if (total != 0) setScrollPercentY(1 - (scrollV - vScrollBounds.y) / total);
                    // lastPoint.set(x, y);
                }
            }

            public boolean mouseMoved (InputEvent event, float x, float y) {
                if (!flickScroll) setScrollbarsVisible(true);
                return false;
            }
        });
    }
    
    
    protected ActorGestureListener getFlickScrollListener () {
        return new ActorGestureListener() {
            public void pan (InputEvent event, float x, float y, float deltaX, float deltaY) {
                // setScrollbarsVisible(true);
                // if (!scrollX) deltaX = 0;
                // if (!scrollY) deltaY = 0;
                // amountX -= deltaX;
                // amountY += deltaY;
                // clamp();
                // if (cancelTouchFocus && (deltaX != 0 || deltaY != 0)) cancelTouchFocus();
            }

            public void fling (InputEvent event, float x, float y, int button) {
                float velocityX = Math.abs(x) > 150 && scrollX ? x : 0;
                float velocityY = Math.abs(y) > 150 && scrollY ? -y : 0;
                if (velocityX != 0 || velocityY != 0) {
                    if (cancelTouchFocus) cancelTouchFocus();
                    Scrollpane_min.this.fling(flingTime, velocityX, velocityY);
                }
            }

            public boolean handle (Event event) {
                if (super.handle(event)) {
                    if (((InputEvent)event).getType() == InputEvent.Type.touchDown) flingTimer = 0;
                    return true;
                } else if (event instanceof InputEvent && ((InputEvent)event).isTouchFocusCancel()) //
                    cancel();
                return false;
            }
        };
    }
    
    protected void addScrollListener () {
        addListener(new InputListener() {
            public boolean scrolled (InputEvent event, float x, float y, float scrollAmountX, float scrollAmountY) {
                
                return true;
            }
        });
    }
    
    /** Shows or hides the scrollbars for when using {@link #setFadeScrollBars(boolean)}. */
    public void setScrollbarsVisible (boolean visible) {
        if (visible) {
            fadeAlpha = fadeAlphaSeconds;
            fadeDelay = fadeDelaySeconds;
        } else {
            fadeAlpha = 0;
            fadeDelay = 0;
        }
    }
}
