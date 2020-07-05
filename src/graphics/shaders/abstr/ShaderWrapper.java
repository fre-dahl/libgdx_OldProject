package graphics.shaders.abstr;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import graphics.shaders.ShaderHandler;

public abstract class ShaderWrapper implements Disposable {

    public ShaderProgram program;

    public abstract void init(ShaderHandler handler);
    public abstract void update(float dt);
    private void loadProgram(){};

    @Override
    public void dispose() { program.dispose(); }
}
