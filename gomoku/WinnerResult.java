package net.devstudy.jse.lection06_gomoku;

import java.util.List;

public interface WinnerResult {

	boolean winnerExists();

	List<Cell> getWinnerCells();
}
