package net.devstudy.jse.lection06_gomoku;

public interface HumanTurn {

	void setGameTable(GameTable gameTable);

	Cell makeTurn(int row, int col);
}