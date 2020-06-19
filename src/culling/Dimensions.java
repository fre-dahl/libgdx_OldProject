package culling;

public class Dimensions {

    // Standard: 400*400 / 16 zones / 16 sections in zone / = 32 Cam checks

    public enum SECTION {

        STANDARD(20),
        BIG(40),
        HUGE(75);

        SECTION(int size) {
            this.size = size;
        }
        private int size;
    }

    public enum ZONE {

        STANDARD(100, SECTION.STANDARD),
        BIG(200, SECTION.BIG),
        HUGE(300, SECTION.HUGE);

        ZONE(int size, SECTION section) {
            this.section = section;
            this.size = size;
        }
        private int size;
        private SECTION section;
    }

    public enum MAP {

        STANDARD(400, ZONE.STANDARD),
        BIG(800, ZONE.BIG),
        HUGE(1200, ZONE.HUGE);

        MAP (int size, ZONE zone) {
            this.zone = zone;
            this.size = size;
        }
        private int size;
        private ZONE zone;

        public int size() {return size;}
        public int zoneSize() {return zone.size;}
        public int sectionSize() {return zone.section.size;}
    }

    public enum TILE {

        SMALL(8),
        MEDIUM(16);

        TILE(int size) {
            this.size = size;
        }

        private int size;

        public int size() {return size;}
    }

    public enum SCALE {

        X2(2),
        X4(4),
        X8(8);

        SCALE(int factor) {
            this.factor = factor;
        }

        private int factor;

        public int factor() { return factor; }
    }
}
