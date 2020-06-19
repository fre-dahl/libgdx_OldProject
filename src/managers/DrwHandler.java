package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import culling.Section;
import drawdata.drwabstract.DrwDat;
import main.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DrwHandler {

    private static final String TAG = DrwHandler.class.getName();
    public static DrwHandler instance = new DrwHandler();
    private final Matrix4 originalMatrixTemp = new Matrix4();
    private static final Matrix4 IDENTITY = new Matrix4();
    private Map<Integer, ArrayList<DrwDat>> layers;
    private OrthographicCamera camera;
    private FrameBuffer fbo;
    private SpriteBatch batch;
    private Texture red;



    private static final int NUM_LAYERS = 8;
    private static final boolean[] RENDER = new boolean[NUM_LAYERS];
    private static final boolean[] SORT = new boolean[NUM_LAYERS];

    private DrwHandler() {
        red = red = new Texture(Gdx.files.internal("res/testing/red.png"));
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Settings.SCREEN_W,Settings.SCREEN_H,false);
        batch = new SpriteBatch();
        layers = new HashMap<>();
        layers.put(0,new ArrayList<>());
        layers.put(1,new ArrayList<>());
        layers.put(2,new ArrayList<>());
        layers.put(3,new ArrayList<>());
        layers.put(4,new ArrayList<>());
        layers.put(5,new ArrayList<>());
        layers.put(6,new ArrayList<>());
        layers.put(7,new ArrayList<>());
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void draw() {

        Gdx.gl.glClearColor(1, 1, 1, 1); // clearing with WHITE to see the framebuffer texture
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i < NUM_LAYERS; i++) {
            if (RENDER[i]) {
                if (SORT[i]) Collections.sort(layers.get(i));
                //for (DrwDat dat: layers.get(i)) { dat.draw(batch);}

                if (i==1) { renderFbo(i); } // calling the framebuffer-using method for "water layer"
                else{
                    for (DrwDat dat: layers.get(i)) {
                        dat.draw(batch);
                    }
                }


            }
        }

        batch.end();
        Section.resetSections();
    }

    private void renderFbo(int layer) {
        batch.flush();

        originalMatrixTemp.set(batch.getProjectionMatrix());
        int originalBlendSrcFunc = batch.getBlendSrcFunc();
        int originalBlendDstFunc = batch.getBlendDstFunc();

        batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE);

        fbo.begin();

        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Drawing the layer unto the framebuffer:
        //for (DrwDat dat: layers.get(layer)) { dat.draw(batch); }
        //batch.draw(red,0,0,500,500);
        batch.flush();
        fbo.end();

        batch.setColor(Color.WHITE);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setProjectionMatrix(IDENTITY);

        // Halving the output texture to see the issue clearer. whole screen: (-1, 1, 2, -2)
        batch.draw(fbo.getColorBufferTexture(), -0.5f, 0.5f, 1f, -1f);
        batch.flush();

        batch.setProjectionMatrix(originalMatrixTemp);
        batch.setBlendFunction(originalBlendSrcFunc, originalBlendDstFunc);
    }

    public void setAll(boolean draw) {
        for (int i = 0; i < NUM_LAYERS; i++) {
            RENDER[i] = draw; }
    }

    public void set(int layer, boolean draw) {
        RENDER[layer] = draw;
    }

    public void sort(int layer, boolean sort) {
        SORT[layer] = sort;
    }

    public void add(DrwDat dat) {
        if (dat != null) {
            int layer = dat.getZ();
            if (layers.containsKey(layer))
                layers.get(layer).add(dat); }
    }

    public void transfer(DrwDat dat, int newLayer) {
        if (newLayer >= (0) && newLayer < NUM_LAYERS) {
            if (dat != null) { int oldLayer = dat.getZ();
                if (layers.get(oldLayer).contains(dat)) {
                    layers.get(oldLayer).remove(dat);
                    layers.get(newLayer).add(dat); } } }
    }

    public void clear() {
        for(Map.Entry<Integer,ArrayList<DrwDat>> entry : layers.entrySet()) {
            entry.getValue().clear(); }
        for (int i = 0; i < NUM_LAYERS; i++){
            RENDER[i] = false; SORT[i] = false; }
        fbo.dispose();
        red.dispose();
    }

    public int getLayerSize(int layer) {
        if (layers.containsKey(layer)) return layers.get(layer).size();
        else {
            Gdx.app.debug(TAG, "Layer does not exist: layer " + layer);
            return 9999999;
        }
    }
    public void remove(DrwDat dat) {
        if (dat!=null) { layers.get((int)dat.getZ()).remove(dat); }
    }
}
