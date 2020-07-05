package graphics.shaders;

import graphics.shaders.abstr.ShaderWrapper;

public class DefaultWrapper extends ShaderWrapper {


    @Override
    public void init(ShaderHandler handler) {
        this.program = handler.getBatch().getShader();
    }

    @Override
    public void update(float dt) {

    }
}
