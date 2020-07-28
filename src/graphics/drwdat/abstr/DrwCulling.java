package graphics.drwdat.abstr;


import components.map.Section;

public abstract class DrwCulling extends DrwDat{

    private Section section;
    private boolean sectionIsSet;


    public Section getSection() {
        return section;
    }

    public boolean isSectionSet() {
        return sectionIsSet;
    }

    public void setSection(Section section) {
        this.section = section;
        sectionIsSet = true;
    }
}
