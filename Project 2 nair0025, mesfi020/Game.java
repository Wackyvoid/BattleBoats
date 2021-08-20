//Namita Nair, nair0025, 5461640
//Ruth Mesfin, mesfi020, 5616951

import java.util.Scanner;

public class Game {
    static int missileUse = 1; //to limit missile power use to 1
    static int droneUse = 1; //to limit drone power use to 1


    //helper method to call fire() method in Board class
    public static void attack(Board b) {
        Scanner gameScan = new Scanner(System.in);
        System.out.println("Enter the row you would like to attack: ");
        int rAttack = Integer.parseInt(gameScan.nextLine());
        System.out.println("Enter a column you would like to attack: ");
        int cAttack = Integer.parseInt(gameScan.nextLine());
        System.out.println(b.fire(rAttack, cAttack));
    }

    //helper method to perform each turn in regular mode. prompts user for what action they would like to perform and
    //calls the method necessary to do so. Also checks for user error in entering valid points when necessary.
    public static void playGame(Board newGame, int rows, int columns) {
        Scanner gameScan = new Scanner(System.in);
        System.out.println("What would you like to do? \n 1) Attack \n 2) Missile \n 3) Drone \nEnter 1, 2, or 3");
        int turn =  Integer.parseInt(gameScan.nextLine());

        if (turn == 1) { //calls attack helper function
            attack(newGame);

        } else if (turn == 2) { //missile
            if (missileUse == 1) {
                System.out.println("Where would you like to launch your missile?");
                int xPos = -1;
                while (xPos < 0 || xPos >= rows) {
                    System.out.println("What X position?");
                    xPos = Integer.parseInt(gameScan.nextLine());
                    if (xPos < 0 || xPos >= rows) {
                        System.out.println("Invalid input. Enter a number that is less than " + rows);
                    }
                }
                int yPos = -1;
                while (yPos < 0 || yPos >= columns) {
                    System.out.println("What Y position?");
                    yPos = Integer.parseInt(gameScan.nextLine());
                    if (yPos < 0 || yPos >= columns) {
                        System.out.println("Invalid input. Enter a number that is less than " + columns);
                    }
                }
                newGame.missile(xPos,yPos);
                missileUse --;
            } else {
                System.out.println("Already used missile power. Penalty");
            }


        } else if (turn == 3) { //drone
            if (droneUse == 1) {
                int droneInput = 0;
                while (droneInput != 1 && droneInput != 2) {
                    System.out.println("Would you like to scan a row or a column? [1 for row, 2 for col]");
                    droneInput = Integer.parseInt(gameScan.nextLine());
                    if (droneInput != 1 && droneInput != 2) {
                        System.out.println("Invalid input. Type 1 for row or 2 for column.");
                    }
                }
                int droneIndex = -1;
                if (droneInput == 1) {
                    while (droneIndex < 0 || droneIndex >= rows) {
                        System.out.println("Which row would you like to scan?");
                        droneIndex = Integer.parseInt(gameScan.nextLine());
                        if (droneIndex < 0 || droneIndex >= rows) {
                            System.out.println("Invalid input. Enter a number that is less than " + rows);
                        }
                    }
                } else if (droneInput == 2){
                    while (droneIndex < 0 || droneIndex >= columns) {
                        System.out.println("Which column would you like to scan?");
                        droneIndex = Integer.parseInt(gameScan.nextLine());
                        if (droneIndex < 0 || droneIndex >= columns) {
                            System.out.println("Invalid input. Enter a number that is less than " + columns);
                        }
                    }
                }
                System.out.println(newGame.drone(droneInput, droneIndex));
                droneUse++;
            } else {
                System.out.println("Already used drone power. Penalty");
            }

        } else { //invalid input for turn
            System.out.println("Invalid input. Enter 1, 2, or 3");
        }
    }


    //this method runs after every turn to check and see if every boat has been sunk; if so, the game will end
    public static boolean checkBoats() {
        for (int i = 0; i < Board.boardArray.length; i++) {
            for (int j = 0; j < Board.boardArray[0].length; j++) {
                if (Board.boardArray[i][j].getStatus() == 'B') {
                    return false;
                }
            }
        }
        return true;
    }

    //main method where game is created and scanner is initialized to collect user inputs. Calls helper methods in Game class
    //Asks user if they want to play in debug or regular mode. Prompts user for board size and makes sure valid points are entered.
    //then runs a while loop that runs until all boats have been sunk and the game ends.
    public static void main(String[] args){
        Scanner gameScan = new Scanner(System.in);
        System.out.println("Welcome to Battleboats!!");
        System.out.println("What mode would you like to play in? \n 1) Standard \n 2) Debug \nEnter 1 or 2");
        int modeInput = 0;
        while (modeInput != 1 && modeInput != 2) {
            modeInput = Integer.parseInt(gameScan.nextLine());
            if (modeInput != 1 && modeInput != 2) {
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        }

        System.out.println("Pick the size of your board!");
        System.out.println("What width board would you like? [min 3 max 10]");
        int columns = 0;
        while (columns > 10 || columns < 3) {
            columns = Integer.parseInt(gameScan.nextLine());
            if (columns < 3 || columns > 10) {
                System.out.println("Invalid input; enter a number between 3 and 10.");
            }
        }
        int rows = 0;
        while (rows > 10 || rows < 3) {
            System.out.println("What height board would you like? [min 3 max 10]");
            rows = Integer.parseInt(gameScan.nextLine());
            if (rows < 3 || rows > 10) {
                System.out.println("Invalid input; enter a number between 3 and 10.");
            }
        }

        Board newGame = new Board(rows, columns);
        newGame.placeBoats();

        System.out.println("\n");
        System.out.println("The game has now begun! Sink all the ships in as few turns as possible.");
        System.out.println("You can attack, use a drone, or a missile. Missile and drone can each only be used once.");
        System.out.println("\n");
        boolean allBoatsSunk = false;
        if (modeInput == 1) {

            while (!allBoatsSunk) {
                allBoatsSunk = checkBoats();
                newGame.display();
                System.out.println("\n");
                playGame(newGame, rows, columns);
                allBoatsSunk = checkBoats();
            }

        } else {

            while (!allBoatsSunk) {
                System.out.println("DEBUG DISPLAY:");
                newGame.print();
                System.out.println("\n");
                newGame.display();
                playGame(newGame, rows, columns);
                allBoatsSunk = checkBoats();
            }

        }

        //end of game display; prints board and tells user total turns and shots taken.
        newGame.display();
        System.out.println("\n");
        System.out.println("Congratulations!! You have won Battleboats.");
        System.out.println("Total turns taken: " + newGame.getTurns());
        System.out.println("Total shots taken: " + newGame.getTotalShots());
    }
}

