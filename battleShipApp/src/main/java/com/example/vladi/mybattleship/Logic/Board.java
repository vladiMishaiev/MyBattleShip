package com.example.vladi.mybattleship.Logic;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vladi on 12/30/2017.
 */

public class Board {
    public enum Direction {HORIZONTHAL, VERTICAl};

    private static final String TAG = "Board";
    private static final int MAX_SIZE_OF_SHIP = 7;
    private static final int MIN_SIZE_OF_SHIP = 1;
    private static int SHIPS_COUNTER = 0;
    private Tile[][] gameBoard;
    private int rows, cols;
    private boolean playerBoard;
    private int numOfShips;
    private Ship[] ships;

    public Board(int rows, int cols, boolean playerBoard, int numOfShips) {
        this.cols = cols;
        this.rows = rows;
        this.playerBoard = playerBoard;
        this.numOfShips = numOfShips;
        gameBoard = new Tile[this.rows][ this.cols];
        ships = new Ship[numOfShips];
        initBoard();
        generateShips();
        placeShips();
    }

    public void initBoard(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                gameBoard[i][j] = new Tile();
            }
        }
    }

    public void generateShips (){
        Random rn = new Random();
        for (int i=0 ;i<numOfShips;i++){
            //int size = rn.nextInt(((MAX_SIZE_OF_SHIP - MIN_SIZE_OF_SHIP) + 1) + MIN_SIZE_OF_SHIP);
            int size = ThreadLocalRandom.current().nextInt(MIN_SIZE_OF_SHIP, MAX_SIZE_OF_SHIP + 1);
            ships[i] = new Ship(++SHIPS_COUNTER,size);
            Log.d(TAG, "generateShips: id: "+SHIPS_COUNTER+" size:"+size);
        }
        Log.d(TAG, "generateShips: ships generated");
    }

    public void placeShips(){
        Random rn = new Random();
        int i=0;
        while (i<numOfShips) {
            //int col = rn.nextInt(cols);
            //int row = rn.nextInt(rows);
            //int choice = rn.nextInt(2);
            int col = ThreadLocalRandom.current().nextInt(0, cols);
            int row = ThreadLocalRandom.current().nextInt(0, rows);
            int choice = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            Log.d(TAG, "placeShips: chosen place: "+row+":"+col);
            boolean res = false;
            if (choice==0) {
                res = isValidPlacement(row, col, ships[i], Direction.HORIZONTHAL);
                if (!res)
                    res = isValidPlacement(row, col, ships[i], Direction.VERTICAl);
            }else{
                res = isValidPlacement(row, col, ships[i], Direction.VERTICAl);
                if (!res)
                    res = isValidPlacement(row, col, ships[i], Direction.HORIZONTHAL);
            }
            if (res)
                i++;
        }

        /*
         mineRows = (int)(mRows * Math.random());
            mineCols = (int)(mCols * Math.random());
         */
    }

    private boolean isValidPlacement(int row, int col, Ship ship,Direction direction){
        int size = ship.getSize()-1;
        if (!gameBoard[row][col].isEmptySlot())
            return false;
        if (row != 0 && !gameBoard[row-1][col].isEmptySlot())
            return false;
        if (row < rows-1 && !gameBoard[row+1][col].isEmptySlot())
            return false;
        if (col != 0 && !gameBoard[row][col-1].isEmptySlot())
            return false;
        if (col < cols-1 && !gameBoard[row][col+1].isEmptySlot())
            return false;


        if (direction == Direction.HORIZONTHAL){
            if (col+size < cols) {
                if (col+size < cols-1 && !gameBoard[row][col+size].isEmptySlot())
                    return false;
                for (int i = col; i <= col + size; i++) {
                    if (!gameBoard[row][i].isEmptySlot())
                        return false;
                }
                //place ship
                Log.d(TAG, "isValidPlacement: -to the rigth- shipSize :"+ship.getSize()+" "+row+":"+col);
                for (int i = col; i <= col + size; i++) {
                    gameBoard[row][i].setShip(ship);
                }
                return true;
            }

            if (col-size >= 0) {
                if (col-size - 1 >= 0 && !gameBoard[row][col-size].isEmptySlot())
                    return false;
                for (int i = col; i >= col - size; i--) {
                    if (!gameBoard[row][i].isEmptySlot())
                        return false;
                }
                Log.d(TAG, "isValidPlacement: -to the left- shipSize :"+ship.getSize()+" "+row+":"+col);
                for (int i = col; i >= col - size; i--) {
                    gameBoard[row][i].setShip(ship);
                }
                return true;
            }
            return false;


        }else{//vertical
            if (row+size < rows) {
                if (row+size < rows-1 && !gameBoard[row+size][col].isEmptySlot())
                    return false;
                for (int i = row; i <= row + size; i++) {
                    if (!gameBoard[i][col].isEmptySlot())
                        return false;
                }
                Log.d(TAG, "isValidPlacement: -to the down- shipSize :"+ship.getSize()+" "+row+":"+col);
                for (int i = row; i <= row + size; i++) {
                    gameBoard[i][col].setShip(ship);
                }
                return true;
            }
            if (row-size >= 0) {
                if (row-size - 1 >= 0 && !gameBoard[row-size][col].isEmptySlot())
                    return false;
                for (int i = row; i >= row - size; i--) {
                    if (!gameBoard[i][col].isEmptySlot())
                        return false;
                }
                Log.d(TAG, "isValidPlacement: -to the up- shipSize :"+ship.getSize()+" "+row+":"+col);
                for (int i = row; i >= row - size; i--) {
                    gameBoard[i][col].setShip(ship);
                }
                return true;
            }
            return false;
        }
    }

    public void clearShipsFromBoard(){

    }
    public boolean hit (int row,int col){
        if (row >= rows || col >=cols || row < 0 || col < 0)
            return false;
        if (gameBoard[row][col].isHit())
            return false;
        gameBoard[row][col].setHit(true);
        if (!gameBoard[row][col].isEmptySlot())
            gameBoard[row][col].getShip().takeHit();
        return true;
    }
    public Tile[][] getGameBoard() {
        return gameBoard;
    }

    public Tile getTile(int row, int col)
    {
        return gameBoard[row][col];
    }

    public void setGameBoard(Tile[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public boolean isPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(boolean playerBoard) {
        this.playerBoard = playerBoard;
    }

    public int getNumOfShips() {
        return numOfShips;
    }

    public void setNumOfShips(int numOfShips) {
        this.numOfShips = numOfShips;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }
}
