package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import graphics.culling.Section;
import graphics.drwdat.abstr.DrwDat;
import main.Settings;

import static java.util.Map.entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class DrwHandler {


    public static final int LAYER_0 = 0;
    public static final int LAYER_1 = 1;
    public static final int LAYER_2 = 2;
    public static final int LAYER_3 = 3;
    public static final int LAYER_4 = 4;
    public static final int LAYER_5 = 5;
    public static final int LAYER_6 = 6;
    public static final int LAYER_7 = 7;


    public static final byte NUM_LAYERS = 8;
    public static final String TAG = DrwHandler.class.getName();
    private static final boolean[] RENDER = new boolean[NUM_LAYERS];
    private static final boolean[] SORT = new boolean[NUM_LAYERS];

    private final static Map<Integer,ArrayList<DrwDat>> LAYERS = Map.ofEntries(
            entry(LAYER_0, new ArrayList<>()),
            entry(LAYER_1, new ArrayList<>()),
            entry(LAYER_2, new ArrayList<>()),
            entry(LAYER_3, new ArrayList<>()),
            entry(LAYER_4, new ArrayList<>()),
            entry(LAYER_5, new ArrayList<>()),
            entry(LAYER_6, new ArrayList<>()),
            entry(LAYER_7, new ArrayList<>())
    );

    private SpriteBatch batch;
    private FrameBuffer fbo;
    private OrthographicCamera camera;
    //private static Matrix4 screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, Settings.SCREEN_W, Settings.SCREEN_H));
    //private static TextureRegion bufferTexture = new TextureRegion(frameBuffer.getColorBufferTexture());

    // remember the fbo must be disposed;
    private final Matrix4 originalMatrixTemp = new Matrix4();
    private static final Matrix4 IDENTITY = new Matrix4();


    public void disposeFbo() {
        fbo.dispose();
    }
    public void init(OrthographicCamera camera ) {
        batch = new SpriteBatch();
        this.camera = camera;
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888,Settings.SCREEN_W,Settings.SCREEN_H,false);

    }
    private void renderfbo(int layer) {
        batch.flush();

        originalMatrixTemp.set(batch.getProjectionMatrix());
        int originalBlendSrcFunc = batch.getBlendSrcFunc();
        int originalBlendDstFunc = batch.getBlendDstFunc();
        batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE);

        fbo.begin();
        Gdx.gl.glClearColor(0, 1f, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (DrwDat dat: LAYERS.get(layer)) {
            dat.draw(batch);
        }
        batch.flush();
        fbo.end();


        batch.setColor(Color.WHITE);



        batch.setProjectionMatrix(IDENTITY);
        //BATCH.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //BATCH.draw(frameBuffer.getColorBufferTexture(), -1, 1, 2, -2);
        batch.draw(fbo.getColorBufferTexture(), -0.5f, 0.5f, 1f, -1f);
        batch.flush();


        batch.setProjectionMatrix(originalMatrixTemp);
        batch.setBlendFunction(originalBlendSrcFunc, originalBlendDstFunc);

    }
    public void draw() {

        batch.enableBlending();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i < NUM_LAYERS; i++) {
            if (RENDER[i]) {
                if (SORT[i]) Collections.sort(LAYERS.get(i));
                if (i==1) {
                    renderfbo(i);
                }
                else{
                    for (DrwDat dat: LAYERS.get(i)) {
                    dat.draw(batch);
                }
                }

            }
        }

        batch.end();
        Section.resetSections();
    }

    public static void setAll(boolean draw) {
        for (int i = 0; i < NUM_LAYERS; i++) {
            RENDER[i] = draw; }
    }

    public static void set(int layer, boolean draw) {
        RENDER[layer] = draw;
    }

    public static void sort(int layer, boolean sort) {
        SORT[layer] = sort;
    }

    public static void add(DrwDat dat) {
        if (dat != null) {
            int layer = dat.getZ();
            if (LAYERS.containsKey(layer))
            LAYERS.get(layer).add(dat); }
    }

    public static void transfer(DrwDat dat, int newLayer) {
        if (newLayer >= (LAYER_0) && newLayer < NUM_LAYERS) {
            if (dat != null) { int oldLayer = dat.getZ();
                if (LAYERS.get(oldLayer).contains(dat)) {
                    LAYERS.get(oldLayer).remove(dat);
                    LAYERS.get(newLayer).add(dat); } } }
    }

    public static void clear() {
        for(Map.Entry<Integer,ArrayList<DrwDat>> entry : LAYERS.entrySet()) {
            entry.getValue().clear(); }
        for (int i = 0; i < NUM_LAYERS; i++){
            RENDER[i] = false; SORT[i] = false; }
        //disposeFbo();
    }

    public static int getLayerSize(int layer) {
        if (LAYERS.containsKey(layer)) return LAYERS.get(layer).size();
        else {
            Gdx.app.debug(TAG, "Layer does not exist: layer " + layer);
            return 9999999;
        }
    }
    public static void remove(DrwDat dat) {
        if (dat!=null) { LAYERS.get((int)dat.getZ()).remove(dat); }
    }


}
