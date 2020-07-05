package components.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import components.entity.Action.Intent;
import components.tile.Tile;
import components.tile.TileMap;
import graphics.culling.Section;
import ui.UI.Selectable;

public abstract class Entity extends WorldObject implements Selectable {

    State state;
    TileMap map;
    Type type;
    Tile currentTile;
    Section currentSection;
    Vector2 texturePosition;
    Vector2 centerPosition;
    Vector2 velocity;
    Circle proximity;
    Rectangle selectBox;
    Array<Action> actionQueue;

    float moveSpeed = 50;

    // Flags
    boolean moving; // set in actions
    boolean nearWater; // set in setCurrentTile
    boolean inWater; // later
    boolean selected;
    boolean hovered;

    public Entity(Type type, TileMap map, float x, float y) {
        this.map = map;
        this.type = type;
        actionQueue = new Array<>();
        velocity = new Vector2();
        position = new Vector2(x,y);
        updateGridPos(); // updated in actions in update
        texturePosition = new Vector2(x - type.w/2 , y);
        centerPosition = new Vector2(x, y + type.h/2);
        selectBox = new Rectangle(x - type.w/2 , y, type.w * scale, type.h * scale);
        proximity = new Circle(x,y,type.w * scale);

    }

    public abstract void update(float dt);

    public enum State {
        IDLE,
        WALK,
        ATTACK
    }

    public enum Type {

        KNIGHT(16,21);

        private float w, h;

        Type(float w, float h) {
            this.w = w; this.h = h;
        }
        public float getW() {return w;}
        public float getH() {return h;}
    }

    private static ActionPool actionPool = new ActionPool() {

        private Entity actor;
        private WorldObject targetObject;
        private Vector2 targetPosition;
        private Intent intent;
        private boolean newObject;


        @Override
        public void initMove(Entity actor, Vector2 target) {

            newObject = false;
            intent = Intent.MOVE_TO_POSITION;
            this.actor = actor;
            targetPosition = target;
            Action action = this.obtain();
            if(!newObject) action.init(actor, target, intent);
            actor.actionQueue.add(action);
            action.onNewAction();
        }

        @Override
        public void initAction(Entity actor, WorldObject target, Intent intent) {

            newObject = false;
            this.intent = intent;
            this.actor = actor;
            this.targetObject = target;
            Action action = this.obtain();
            if(!newObject) action.init(actor, target, intent);
            actor.actionQueue.add(action);
            action.onNewAction();
        }

        @Override
        protected Action newObject() {

            newObject = true;
            if(intent == Intent.MOVE_TO_POSITION) return new Action(actor, new Vector2(targetPosition.x,targetPosition.y));
            else return new Action(actor, targetObject, intent);
        }

    };

    public void updateGridPos() {
        map.setGridPositions(this);
        nearWater = currentTile.reflects();
    }
    public void moveTo(Vector2 target) { actionPool.initMove(this, target);}

    public ActionPool getActionPool() {return actionPool; }

    public void setSection(Section section) {
        this.currentSection = section;
    }

    public void setTile(Tile tile) {
        this.currentTile = tile;
    }

    @Override
    public Circle getProximity() { return proximity; }

}
