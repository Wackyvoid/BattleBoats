//Namita Nair, nair0025, 5461640
//Ruth Mesfin, mesfi020, 5616951

public class Cell {
    int row; // x position
    int col; // y position
    char status;
    int boatNumber; //to identify boats in debug mode

    public Cell(int row, int col) {
        this.status = '-';
        this.row = row;
        this.col = col;
        this.boatNumber = 0;
    }

    public char getStatus() {
        return status;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }


    //used in print() debug mode
    public int getBoatNumber() {
        return boatNumber;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setBoatNumber(int boatNumber) {
        this.boatNumber = boatNumber;
    }

    public void setStatus(char status) {
        this.status = status;
    }
        //status list:
        //'-' no guess no boat
        //'B' no guess, yes boat
        //'H' yes guess, yes boat
        //'M' yes guess, no boat
}
