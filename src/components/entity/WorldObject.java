package components.entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import components.entity.Action.Interactable;
import components.entity.Action.Intent;
import main.Settings;

public abstract class WorldObject implements Interactable, Disposable {

    final static int scale = Settings.SCALE;
    Vector2 position;

    public Vector2 getPosition() { return position; }

    @Override
    public void attacked(Entity attacker) { }

    @Override
    public void interaction(Entity interactor) { }

    @Override
    public void targeted(Entity actor, Intent intent) { }


}
