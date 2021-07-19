//game_of_life.java = Conway's Game of Life

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class GameOfLife {
    public static void main(String[] args) throws java.io.IOException
    {
        //read in the size of the grid and create arrays
        int size, aliveCnt;
        boolean playRandom;
        boolean[][] currentGeneration, nextGeneration;
        Scanner scan = new Scanner(System.in);
        size = getSizeFromUser(scan);
        aliveCnt = getAliveCntFromUser(scan, size);
        playRandom = askPlayType(scan);
        if (playRandom) {
            currentGeneration = new boolean[size][size];
            nextGeneration = new boolean[size][size];
            makeRandomGrid(currentGeneration, aliveCnt);
        }
        else {
            currentGeneration = new boolean[size][size];
            nextGeneration = new boolean[size][size];
            readInitialGeneration(currentGeneration);
        }
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

    static void makeRandomGrid(boolean[][] w, int aliveCnt) {
        int m = w.length;
        int n = w[0].length;
        // set all cells empty
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = EMPTY;
            }
        }
        // pick aliveCnt random cells to set empty
        ArrayList<Integer> slots = new ArrayList<Integer>();
        for (int i = 0; i < m * n; i++) {
            slots.add(i);
        }
        Collections.shuffle(slots);
        int row, col, idx;
        for (int i = 0; i < aliveCnt; i++) {
            idx = slots.get(i);
            row = idx / m;
            col = idx % n;
            w[row][col] = ALIVE;
        }
        System.out.println("This is your grid.");
        printState(w);
        //set border cells to be empty
        int border = w.length - 1;
        for (int i = 0; i < w.length; i++) {
            w[i][0] = w[0][i] = EMPTY;
            w[i][border] = w[border][i] = EMPTY;
        }
    }

    static int getAliveCntFromUser(Scanner scan, int gridSize) {
        boolean done = false;
        int aliveCnt = 0;
        while (! done) {
            System.out.println("How many cells will start off alive?");
            System.out.println("(type an integer and then press enter)");
            aliveCnt = scan.nextInt();
            int t = gridSize * gridSize + 1;
            if (aliveCnt < t) done = true;
            else {
                System.out.println("You did not enter a valid number.");
                System.out.printf("It must be less than %d.", t);
            }
        }
        return aliveCnt;
    }

    static int getSizeFromUser(Scanner scan) {
        int gridSize, aliveCnt;
        System.out.println("Welcome to Game of Life!");
        System.out.println("What size grid will you be playing with?");
        System.out.println("(type an integer and then press enter)");
        gridSize = scan.nextInt();
        return gridSize;
    }

    static boolean askPlayType(Scanner scan) {
        System.out.println("You can play in random mode or assign mode.");
        System.out.println("In random mode the initial live cells are chosen");
        System.out.println("at random.");
        System.out.println("In assign mode you assign the initial live cells");
        System.out.println("via coordinate ordered pairs, where (0,0) is the");
        System.out.println("bottom left corner, the first coordinate moves");
        System.out.println("horizontally and the second coordinate moves");
        System.out.println("vertically.");
        boolean done = false;
        char c = 'a';
        while (! done) {
            System.out.println("Do you want to play in random mode? (y/n)");
            c = scan.next().charAt(0);
            if (c == 'y' || c == 'n') done = true;
            else System.out.println("You did not give a valid response.");
        }
        if (c == 'y') return true;
        else return false;
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

    static int randomInteger(int l, int u) {
        return l + (int)(Math.random() * ((u - l) + 1));
    }

    static final boolean ALIVE = true;
    static final boolean EMPTY = false;
} 
