package graphics.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import graphics.shaders.abstr.ShaderWrapper;
import main.Settings;

public class WaterWrapper extends ShaderWrapper {

    private static final float WATER_PERIOD = 10.0f; private float waterTime = 0f;
    private final String vert =  Gdx.files.internal("res/shaders/water/water.vert").readString();
    private final String noise = Gdx.files.internal("res/shaders/water/classicnoise3D.glsl").readString();
    private final String frag =  Gdx.files.internal("res/shaders/water/water.frag").readString().replace("#FUNCTION_INSERT", noise);


    @Override
    public void init(ShaderHandler handler) {
        loadProgram();
    }

    @Override
    public void update(float dt) {
        waterTime = (waterTime + dt) % WATER_PERIOD;
        program.setUniformf("u_time", waterTime);
        program.setUniformf("u_waterPeriod", WATER_PERIOD);
        program.setUniformf("u_resolution", Settings.SCREEN_W, Settings.SCREEN_H);
        program.setUniformf("u_tone",0.9f, 0.4f, 0.4f, 1.0f);
    }


    private void loadProgram() {
        if (program != null) program.dispose();
        ShaderProgram.pedantic = false;
        program = new ShaderProgram(vert, frag);
        if (!program.isCompiled()) {
            Gdx.app.error("Water shader", "Failed to compile:\n" + program.getLog());
        }
    }
}
