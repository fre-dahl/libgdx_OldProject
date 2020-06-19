package tilecoding.noiceGenerators;


import java.util.Random;

public class NoiseGenerator {

    /* TODO: 19/12/2019 Utvid klassen med konstruktor og instans avhengige metoder for;
        Massasje
        linke N/E/S/W for kart
            gjør ytterkantene avhengig av motstående ytterkant (reach_around())
        Hente matrisen etter hver iterasjon
        Fjerne kanter
     */

    // 0.5 5 3
    // 0.4 4 3


    int birthLimit;                         // Number of neighbors to spawn
    int deathLimit;                         // Number of neighbors to stay alive
    private float iniChance;                // 0.0 -> 1.0
    private int evolutions;                 // Number of states for evolvedState
    private int iterations;                 // Cycles
    private int columns;                    // Columns
    private int rows;                       // Rows
    private Random rnd;                     // RND
    boolean[][] state;                      // Map [rows][columns]
    boolean[][] imprint;                    // Imprint
    int [][] evolvedState;                  // evolved state 0,1,2,3
    private boolean has_state = false;      // if state set
    private boolean has_imprint = false;    // if imprint set
    private boolean has_evolvedState = false;  // if quadState is set
    long seed = 999_999_999;

    public NoiseGenerator() {

        super();
        rows = 0;
        columns = 0;
        iniChance = 0f;
        iterations = 0;
        birthLimit = 9; // = No change
        deathLimit = 0; // = No change
    }

    public NoiseGenerator(int rows, int columns) {

        state = new boolean[rows][columns];
        has_state = true;
        this.columns = columns;
        this.rows = rows;
        iterations = 0;
        birthLimit = 9; // = No change
        deathLimit = 0; // = No change
    }

    public NoiseGenerator(float iniChance, int rows, int columns) {

        state = new boolean[rows][columns];
        has_state = true;
        if (iniChance > 1.0) this.iniChance = 1.0f;
        else this.iniChance = iniChance;
        this.columns = columns;
        this.rows = rows;
        iterations = 0;
        birthLimit = 9; // = No change
        deathLimit = 0; // = No change

        initialize();
    }

