package culling;

import camera.Cam;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;


public class Section {


    private GridPoint2 position;
    private Rectangle boundary;
    private boolean inView;
    private static ArrayList<Section> sectionsInview = new ArrayList<>();

    public Section(Rectangle boundary, int row, int col) {
        position = new GridPoint2(col,row);
        this.boundary = boundary;
    }

    public void renderCheck () {
        if(Cam.instance.getCullingWindow().overlaps(boundary)) {
            sectionsInview.add(this);
            inView = true;
        }
    }

    private void select() {
        System.out.println("Selected Section: " + position);
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
