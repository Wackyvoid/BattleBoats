//Namita Nair, nair0025, 5461640
//Ruth Mesfin, mesfi020, 5616951

import java.lang.Math;

public class Board {
    static Cell[][] boardArray; //static variable that represents the game board through all classes
    Boat[] boatArray; //array containing all boats on board
    int totalShots;
    int turns;


    //constructor that initializes a game board based on the user input of board size
    public Board(int rows, int cols) {
        boardArray = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardArray[i][j] = new Cell(i, j);
            }
        }
    }

    public int getTotalShots() {
        return totalShots;
    }

    public int getTurns() {
        return turns;
    }


    //method that initializes and creates boat objects according to board size; randomizes orientation using Math.random()
    public void placeBoats() {
        int width = boardArray.length;
        int height = boardArray[0].length;

        if (width == 3 || height == 3) { //2
            boatArray = new Boat[1];
            Boat boat1 = new Boat(2, Math.random() <= 0.5, 1);
            boatArray[0] = boat1;

        } else if (width == 4 || height == 4) { //2,3
            boatArray = new Boat[2];
            Boat boat1 = new Boat(2, Math.random() <= 0.5, 1);
            boatArray[0] = boat1;
            Boat boat2 = new Boat(3, Math.random() <= 0.5, 2);
            boatArray[1] = boat2;

        } else if (width == 5 || width == 6 || height == 5 || height == 6) { //2, 3, 3
            boatArray = new Boat[3];
            Boat boat1 = new Boat(2, Math.random() <= 0.5, 1);
            boatArray[0] = boat1;
            Boat boat2 = new Boat(3, Math.random() <= 0.5, 2);
            boatArray[1] = boat2;
            Boat boat3 = new Boat(3, Math.random() <= 0.5, 3);
            boatArray[2] = boat3;

        } else if (width == 7 || width == 8 || height == 7 || height == 8) { //2, 3, 3, 4
            boatArray = new Boat[4];
            Boat boat1 = new Boat(2, Math.random() <= 0.5, 1);
            boatArray[0] = boat1;
            Boat boat2 = new Boat(3, Math.random() <= 0.5, 2);
            boatArray[1] = boat2;
            Boat boat3 = new Boat(3, Math.random() <= 0.5, 3);
            boatArray[2] = boat3;
            Boat boat4 = new Boat(4, Math.random() <= 0.5, 4);
            boatArray[3] = boat4;


        } else if (width == 9 || width == 10 || height == 9 || height == 10) { //2, 3, 3, 4, 5
            boatArray = new Boat[5];
            Boat boat1 = new Boat(2, Math.random() <= 0.5, 1);
            boatArray[0] = boat1;
            Boat boat2 = new Boat(3, Math.random() <= 0.5, 2);
            boatArray[1] = boat2;
            Boat boat3 = new Boat(3, Math.random() <= 0.5, 3);
            boatArray[2] = boat3;
            Boat boat4 = new Boat(4, Math.random() <= 0.5, 4);
            boatArray[3] = boat4;
            Boat boat5 = new Boat(5, Math.random() <= 0.5, 5);
            boatArray[4] = boat5;

        }

    }


    //method for attacking on a single coordinate. returns hit, miss, sunk, or penalty. Calls checkIfSunk() function when
    //a boat is hit to see if a boat has been sunk or not. Penalty if user input has already been guessed or is out of bounds.
    public String fire(int x, int y) {
        turns += 1;
        totalShots += 1;
        System.out.println("\n");
        if (x >= boardArray.length || y >= boardArray[0].length) {
            return "penalty, turn skipped \n";
        } else if (boardArray[x][y].getStatus() == '-') {
            boardArray[x][y].setStatus('M');
            return "miss \n";
        } else if (boardArray[x][y].getStatus() == 'B') {
            boardArray[x][y].setStatus('H');
            if (checkIfSunk(x, y)) {
                return "sunk \n";
            } else {
                return "hit \n";
            }
        } else {
            return "penalty, turn skipped + \n";
        }

    }


    //function that loops through boats and finds the one that was hit in order to see if all locations of the boat have
    //been hit; if so, returns true and fire() method will return sunk instead of hit.
    public boolean checkIfSunk(int x, int y) {
        boolean checkSunk = true;
        for (Boat boat: boatArray) {
            for (int i = 0; i < boat.getSize(); i++) {
                if (boat.location[i].equals(boardArray[x][y])) {
                    for (Cell cell: boat.location) {
                        if (cell.getStatus() == 'B') {
                            checkSunk = false;
                            break;
                        }
                    }
                }
            }
        }
        return checkSunk;
    }

    //displays the board before each turn. Hides location of boats, only shows hits and misses
    public void display() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                if (boardArray[i][j].getStatus() == 'B') {
                    System.out.print('-' + " ");
                } else {
                    System.out.print(boardArray[i][j].getStatus() + " ");
                }
            }
            System.out.println();
        }
    }


    //for debug mode: prints out the revealed board. Each seperate boat is denoted by its number (i.e. 11, 222, 333, etc)
    //also shows when parts of the boat have been hit.
    public void print() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                if (boardArray[i][j].getBoatNumber() != 0) {
                    if (boardArray[i][j].getStatus() == 'B') {
                        System.out.print(boardArray[i][j].getBoatNumber() + " ");
                    } else {
                        System.out.print(boardArray[i][j].getStatus() + " ");
                    }

                } else {
                    System.out.print('-' + " ");
                }
            }
            System.out.println();
        }
    }

    //missile power method. cycles through each corrdinate in 3x3 square with center picked by user and checks to see
    //if it is on the board, and if so hits those locations.
    public void missile(int x, int y) {
        turns += 1;

        if (boardArray[x][y].getStatus() == '-') {
            boardArray[x][y].setStatus('M');
        } else if (boardArray[x][y].getStatus() == 'B') {
            boardArray[x][y].setStatus('H');
        }

        if (x-1 >= 0 && x-1 < boardArray.length && y+1 < boardArray[0].length){
            if (boardArray[x-1][y+1].getStatus() == '-') {
                boardArray[x-1][y+1].setStatus('M');
            } else if (boardArray[x-1][y+1].getStatus() == 'B') {
                boardArray[x - 1][y + 1].setStatus('H');
            }
        }
        if (y+1 < boardArray[0].length){
            if (boardArray[x][y+1].getStatus() == '-') {
                boardArray[x][y+1].setStatus('M');
            } else if (boardArray[x][y+1].getStatus() == 'B') {
                boardArray[x][y+1].setStatus('H');
            }
        }
        if (x+1 < boardArray.length && y+1 < boardArray[0].length){
            if (boardArray[x+1][y+1].getStatus() == '-') {
                boardArray[x+1][y+1].setStatus('M');
            } else if (boardArray[x+1][y+1].getStatus() == 'B') {
                boardArray[x+1][y+1].setStatus('H');
            }
        }
        if (x-1 >= 0 && x-1 < boardArray.length){
            if (boardArray[x-1][y].getStatus() == '-') {
                boardArray[x-1][y].setStatus('M');
            } else if (boardArray[x-1][y].getStatus() == 'B') {
                boardArray[x-1][y].setStatus('H');
            }
        }
        if (x+1 < boardArray.length){
            if (boardArray[x+1][y].getStatus() == '-') {
                boardArray[x+1][y].setStatus('M');
            } else if (boardArray[x+1][y].getStatus() == 'B') {
                boardArray[x+1][y].setStatus('H');
            }
        }
        if (x-1 >= 0 && x-1 < boardArray.length && y-1 >= 0 && y-1 < boardArray[0].length){
            if (boardArray[x-1][y-1].getStatus() == '-') {
                boardArray[x-1][y-1].setStatus('M');
            } else if (boardArray[x-1][y-1].getStatus() == 'B') {
                boardArray[x-1][y-1].setStatus('H');
            }
        }
        if (y-1 >= 0 && y-1 < boardArray[0].length){
            if (boardArray[x][y-1].getStatus() == '-') {
                boardArray[x][y-1].setStatus('M');
            } else if (boardArray[x][y-1].getStatus() == 'B') {
                boardArray[x][y-1].setStatus('H');
            }
        }
        if (x+1 < boardArray.length && y-1 >= 0 && y-1 < boardArray[0].length){
            if (boardArray[x+1][y-1].getStatus() == '-') {
                boardArray[x+1][y-1].setStatus('M');
            } else if (boardArray[x+1][y-1].getStatus() == 'B') {
                boardArray[x+1][y-1].setStatus('H');
            }
        }
        turns += 1;
    }


    //drone power method. returns number of boat locations (hit or not) wihtin a row or column depending on the user's choice.
    public String drone(int direction, int index) {
        int numSpots = 0;

        if (direction == 1) {
            for (int i = 0; i < boardArray.length - 1; i++) {
                if (boardArray[index][i].getStatus() == 'B' || boardArray[index][i].getStatus() == 'H') {
                    numSpots++;
                }
            }
        } else {
            for (int i = 0; i < boardArray.length; i++) {
                if (boardArray[i][index].getStatus() == 'B' || boardArray[i][index].getStatus() == 'H') {
                    numSpots++;
                }
            }
        }
        turns += 1;
        return ("Drone has scanned " + numSpots + " targets in the specified area.");
    }

}
