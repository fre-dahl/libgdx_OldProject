package enums;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Enums {

    public enum GROUP_POS {
        // Type given to all 8x8 tiles to represent it's pos within a 16x16 group-tile

        SW,
        SE,
        NW,
        NE
    }

    public enum IMPRINT {
        // Probably not the best name. This will contain different geological types
        // to represent some general tilemap shape. Used in NoiseGenerator.class to
        // alter birthrate. The names has nothing to do with spesific biomes. for example
        // RIVER does not represent water but rather some general imprint.

        RIVER,
        MOUND,
        RECTANGLE;
    }

    public enum TILETYPE {

        DEEP_WATER(false),
        WATER(true),
        BANK(true),
        GRASS(true),
        ROAD(true),
        BRICK(true),
        ROCK_WALL(false),
        NO_TYPE(false);

        private boolean passable;

        private TILETYPE (boolean passable) {
            this.passable = passable;
        }

        public boolean isPassable() {
            return passable;
        }
    }

    public enum BIOME {

        LAKES,
        COAST,
        PLAINS,
        FOREST,
        MOUNTAIN,
        DESSERT,
        CAVE
    }

    public enum DIRECTION {

        NORTH(0, 1),
        NORTH_EAST(1, 1),
        EAST(1, 0),
        SOUTH_EAST(1, -1),
        SOUTH(0, -1),
        SOUTH_WEST(-1,-1),
        WEST(-1, 0),
        NORTH_WEST(-1,1);

        private Vector2 vector;

        DIRECTION (int dx , int dy) {
            vector = new Vector2(dx,dy);
        }
        public Vector2 getVector() {
            return vector;
        }
    }

    public enum ANIM_STATE {

        IDLE,
        ALERT,
        DYING,
        WALKING,
        SLEEPING,
        INTERACTING,
        SPELLCASTING,
        ATTACKING_MELEE,
        ATTACKING_RANGED
    }

    public enum COLORATION {

        DEFAULT(1f,1f,1f,1f),
        SHADOW(0f,0f,0f, 0.2f),
        WATER_REFLECTION(0.5f,0.5f,0.8f,0.2f);

        private Color coloring;

        COLORATION (float r, float g, float b, float a) {
            this.coloring = new Color(r,g,b,a);
        }

        public Color getColoring() {
            return coloring;
        }
    }
}
