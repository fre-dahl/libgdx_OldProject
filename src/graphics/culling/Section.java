package graphics.culling;

import camera.Cam;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import utils.Counter;

import java.util.ArrayList;


public class Section {


    public static int inlist = 0;
    private GridPoint2 position;
    private Rectangle boundary;
    private boolean inView = false;
    private boolean inArray = false;
    private static ArrayList<Section> sectionsInview = new ArrayList<>();

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
