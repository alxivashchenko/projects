package net.devstudy.jse.lection06_gomoku.impl_my;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devstudy.jse.lection06_gomoku.Cell;
import net.devstudy.jse.lection06_gomoku.CellValue;
import net.devstudy.jse.lection06_gomoku.GameTable;
import net.devstudy.jse.lection06_gomoku.WinnerChecker;
import net.devstudy.jse.lection06_gomoku.WinnerResult;

public class DefaultWinnerChecker implements WinnerChecker {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultComputerTurn.class);
	private GameTable gameTable;
	private int winCount = DefaultConstants.WIN_COUNT;

	@Override
	public void setGameTable(GameTable gameTable) {
		if (gameTable == null) {
			throw new NullPointerException("Game table can't be null");
		}
		if (gameTable.getSize() < winCount) {
			throw new IllegalArgumentException(
					"Size of gameTable is small: size=" + gameTable.getSize() + ". Required >= " + winCount);
		}
		this.gameTable = gameTable;

	}

	@Override
	public WinnerResult isWinnerFound(CellValue cellValue) {
		if (cellValue == null) {
			throw new NullPointerException("cellValue can't be null");
		}

		LOGGER.trace("Try to find winner by row: is {} winner?", cellValue);
		List<Cell> result = checkByRow(cellValue);

		if (result != null) {
			LOGGER.debug("Winner is {}. By row: {}", cellValue, result);
			return new DefaultWinnerResult(result);
		}

		LOGGER.trace("Try to find winner by col: is {} winner?", cellValue);
		result = checkByCol(cellValue);

		if (result != null) {
			LOGGER.debug("Winner is {}. By col: {}", cellValue, result);
			return new DefaultWinnerResult(result);
		}

		LOGGER.trace("Try to find winner by main diagonal: is {} winner?", cellValue);
		result = checkByMainDiagonal(cellValue);

		if (result != null) {
			LOGGER.debug("Winner is {}. By main diagonal: {}", cellValue, result);
			return new DefaultWinnerResult(result);
		}

		LOGGER.trace("Try to find winner by not main diagonal: is {} winner?", cellValue);
		result = checkByNotMainDiagonal(cellValue);

		if (result != null) {
			LOGGER.debug("Winner is {}. By not main diagonals: {}", cellValue, result);
			return new DefaultWinnerResult(result);
		}

		LOGGER.trace("Winner not found");
		return new DefaultWinnerResult(null);

	}

	public List<Cell> checkByRow(CellValue cellValue) {
		List<Cell> checkCells = new ArrayList<>();
		for (int i = 0; i < gameTable.getSize(); i++) {
			for (int j = 0; j < gameTable.getSize() - (winCount - 1); j++) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i, j + k) == cellValue) {
						checkCells.add(new Cell(i, j + k));
					} else {
						checkCells.clear();
						break;
					}
				}
				if (checkCells.size() == winCount) {
					return checkCells;
				}
			}
		}
		return null;
	}

	public List<Cell> checkByCol(CellValue cellValue) {
		List<Cell> checkCells = new ArrayList<>();

		for (int i = 0; i < gameTable.getSize(); i++) {
			for (int j = 0; j < gameTable.getSize() - (winCount - 1); j++) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(j + k, i) == cellValue) {
						checkCells.add(new Cell(j + k, i));
					} else {
						checkCells.clear();
						break;
					}
				}
				if (checkCells.size() == winCount) {
					return checkCells;
				}
			}
		}
		return null;
	}

	public List<Cell> checkByMainDiagonal(CellValue cellValue) {
		List<Cell> checkCells = new ArrayList<>();
		for (int i = 0; i < gameTable.getSize() - (winCount - 1); i++) {
			for (int j = 0; j < gameTable.getSize() - (winCount - 1); j++) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i + k, j + k) == cellValue) {
						checkCells.add(new Cell(i + k, j + k));
					} else {
						checkCells.clear();
						break;
					}
				}
				if (checkCells.size() == winCount) {
					return checkCells;
				}
			}
		}
		return null;
	}

	public List<Cell> checkByNotMainDiagonal(CellValue cellValue) {
		List<Cell> checkCells = new ArrayList<>();
		for (int i = 0; i < gameTable.getSize() - (winCount - 1); i++) {
			for (int j = gameTable.getSize() - 1; j >= winCount - 1; j--) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i + k, j - k) == cellValue) {
						checkCells.add(new Cell(i + k, j - k));
					} else {
						checkCells.clear();
						break;
					}
				}
				if (checkCells.size() == winCount) {
					return checkCells;
				}
			}
		}
		return null;
	}

	private static class DefaultWinnerResult implements WinnerResult {
		private final List<Cell> winnerCells;

		DefaultWinnerResult(List<Cell> winnerCells) {
			this.winnerCells = winnerCells != null ? Collections.unmodifiableList(winnerCells)
					: Collections.emptyList();
		}

		@Override
		public boolean winnerExists() {
			return winnerCells.size() == DefaultConstants.WIN_COUNT ? true : false;
		}

		@Override
		public List<Cell> getWinnerCells() {
			return winnerCells;
		}

	}

}