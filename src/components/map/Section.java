package components.map;

import camera.Cam;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;


public class Section {

    private GridPoint2 position;
    private Rectangle boundary;
    private boolean inView = false;
    private static Array<Section> sectionsInview = new Array<>();

    public Section(Rectangle boundary, int row, int col) {
        position = new GridPoint2(col,row);
        this.boundary = boundary;
    }

    public void renderCheck () {

        inView = Cam.instance.getCullingWindow().overlaps(boundary);
        if(inView) {
            sectionsInview.add(this);
        }
    }

    public static void resetSections() {
        for (Section section : sectionsInview) {
            section.inView = false;
        }
        sectionsInview.clear();
    }

    public boolean currentlyInView() { return inView; }

    public Rectangle getBoundary() {return boundary;}

    public GridPoint2 getPosition() {return position;}
}
