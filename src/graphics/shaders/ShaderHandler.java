package graphics.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import graphics.shaders.abstr.ShaderWrapper;

public class ShaderHandler implements Disposable {

    private SpriteBatch batch;
    private ShaderWrapper currentShader;
    private ShaderWrapper defaultShader;
    private ShaderWrapper waterShader;

    public static final int DEFAULT = 0;
    public static final int WATER = 1;
    private static int current;


    public ShaderHandler(SpriteBatch batch) {
        this.batch = batch;
        defaultShader = new DefaultWrapper();
        waterShader = new WaterWrapper();
        defaultShader.init(this);
        waterShader.init(this);
        currentShader = defaultShader;
        current = DEFAULT;
    }

    public void setShader(int shader) {
        if (shader == DEFAULT) {
            currentShader = defaultShader;
            current = DEFAULT;
        }
        if (shader == WATER) {
            currentShader = waterShader;
            current = WATER;
        }
        batch.setShader(currentShader.program);
    }


    public void update() {
        currentShader.update(Gdx.graphics.getDeltaTime());
    }


    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        // Default gets disposed when batch get's disposed
        waterShader.dispose();
    }
}
