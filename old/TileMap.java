package old;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import components.BaseTile;
import main.Settings;
import java.util.ArrayList;

public class TileMap implements Disposable {

    public int rows, cols;
    public ArrayList<ArrayList<BaseTile>> tiles;

    public  TileMap(int rows, int cols) {
        tiles = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void dispose() {
        for (ArrayList<BaseTile> row : tiles) {
            for (BaseTile tile : row) {
                tile.dispose();
            }
        }
    }

    public Vector2 getCentre() {
        Vector2 centre;
        float width =  Settings.SCALE * Settings.TILE_SIZE * cols;
        float height = Settings.SCALE * Settings.TILE_SIZE * rows;
        centre = new Vector2(width/2, height/2);
        return centre;
    }
}
