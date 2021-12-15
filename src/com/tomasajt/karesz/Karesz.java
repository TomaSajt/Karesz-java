package com.tomasajt.karesz;

public class Karesz {

	public static final int empty = 0;
	public static final int wall = 1;
	public static final int black = 2;
	public static final int red = 3;
	public static final int green = 4;
	public static final int yellow = 5;

	public static final int north = 0;
	public static final int east = 1;
	public static final int south = 2;
	public static final int west = 3;

	public static final int right = 1;
	public static final int left = -1;
	private int[] stoneNum = { 0, 0, 100, 100, 100, 100 };

	private int x;
	private int y;
	private int direction = north;
	private AbstractLevel level;
	private int time = 0;

	public Karesz(AbstractLevel level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
	}

	public void Turn(int direction) {
		this.direction = mod(this.direction + direction, 4);
		time++;
	}

	private int mod(int a, int b) {
		if (a < 0)
			return b + a % b;
		else
			return a % b;

	}

	public void pickUpStone() {
		if (level.levelData[x][y] > wall) {
			stoneNum[level.levelData[x][y]]++;
			level.levelData[x][y] = empty;
			time++;
			updateLabels();
		}
	}

	public void placeDownStone(int color) {
		if (!isThereAStoneHere()) {
			level.levelData[x][y] = color;
			stoneNum[color]--;
			time++;
			updateLabels();
		} else {
			level.showMessage("Már van itt kavics");
		}
	}

	public boolean isThereAStoneHere() {
		return level.levelData[x][y] > wall;
	}

	public int whatIsUnderMe() {
		return level.levelData[x][y];
	}

	public void updateLabels() {
		level.labelBlack.setText("Fekete: " + stoneNum[black]);
		level.labelRed.setText("Piros: " + stoneNum[red]);
		level.labelGreen.setText("Zöld: " + stoneNum[green]);
		level.labelYellow.setText("Sárga: " + stoneNum[yellow]);
		level.labelTime.setText("Idõ: " + time);
	}

	public void Step() {
		int dx = 0;
		int dy = 0;
		switch (direction) {
		case north:
			dy--;
			break;
		case east:
			dx++;
			break;
		case south:
			dy++;
			break;
		case west:
			dx--;
			break;
		}
		int newX = x + dx;
		int newY = y + dy;
		if (newX >= 0 && newX < level.getLevelWidth() && newY >= 0 && newY < level.getLevelHeight()
				&& level.levelData[newX][newY] != wall) {
			x = newX;
			y = newY;
		} else
			level.showMessage("Nem tudok lépni!");
		time++;
	}

	public int whatIsInFrontOfMe() {
		int dx = 0;
		int dy = 0;
		switch (direction) {
		case north:
			dy--;
			break;
		case east:
			dx++;
			break;
		case south:
			dy++;
			break;
		case west:
			dx--;
			break;
		}
		int newX = x + dx;
		int newY = y + dy;
		if (newX >= 0 && newX < level.getLevelWidth() && newY >= 0 && newY < level.getLevelHeight()
				&& level.levelData[newX][newY] != wall) {
			return level.levelData[newX][newY];
		}
		return -1;
	}

	public int getDirection() {
		return direction;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}
