package graphics.culling;

import camera.Cam;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import utils.Counter;

import java.util.ArrayList;

public class Zone {

    private GridPoint2 position;
    private ArrayList<Section> sections;
    private Rectangle boundary;

    public Zone(Rectangle boundary, int row, int col) {
        position = new GridPoint2(col,row);
        sections = new ArrayList<>();
        this.boundary = boundary;
    }

    public void renderCheck () {
        if(Cam.instance.getCullingWindow().overlaps(boundary)) {
            for (Section section : sections) section.renderCheck();
        }
    }

    public void addSection(Section section) { sections.add(section); }

    public Rectangle getBoundary() {return boundary;}

    public GridPoint2 getPosition() {return position;}

}
