package components;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import components.tile.tilecoding.psuedoComponents.PsuedoTile;


public class Biome {

    private BIOME biome;
    private AssetDescriptor<TextureAtlas> atlas;
    public static final String BIOME_TUNDRA = "res/testing/atlas/biome_tundra.atlas";


    public Biome(BIOME biome) {
        this.biome = biome;
        atlas = new AssetDescriptor<>(biome.path, TextureAtlas.class);
        switch (biome){
            case LAKES:
                break;
            case COAST:
                break;
            case MOUNTAIN:
                break;
            case DESSERT:
                break;
            case PLAINS:
                break;
            case FOREST:
                break;
            case CAVE:
                break;
            case TUNDRA:
                break;
            default:
                break;
        }
    }

    public enum BIOME {

        LAKES(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        COAST(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        PLAINS(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        FOREST(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        MOUNTAIN(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        DESSERT(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        CAVE(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.7f),
        TUNDRA(BIOME_TUNDRA,0.25f,0.32f,0.4f,0.5f,0.68f);

        float t0,t1,t2,t3,t4; // terrain generation thresholds
        String path;

        BIOME(String path, float t0, float t1, float t2, float t3, float t4) {
            this.path = path;
            this.t0 = t0;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
        }
    }

    public void setTier(PsuedoTile tile) {
        float n = tile.noiceValue;
        if 		(n <= biome.t0) 			    tile.tier = 0;
        else if (n > biome.t0 && n <= biome.t1)	tile.tier = 1;
        else if (n > biome.t1 && n <= biome.t2)	tile.tier = 2;
        else if (n > biome.t2 && n <= biome.t3)	tile.tier = 3;
        else if (n > biome.t3 && n <= biome.t4)	tile.tier = 4;
        else 							        tile.tier = 5;
    }

    public AssetDescriptor<TextureAtlas> getAtlas() { return atlas; }

    public BIOME getBiome() {return biome;}
}
