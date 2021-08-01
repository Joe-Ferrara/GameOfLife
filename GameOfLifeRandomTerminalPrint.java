//GameOfLifeRandomTerminalPrint.java
//Conway's Game of Life
//Set the board size, number of alive cells, and number of rounds
//The alive cells are randomly assigned and then the rounds are printed in the
//terminal.

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class GameOfLifeRandomTerminalPrint {
    public static void main(String[] args) throws java.io.IOException
    {
        //read in the size of the grid and create arrays
        int size, aliveCnt;
        boolean playRandom;
        boolean[][] currentGeneration, nextGeneration;
        Scanner scan = new Scanner(System.in);
        size = getSizeFromUser(scan);
        printRandomInfo();
        aliveCnt = getAliveCntFromUser(scan, size);
        currentGeneration = new boolean[size][size];
        nextGeneration = new boolean[size][size];
        makeRandomGrid(currentGeneration, aliveCnt);
        // read in the number of generations to simulate
        int cycles;
        cycles = askNumberRounds(scan);
        System.out.println();
        System.out.println("Round 1: ");
        printState(currentGeneration);
        for (int i = 2; i <= cycles; i++) {
            System.out.println();
            System.out.println("Round " + i + ":");
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
        System.out.println("This is your grid:");
        printState(w);
        System.out.println("An X is an alive cell and a . is a dead cell.");
        System.out.println("The cells on the border will be set to dead on");
        System.out.println("the first round.");
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

    static void printRandomInfo() {
        System.out.println("This is Game of Life in random mode,");
        System.out.println("which means that the initial live cells are");
        System.out.println("chosen at random!");
    }

    static int getSizeFromUser(Scanner scan) {
        int gridSize, aliveCnt;
        System.out.println("Welcome to Game of Life!");
        System.out.println("What size grid will you be playing with?");
        System.out.println("(type an integer and then press enter)");
        gridSize = scan.nextInt();
        return gridSize;
    }

    static int askNumberRounds(Scanner scan) {
        System.out.println("How many rounds do you want to play?");
        System.out.println("(type an integer and then press enter)");
        boolean done = false;
        int r = 0;
        while (! done) {
            if (scan.hasNextInt()) {
                r = scan.nextInt();
                done = true;
            }
            else {
                System.out.println("You did not enter an integer! Try again.");
            }
        }
        return r;
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
            for (int j = 0; j < w[i].length; j++)
                if (w[i][j] == ALIVE)
                    System.out.print('X');
                else
                    System.out.print('.');
            System.out.println();
        }
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