    public void setEvolvedState() {

        if (has_state){
            if (has_evolvedState) {
                for (int r = 0; r < this.rows; r++) {
                    for (int c = 0; c < this.columns; c++) {
                        evolvedState[r][c] = 0;
                    }
                }
            }
            else evolvedState = new int[rows][columns];
            int[] rows = {1,0,-1};
            int[] cols = {-1,0,1};
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.columns; c++) {
                    if (state[r][c]){
                        evolvedState[r][c] += 2;
                    } else evolvedState[r][c]++;
                    int number_of_populated = 0;
                    for(int i: rows) {
                        for (int j : cols) {
                            if (r + i < 0 || r + i > this.rows - 1 || c + j < 0 || c + j > this.columns - 1) {
                                number_of_populated++; // outside of grid counts as populated
                            }
                            else {
                                if (state[r + i][c + j]){
                                    number_of_populated++;
                                }
                            }
                        }
                    }
                    if (number_of_populated == 9) {
                        evolvedState[r][c]++;
                        if (r>10 && r<20 && c>10 && c<20)
                        System.out.println("row: "+ r + "col: " + c);
                    }
                    if (number_of_populated == 0) {
                        evolvedState[r][c]--;
                    }
                }
            }
            has_evolvedState = true;
            evolutions = 4; // 0,1,2,3
        }
        else System.out.println("No state to generate quadState");
    }

    /*
    public void setImprint(IMPRINT type) {
        if (has_state){
            imprint = new boolean[rows][columns];
            switch (type) {
                case MOUND:
                    int centerRow = rows/2;
                    int centerCol = columns/2;
                    int radius = Math.min(centerRow, centerCol) / 6;
                    for (int row = 0; row < this.rows; row++) {
                        for (int col = 0; col < this.columns; col++) {
                            // Formula for a disc in karthesian cordinates
                            imprint[row][col] = (Math.pow((col - centerCol), 2) + Math.pow((row - centerRow), 2)) < Math.pow(radius, 2);
                        }
                    }
                    break;
                case RIVER:
                    imprint = run_without_instance(30,4,3,20,rows,columns);
                    break;
                case RECTANGLE:
                    break;
                default:
                    break;
            }
            has_imprint = true;
        }
        else System.out.println("Generator need a state before it can create an imprint");
    }

     */

    public void create (float iniChance, int rows, int columns) {

        state = new boolean[rows][columns];
        has_state = true;
        if (iniChance > 1.0) this.iniChance = 1.0f;
        else this.iniChance = iniChance;
        this.columns = columns;
        this.rows = rows;

        initialize();
    }

    public void create (float iniChance) {

        if (has_state) {
            if (iniChance > 1.0) this.iniChance = 1.0f;
            else this.iniChance = iniChance;
            initialize();
        } else {
            System.out.println( "State has to be set: Use the alternative create() function OR the set_state() function");
        }
    }

    public void set_state (int rows, int columns) {

        if (!(has_state)) {
            state = new boolean[rows][columns];
            has_state = true;
        } else {
            System.out.println("State already set");
        }

    }

    private void initialize() {

        rnd = new Random();
        //rnd.setSeed(seed);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (r < rows/3 && r > rows/6) {
                    state [r][c] = rnd.nextInt(101) < (iniChance/(2)) * 100;
                }
                else state [r][c] = rnd.nextInt(101) < iniChance * 100;
            }
        }
    }

    public void iterate(int iterations) {

        if (has_state) {
            if (iterations > 0) {
                int[] rows = {1,0,-1};
                int[] cols = {-1,0,1};
                for (int cycle = 0; cycle < iterations; cycle++) {
                    for (int r = 0; r < this.rows; r++) {
                        for (int c = 0; c < this.columns; c++) {
                            int naboer = 0;
                            for(int i: rows) {
                                for (int j : cols) {
                                    if ( !((i == 0) && (j == 0)) ) {
                                        if (r + i < 0 || r + i > this.rows - 1 || c + j < 0 || c + j > this.columns - 1){
                                            naboer++; // utsiden av kartet er populated
                                        }
                                        else {
                                            if (state[r + i][c + j]){
                                                naboer++;
                                            }
                                        }
                                    }
                                }
                            }
                            // TODO: 03/02/2020 Implement imprint here
                            int birthlim_mod = 0;
                            if (has_imprint) {
                                if (imprint[r][c]) birthlim_mod -= 1;
                            }

                            if (naboer > birthLimit + birthlim_mod) state[r][c] = true;
                            else if (naboer < deathLimit) state[r][c] = false;
                            else state[r][c] = state[r][c];
                        }
                    }
                    this.iterations++;
                }
            }
        }
        else System.out.println("Create a map before iteration: Use another constructor OR call the create() function");
    }

    public static boolean[][] run_without_instance(int iniChance, int birthLimit, int deathLimit, int iterations, int width, int height) {

        int[] rows = {1,0,-1};
        int[] cols = {-1,0,1};
        Random rnd = new Random();
        boolean[][] map = new boolean[width][height];

        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                map [r][c] = rnd.nextInt(101) < iniChance;
            }
        }
        // antall simulations av game of life
        for (int cycle = 0; cycle < iterations; cycle++) {
            // går igjennom kartet
            for (int r = 0; r < width; r++) {
                for (int c = 0; c < height; c++) {
                    int naboer = 0; // reset naboer
                    //ser på naboene til hver enkelt tile
                    for(int i: rows){
                        for(int j: cols){// Så lenge vi ikke er på center tilen
                            if ( !((i == 0) && (j ==0)) ) {
                                // for out of bounds
                                if (r + i < 0 || r + i > width - 1 || c + j < 0 || c + j > height - 1){
                                    naboer++; // utsiden av kartet er populated
                                }
                                else {
                                    // skjekk for nabo og ++
                                    if (map[r + i][c + j]){
                                        naboer++;
                                    }
                                }
                            }
                        }
                    }
                    // Utfall
                    if (naboer > birthLimit) map[r][c] = true;
                    else if (naboer < deathLimit) map[r][c] = false;
                    else map[r][c] = map[r][c];
                }
            }
        }
        return map;
    }

    public boolean has_state() {
        return has_state;
    }

    public int getIterations() {
        return iterations;
    }

    public float getIniChance() {
        return iniChance;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
