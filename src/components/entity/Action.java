package components.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import components.entity.Entity.State;
import components.entity.ActionPool.ActionPoolItem;
import main.Settings;

public class Action implements ActionPoolItem {

    private Entity actor;
    private WorldObject targetObject;
    private Vector2 targetPosition;
    private Intent intent;
    private boolean performing;
    private boolean targetReached;
    private boolean complete;
    private static final int ZERO_MARGIN = 4;

    public Action(Entity actor, Vector2 targetPosition) { // target gets created in pool newObject().
        this.targetPosition = targetPosition;
        this.actor = actor;
        this.intent = Intent.MOVE_TO_POSITION;
        if(actor.getProximity().contains(targetPosition)) {
            targetReached = true;
            complete = true;
        }
    }

    public Action(Entity actor, WorldObject targetObject, Intent intent) {
        if (intent == Intent.MOVE_TO_POSITION) this.intent = Intent.MOVE_TO_WORLD_OBJECT;
        else this.intent = intent;
        targetPosition = new Vector2(); // must be set for pool purposes
        targetObject.targeted(actor, intent);
        this.actor = actor;
        this.targetObject = targetObject;
        if(actor.getProximity().overlaps(targetObject.getProximity())) {
            targetReached = true;
            if(this.intent == Intent.MOVE_TO_WORLD_OBJECT) complete = true;
        }
    }

    public void execute(float dt) {

        if(complete) {
            actor.actionQueue.removeValue(this, true);
            actor.getActionPool().free(this);
            return;
        }
        if(intent == Intent.MOVE_TO_POSITION) {
            moveToPosition(dt); // set complete here
        }
        else{
            targetReached = actor.getProximity().overlaps(targetObject.getProximity());
            if(targetReached) {
                switch(intent) {
                    case MOVE_TO_WORLD_OBJECT: complete = true; break;
                    case INTERACT_WITH: targetObject.interaction(actor); break; // set action complete in targetObject
                    case ATTACK: targetObject.attacked(actor); break; // set action complete in targetObject
                }
            }
            else{
                moveToTarget(dt);
            }
        }
    }

    private void moveToTarget(float dt) {
        float x = targetObject.position.x - actor.position.x;
        float y = targetObject.position.y - actor.position.y;
        actor.velocity.set(x,y);
        actor.velocity.nor();
        actor.velocity.scl(actor.moveSpeed);
        actor.position.add(actor.velocity.x * dt * Settings.SCALE, actor.velocity.y * dt * Settings.SCALE);
        actor.updateGridPos();
    }

    private void moveToPosition(float dt) {
        float x = targetPosition.x - actor.position.x;
        float y = targetPosition.y - actor.position.y;
        actor.velocity.set(x,y);
        targetReached = Math.abs(actor.velocity.len()) < ZERO_MARGIN;
        if (targetReached) {
            actor.position.set(targetPosition);
            complete = true;
        }
        else {
            actor.velocity.nor();
            actor.velocity.scl(actor.moveSpeed);
            actor.position.add(actor.velocity.x * dt * Settings.SCALE, actor.velocity.y * dt * Settings.SCALE);
        }
        actor.updateGridPos();
    }


    public void init(Entity actor, Vector2 targetPosition, Intent intent) {
        this.targetPosition.set(targetPosition.x, targetPosition.y);
        this.intent = intent;
        this.actor = actor;
        if(actor.getProximity().contains(targetPosition)) {
            complete = true;
        }
    }

    public void init(Entity actor, WorldObject targetObject, Intent intent) {
        if (intent == Intent.MOVE_TO_POSITION) intent = Intent.MOVE_TO_WORLD_OBJECT;
        this.targetObject = targetObject;
        this.intent = intent;
        this.actor = actor;
        targetObject.targeted(actor, intent);
        if(actor.getProximity().overlaps(targetObject.getProximity())) {
            targetReached = true;
            if (this.intent == Intent.MOVE_TO_WORLD_OBJECT) complete = true;
        }
    }

    @Override
    public void onNewAction() {
        if (actor.actionQueue.size == 1) {
            switch (intent) {
                case MOVE_TO_POSITION:
                case MOVE_TO_WORLD_OBJECT: actor.state = State.WALK; break;
            }
        }
    }

    @Override
    public void reset() {
        if (actor.actionQueue.size > 0) {
            Intent newIntent = actor.actionQueue.get(0).intent;
            switch (newIntent) {
                case MOVE_TO_POSITION:
                case MOVE_TO_WORLD_OBJECT: actor.state = State.WALK; break;
            }
        }
        else actor.state = State.IDLE;
        targetReached = false;
        performing = false;
        complete = false;
    }


    public enum Intent {
        MOVE_TO_POSITION,
        MOVE_TO_WORLD_OBJECT,
        FLEE_FROM,
        WAIT,
        PATROL,
        INTERACT_WITH,
        ATTACK
    }

    public interface Interactable {

        void attacked(Entity attacker);
        void interaction(Entity interactor);
        void targeted(Entity actor, Intent intent);

        Circle getProximity();
    }
}
