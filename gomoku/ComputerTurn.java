package net.devstudy.jse.lection06_gomoku;

public interface ComputerTurn {

	void setGameTable(GameTable gameTable);

	Cell makeTurn();

	Cell makeFirstTurn();
}