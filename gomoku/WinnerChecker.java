package net.devstudy.jse.lection06_gomoku;

public interface WinnerChecker {

	void setGameTable(GameTable gameTable);

	WinnerResult isWinnerFound(CellValue cellValue);
}