//game_of_life.java = Conway's Game of Life
class GameOfLife {
    public static void main(String[] args) throws java.io.IOException
    {
        //read in the size of the grid and create arrays
        int size = 10; // fix at 10 for testing
        boolean[][] currentGeneration, nextGeneration;
        currentGeneration = new boolean[size][size];
        nextGeneration = new boolean[size][size];
        readInitialGeneration(currentGeneration);

        // read in the number of generations to simulate
        int cycles = 4; // fix at 4 for testing
        printState(currentGeneration);
        for (int i = 0; i < cycles; i++) {
            System.out.println("Cycle = " + i + "\n\n");
            advanceOneGen(currentGeneration, nextGeneration);
            printState(nextGeneration);
            // swap current and next generations
            boolean[][] temp = nextGeneration;
            nextGeneration = currentGeneration;
            currentGeneration = temp;
        }
    }

    // read the initial generation from the input
    // a dot means empty and an X means alive
    // any other characters are ignored
    // the border cells are all set to empty
    // the method assumes the array is square
    static void readInitialGeneration(boolean[][] w) throws java.io.IOException
    {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                char c = (char)System.in.read();

                //skip illegal characters
                while (c != '.' && c != 'X')
                    c = (char)System.in.read();
                if (c == '.')
                    w[i][j] = EMPTY;
                else
                    w[i][j] = ALIVE;
            }
        }
        //set border cells to be empty
        int border = w.length - 1;
        for (int i = 0; i < w.length; i++) {
            w[i][0] = w[0][i] = EMPTY;
            w[i][border] = w[border][i] = EMPTY;
        }
    }

    // print a generation to the console
    static void printState(boolean[][] w) {
        for (int i = 0; i < w.length; i++) {
            System.out.println();
            for (int j = 0; j < w[i].length; j++)
                if (w[i][j] == ALIVE)
                    System.out.print('X');
                else
                    System.out.print('.');
        }
        System.out.println();
    }

    // compute the number of alive neighbors of a cell
    static int neighbors(int row, int column, boolean[][] w)
    {
        int neighborCount = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (w[row + i][column + j] == ALIVE)
                    neighborCount = neighborCount + 1;
        if (w[row][column] == ALIVE)
            neighborCount--;
        return neighborCount;
    }

    static void advanceOneGen(boolean[][] wOld, boolean[][] wNew)
    {
        int neighborCount;
        for (int i = 1; i < wOld.length - 1; i++) {
            for (int j = 1; j < wOld[i].length - 1; j++) {
                neighborCount = neighbors(i, j, wOld);
                if (neighborCount == 3)
                    wNew[i][j] = ALIVE;
                else if (wOld[i][j] == ALIVE && neighborCount == 2)
                    wNew[i][j] = ALIVE;
                else
                    wNew[i][j] = EMPTY;
            }
        }
    }

    static final boolean ALIVE = true;
    static final boolean EMPTY = false;
} 
