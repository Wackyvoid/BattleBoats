//Namita Nair, nair0025, 5461640
//Ruth Mesfin, mesfi020, 5616951

import java.lang.Math;

public class Boat {
    int size;
    boolean orientation; //true = horizontal, false = vertical;
    Cell[] location;
    int number; //to identify boats in debug mode method print()


    //creates boat objects based on board size and orientation. also initializes cell[] location array
    //and randomly places boat, checking to make sure it fits on the board and is not overlapping with any other boat.
    public Boat(int size, boolean orientation, int number) {
        this.size = size;
        this.orientation = orientation;
        this.location = new Cell[size];
        this.number = number;

        int xPos = 0;
        int yPos = 0;
        boolean check = false;

        //while loop randomizes coordinates until they all fit on the board and check == true
        while (!check) {
            if (orientation) {
                xPos = (int)(Math.floor((Math.random() * (Board.boardArray.length - size))));
                yPos = (int)(Math.floor((Math.random() * (Board.boardArray[0].length))));
                check = true;
                for (int i = 0; i < size; i++) {
                    if (Board.boardArray[xPos + i][yPos].getStatus() != '-') {
                        check = false;
                        break;
                    }
                }
            } else {
                xPos = (int)(Math.floor((Math.random() * (Board.boardArray.length))));
                yPos = (int)(Math.floor((Math.random() * (Board.boardArray[0].length - size))));
                check = true;
                for (int i = 0; i < size; i++) {
                    if (Board.boardArray[xPos][yPos + i].getStatus() != '-') {
                        check = false;
                        break;
                    }
                }
            }
        }

        //for loop to populate valid location with cells and then change the corresponding
        //board cells to status B, based on orientation
        if (orientation) {
            for (int k = 0; k < size; k++) {
                location[k] = Board.boardArray[xPos + k][yPos];
                Board.boardArray[xPos + k][yPos].setStatus('B');
                Board.boardArray[xPos + k][yPos].setBoatNumber(number);
            }
        } else {
            for (int k = 0; k < size; k++) {
                location[k] = Board.boardArray[xPos][yPos + k];
                Board.boardArray[xPos][yPos + k].setStatus('B');
                Board.boardArray[xPos][yPos + k].setBoatNumber(number);
            }
        }

    }

    public int getSize(){
        return size;
    }

    public boolean getsOrientation(){
        return orientation;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

}
