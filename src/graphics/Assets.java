package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import components.Biome;
import main.Settings;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static Assets instance = new Assets();
    private String currentAtlas;
    private AssetManager assetManager;

    // graphics.assets:
    public AssetTiles assetTiles;
    public AssetClouds assetClouds;
    public AssetRipples assetRipples;
    public AssetKnight assetKnight;


    private Assets() { }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
    }

    private void loadAtlas(AssetDescriptor<TextureAtlas> atlas) {
        assetManager.load(atlas);
        assetManager.finishLoading();
        currentAtlas = atlas.fileName;
        Gdx.app.debug(TAG, "# of graphics.assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG,"asset: " + a);
        }
    }

    public void loadBiome(Biome biome) {
        AssetDescriptor<TextureAtlas> atlas = biome.getAtlas();
        if (!atlas.fileName.equals(currentAtlas)) {
            loadAtlas(atlas);
            TextureAtlas textureAtlas = assetManager.get(atlas);
            switch (biome.getBiome()) {
                case LAKES:
                    assetTiles = new AssetLakes(textureAtlas);
                    break;
                case TUNDRA:
                    assetTiles = new AssetLakes(textureAtlas);
                    assetClouds = new AssetClouds(textureAtlas);
                    assetRipples = new AssetRipples(textureAtlas);
                    assetKnight = new AssetKnight(textureAtlas);
                    break;
                default:
                    assetTiles = new AssetLakes(textureAtlas);
                    break;
            }
        }
    }

    @Override
    public void dispose() {
        assetTiles = null;
        assetClouds = null;
        assetRipples = null;
        assetKnight = null;
        currentAtlas = "";
        assetManager.dispose();
    }

    public void bleedFix(TextureRegion region) {
        float fix = 0.01f;
        float x = region.getRegionX();
        float y = region.getRegionY();
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float invTexWidth = 1f / region.getTexture().getWidth();
        float invTexHeight = 1f / region.getTexture().getHeight();
        region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims Region
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    public class AssetKnight {

        public final Array<Array<TextureRegion>> knight_idle;
        public final Array<Array<TextureRegion>> knight_walk;
        public final Animation<TextureRegion> knight_explode;
        public final TextureRegion knight_shadow;

        public AssetKnight(TextureAtlas atlas) {
            knight_idle = split(atlas.findRegion("knight_yellow_idle"),16,21);
            knight_walk = split(atlas.findRegion("knight_yellow_walk"),16,21);
            TextureRegion explode = atlas.findRegion("knight_yellow_explode");
            knight_explode = new Animation<TextureRegion>(0.06f,getKeyFrames(explode,16), Animation.PlayMode.NORMAL);
            knight_shadow = new TextureRegion(atlas.findRegion("knight_yellow_shadow"),0,0,16,8);
            bleedFix(knight_shadow);
        }

        private Array<Array<TextureRegion>> split(TextureRegion region, int width, int height) {
            if (region!=null) {
                Array<Array<TextureRegion>> grid = new Array<>();
                TextureRegion keyFrame;
                int rows = region.getRegionHeight()/height;
                int cols = region.getRegionWidth()/width;
                for (int row = 0; row < rows; row++) {
                    Array<TextureRegion> keyFrameRow = new Array<>();
                    for (int col = 0; col < cols; col++) {
                        keyFrame = new TextureRegion(region,col*width, row*height, width, height);
                        keyFrameRow.add(keyFrame);
                    }
                    grid.add(keyFrameRow);
                }
                return grid;
            }
            return null;
        }

        private Array<TextureRegion> getKeyFrames(TextureRegion r, int frames) {
            Array<TextureRegion> keyframes = new Array<>();
            int w = r.getRegionWidth() / frames;
            int h = r.getRegionHeight();
            TextureRegion frame;
            for (int i = 0; i < frames; i++) {
                frame = new TextureRegion(r,w*i,0,w,h);
                keyframes.add(frame);
            }
            return keyframes;
        }

    }

    public class AssetRipples {

        public final Animation<TextureRegion> anim;

        public AssetRipples(TextureAtlas atlas) {

            TextureRegion ripples_region = atlas.findRegion("ripples_anim");
            anim = new Animation<>(0.1f, getKeyFrames(ripples_region), Animation.PlayMode.NORMAL);
        }

        private Array<TextureRegion> getKeyFrames(TextureRegion r) {
            int frames = 11;
            Array<TextureRegion> keyframes = new Array<>();
            int w = r.getRegionWidth() / frames;
            int h = r.getRegionHeight();
            TextureRegion frame;
            for (int i = 0; i < frames; i++) {
                frame = new TextureRegion(r,w*i,0,w,h);
                keyframes.add(frame);
            }
            return keyframes;
        }
    }

    public class AssetClouds {

        //public final TextureRegion cloud_small;
        //public final TextureRegion cloud_medium;
        //public final TextureRegion cloud_large;
        public final TextureRegion cloud_large_new;

        public AssetClouds(TextureAtlas atlas) {

            /*
            cloud_small = atlas.findRegion("cloud_small");
            cloud_medium = atlas.findRegion("cloud_medium");
            cloud_large = atlas.findRegion("cloud_large");
             */
            cloud_large_new = atlas.findRegion("cloud_large_new");

        }

        public TextureRegion getCloud() {
            return cloud_large_new;
        }
        /*
        public TextureRegion getRandom() {
            int cloud = MathUtils.random(1,3);
            switch (cloud){
                case 1: return cloud_small;
                case 2: return cloud_medium;
                case 3: return cloud_large;
                default: return null;
            }
        }
         */
    }

    public class AssetLakes extends AssetTiles {

        public final Array<Array<TextureRegion>> waterDeep;     // tier 0
        public final Array<Array<TextureRegion>> water;         // tier 1
        public final Array<Array<TextureRegion>> waterShallow;  // tier 2
        public final Array<Array<TextureRegion>> shoreWater;    // tier 3 0
        public final Array<Array<TextureRegion>> shoreLand;     // tier 3 1
        public final Array<Array<TextureRegion>> grass;         // tier 4
        public final Array<Array<TextureRegion>> grassLight;    // tier 5
        public final Array<Array<TextureRegion>> snow;          // secondary

        public AssetLakes(TextureAtlas atlas) {
            super(atlas);
            waterDeep = split(atlas.findRegion("tileset_waterDeep"), Settings.TILE_SIZE_M);
            water = split(atlas.findRegion("tileset_water"), Settings.TILE_SIZE_M);
            waterShallow = split(atlas.findRegion("tileset_waterShallow"), Settings.TILE_SIZE_M);
            shoreWater = split(atlas.findRegion("tileset_shoreWater"), Settings.TILE_SIZE_M);
            shoreLand = split(atlas.findRegion("tileset_shoreGround"), Settings.TILE_SIZE_M);
            grass = split(atlas.findRegion("tileset_grass"), Settings.TILE_SIZE_M);
            grassLight = split(atlas.findRegion("tileset_grassLight"), Settings.TILE_SIZE_M);
            snow = split(atlas.findRegion("tileset_snow"), Settings.TILE_SIZE_M);
        }

        @Override
        public TextureRegion getTileRegion(int tier, int mask) {
            int rowSize = 8;
            int row = mask / rowSize;
            int col = mask % rowSize;
            switch (tier) {
                case 0: return waterDeep.get(row).get(col);
                case 1: return water.get(row).get(col);
                case 2: return waterShallow.get(row).get(col);
                case 3: return shoreWater.get(row).get(col);
                case 4: return grass.get(row).get(col);
                case 5: return grassLight.get(row).get(col);
                default: Gdx.app.debug(TAG,"Invalid argument * tier * in method * getTileRegion *");

                return null;
            }
        }

        @Override
        public TextureRegion getShoreGroundTile(int mask) {
            int rowSize = 8;
            int row = mask / rowSize;
            int col = mask % rowSize;
            return shoreLand.get(row).get(col);
        }

        @Override
        public TextureRegion getSecondary(int mask) {
            int rowSize = 8;
            int row = mask / rowSize;
            int col = mask % rowSize;
            return snow.get(row).get(col);
        }
    }

    public abstract class AssetTiles {

        public AssetTiles(TextureAtlas atlas) { }

        public Array<Array<TextureRegion>> split(TextureRegion region, int size) {
            if (region!=null) {
                Array<Array<TextureRegion>> tiles = new Array<>();
                TextureRegion tile;
                int rows = region.getRegionHeight()/size;
                int cols = region.getRegionWidth()/size;
                for (int row = 0; row < rows; row++) {
                    Array<TextureRegion> tileRow = new Array<>();
                    for (int col = 0; col < cols; col++) {
                        tile = new TextureRegion(region,col*size, row*size, size, size);
                        Assets.this.bleedFix(tile);
                        tileRow.add(tile);
                    }
                    tiles.add(tileRow);
                }
                return tiles;
            }
            return null;
        }

        public void bleedFix(TextureRegion region) {
            float fix = 0.01f;
            float x = region.getRegionX();
            float y = region.getRegionY();
            float width = region.getRegionWidth();
            float height = region.getRegionHeight();
            float invTexWidth = 1f / region.getTexture().getWidth();
            float invTexHeight = 1f / region.getTexture().getHeight();
            region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims Region
        }

        public TextureRegion getTileRegion(int tier, int mask) {
            Gdx.app.debug(TAG,"Empty method * getTileRegion * in super * AssetTiles *");
            return null;
        }

        public TextureRegion getShoreGroundTile(int mask) {
            Gdx.app.debug(TAG,"Empty method * getShoreGroundTile * in super * AssetTiles *");
            return null;
        }

        public TextureRegion getSecondary(int mask) {
            Gdx.app.debug(TAG,"Empty method * getSecondary * in super * AssetTiles *");
            return null;
        }

        public TextureRegion getTileRNG(int tier, float chance) {return null;}
    }
}
