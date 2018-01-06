package com.example.vladi.mybattleship.Logic;
/**
 * Created by vladi on 12/30/2017.
 */
import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Game {
	public static final int EASY_SIZE_COL = 12;
	public static final int EASY_SIZE_ROW = 6;
	public static final int EASY_NUM_OF_SHIPS = 4;
	public static final int MEDIUM_SIZE_COL = 12;
	public static final int MEDIUM_SIZE_ROW = 9;
	public static final int MEDIUM_NUM_OF_SHIPS = 5;
	public static final int HARD_SIZE_COL = 12;
	public static final int HARD_SIZE_ROW = 15;
	public static final int HARD_NUM_OF_SHIPS = 7;
	private static final String TAG = "Game";
	private Board playerBoard;
	private Board computerBoard;
	private int playerShipsLeft;
	private int computerShipsLeft;
	private int moves;

	public boolean isPlayerTurn() {
		return playerTurn;
	}

	private boolean playerTurn;
	public Game(String difficulty)
	{
		playerTurn=true;
		this.moves =0;
		switch(difficulty)
		{
			case "Beginner": {
				playerBoard = new Board(EASY_SIZE_ROW,EASY_SIZE_COL,true,EASY_NUM_OF_SHIPS);
				computerBoard = new Board(EASY_SIZE_ROW,EASY_SIZE_COL,false,EASY_NUM_OF_SHIPS);
				playerShipsLeft =  EASY_NUM_OF_SHIPS;
				computerShipsLeft =  EASY_NUM_OF_SHIPS;
			}
				break;
			case "Pro": {
				playerBoard = new Board(MEDIUM_SIZE_ROW,MEDIUM_SIZE_COL,true,MEDIUM_NUM_OF_SHIPS);
				computerBoard = new Board(MEDIUM_SIZE_ROW,MEDIUM_SIZE_COL,false,MEDIUM_NUM_OF_SHIPS);
				playerShipsLeft =  MEDIUM_NUM_OF_SHIPS;
				computerShipsLeft =  MEDIUM_NUM_OF_SHIPS;
			}
				break;
			case "Expert": {
				playerBoard = new Board(HARD_SIZE_ROW,HARD_SIZE_COL,true,HARD_NUM_OF_SHIPS);
				computerBoard = new Board(HARD_SIZE_ROW,HARD_SIZE_COL,false,HARD_NUM_OF_SHIPS);
				playerShipsLeft =  HARD_NUM_OF_SHIPS;
				computerShipsLeft =  HARD_NUM_OF_SHIPS;
			}
			break;
			default:
				Log.e(TAG, "Game: board init error");
		}
	}

	public Board getPlayerBoard() {
		return playerBoard;
	}

	public Board getComputerBoard() {
		return computerBoard;
	}

	public boolean playerPlay(int row, int col)
	{
		if (computerBoard.hit(row,col)) {
			if (computerBoard.getTile(row, col).getShip() != null && computerBoard.getTile(row, col).getShip().isDestroyed())
				computerShipsLeft--;
			moves++;
			if (computerBoard.getTile(row, col).isEmptySlot())
				playerTurn=false;
			return true;
		}
		return false;
	}
	public boolean computerPlay(){
		int col;
		int row;
		//do {
			do {
				col = ThreadLocalRandom.current().nextInt(0, playerBoard.getCols());
				row = ThreadLocalRandom.current().nextInt(0, playerBoard.getRows());
				if (playerBoard.hit(row, col)) {
					if (playerBoard.getTile(row, col).getShip() != null && playerBoard.getTile(row, col).getShip().isDestroyed())
						playerShipsLeft--;
					if (playerBoard.getTile(row, col).isEmptySlot())
						playerTurn=true;
					return true;
				}
			} while (!playerBoard.hit(row, col));
		//}while(!playerBoard.getTile(row, col).isEmptySlot());
		if (playerBoard.getTile(row, col).isEmptySlot())
			playerTurn=true;
		return false;
	}

	/**
	 *
	 * @return 0 - game not finished
	 * @return 1 - player won
	 * @return -1 - computer won
	 */
	public int getTotalShipsTiles(boolean playerBoard){
		if (playerBoard){
			return this.playerBoard.getNumOfShipsTiles();
		}
		return this.computerBoard.getNumOfShipsTiles();
	}
	public int gameStatus(){
		if (computerShipsLeft==0)
			return 1;
		if (playerShipsLeft==0)
			return -1;
		return 0;
	}
	public int getPlayerShipsLeft() {
		return playerShipsLeft;
	}

	public void setPlayerShipsLeft(int playerShipsLeft) {
		this.playerShipsLeft = playerShipsLeft;
	}

	public int getComputerShipsLeft() {
		return computerShipsLeft;
	}

	public void setComputerShipsLeft(int computerShipsLeft) {
		this.computerShipsLeft = computerShipsLeft;
	}

	public int getMoves() {
		return moves;
	}

	public double getScore(boolean forPlayer) {
		if (moves==0)
			return 0;
		if (forPlayer)
			return playerBoard.getNumOfShipsTiles()/(double)moves*100.0;
		return computerBoard.getNumOfShipsTiles()/(double)moves*100.0;
	}

	public void setMoves(int score) {
		this.moves = score;
	}
}
