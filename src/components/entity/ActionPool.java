package components.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import components.entity.Action.Intent;

public abstract class ActionPool extends Pool<Action> {

    public abstract void initMove(Entity actor, Vector2 target);

    public abstract void initAction(Entity actor, WorldObject target, Intent intent);

    public interface ActionPoolItem extends Poolable {
        public void onNewAction();
    }

}
