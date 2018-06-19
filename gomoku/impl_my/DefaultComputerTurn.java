package net.devstudy.jse.lection06_gomoku.impl_my;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devstudy.jse.lection06_gomoku.Cell;
import net.devstudy.jse.lection06_gomoku.CellValue;
import net.devstudy.jse.lection06_gomoku.ComputerTurn;
import net.devstudy.jse.lection06_gomoku.GameTable;

public class DefaultComputerTurn implements ComputerTurn {
	
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
	public Cell makeTurn() {

		Cell cell = null;
		for (int i = 4; i > 0; i--) {
			
			cell = tryWin(CellValue.COMPUTER, i);
			if (cell != null) {
				LOGGER.info("Computer turn is {}", cell);
				return cell;
			}
			
			cell = tryWin(CellValue.HUMAN, i);
			if (cell != null) {
				LOGGER.info("Computer turn is {}", cell);
				return cell;
			}
		}

		cell = randomCompTurn();
		
		return cell;
	}

	protected Cell randomCompTurn() {
		
		List<Cell> allEmptyCells = getEmptyCells();
		if (allEmptyCells.size() > 0) {
		Cell randomCell = allEmptyCells.get(new Random().nextInt(allEmptyCells.size()));
		int row = randomCell.getRowIndex();
		int col = randomCell.getColIndex();
		gameTable.setValue(row, col, CellValue.COMPUTER);
		LOGGER.info("Computer random turn is {}", randomCell);
		return randomCell;
	} else {
		throw new ComputerCantMakeTurnException(
				"All cells are filled! Have you checked draw state before call of computer turn?");
	}
		
		
	}

	protected List<Cell> getEmptyCells() {
		List<Cell> allEmptyCells = new ArrayList<>();
		
		for (int i = 0; i < gameTable.getSize(); i++) {
			for (int j = 0; j < gameTable.getSize(); j++) {
				if (gameTable.isCellFree(i, j)) {
					allEmptyCells.add(new Cell(i, j));
				}
			}
		}
	
		return allEmptyCells;
	}

	public Cell tryWin(CellValue cellValue, int numberCheckedCells) {

		LOGGER.trace("Try to make turn by row for pattern: {} empty and {} not empty cells for {}", 
				winCount - numberCheckedCells, numberCheckedCells, cellValue);
		Cell cell = trySetToRow(cellValue, numberCheckedCells);
		if (cell != null) {
			return cell;
		}
		LOGGER.trace("Try to make turn by col for pattern: {} empty and {} not empty cells for {}", 
				winCount - numberCheckedCells, numberCheckedCells, cellValue);
		cell = trySetToCol(cellValue, numberCheckedCells);
		if (cell != null) {
			return cell;
		}
		LOGGER.trace("Try to make turn by main diagonal for pattern: {} empty and {} not empty cells for {}", 
				winCount - numberCheckedCells, numberCheckedCells, cellValue);
		cell = trySetToMainDiagonal(cellValue, numberCheckedCells);
		if (cell != null) {
			return cell;
		}
		LOGGER.trace("Try to make turn by not main diagonal for pattern: {} empty and {} not empty cells for {}", 
				winCount - numberCheckedCells, numberCheckedCells, cellValue);
		cell = trySetToNotMainDiagonal(cellValue, numberCheckedCells);
		if (cell != null) {
			return cell;
		}
		return null;
	}

	public Cell trySetToRow(CellValue cellValue, int numberCheckedCells) {
		List<Cell> checkCells = new ArrayList<>();
		List<Cell> emptyCells = new ArrayList<>();

		
		for (int i = 0; i < gameTable.getSize(); i++) {
			for (int j = 0; j < gameTable.getSize() - (winCount - 1); j++) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i, j + k) == cellValue) {
						checkCells.add(new Cell(i, j + k));
					} else if (gameTable.getValue(i, j + k) == CellValue.EMPTY) {
						emptyCells.add(new Cell(i, j + k));
					} else {
						checkCells.clear();
						emptyCells.clear();
						break;
					}
				}
				if ((checkCells.size() == numberCheckedCells) && (emptyCells.size() == winCount - numberCheckedCells)) {
					LOGGER.debug("Found {} empty and {} not empty cells by row: {} {}", winCount - checkCells.size(), 
							checkCells.size(), checkCells, new LoggerPattern(checkCells));
					Cell cell = makeCompTurn(emptyCells);
					
					return cell;
				} else {
					checkCells.clear();
					emptyCells.clear();
				}
			}
		}
		return null;
	}

	public Cell makeCompTurn(List<Cell> emptyCells) {
		//Cell chouseCell = emptyCells.get(0);
		//Cell chouseCell = emptyCells.get(emptyCells.size() - 1);
		//Cell chouseCell = emptyCells.get(emptyCells.size()/2);
		Cell chouseCell = emptyCells.get(new Random().nextInt(emptyCells.size()));
		int row = chouseCell.getRowIndex();
		int col = chouseCell.getColIndex();
		gameTable.setValue(row, col, CellValue.COMPUTER);
		LOGGER.trace("The best cell is {} for pattern {} {}", chouseCell, emptyCells, new LoggerPattern(emptyCells));
		return chouseCell;
		
	}

	public Cell trySetToCol(CellValue cellValue, int numberCheckedCells) {
		List<Cell> checkCells = new ArrayList<>();
		List<Cell> emptyCells = new ArrayList<>();
		
		for (int i = 0; i < gameTable.getSize(); i++) {
			for (int j = 0; j < gameTable.getSize() - (winCount - 1); j++) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(j + k, i) == cellValue) {
						checkCells.add(new Cell(j + k, i));
					} else if (gameTable.getValue(j + k, i) == CellValue.EMPTY) {
						emptyCells.add(new Cell(j + k, i));
					} else {
						checkCells.clear();
						emptyCells.clear();
						break;
					}
				}
				if ((checkCells.size() == numberCheckedCells) && (emptyCells.size() == winCount - numberCheckedCells)) {
					LOGGER.debug("Found {} empty and {} not empty cells by col: {} {}", winCount - checkCells.size(), 
							checkCells.size(), checkCells, new LoggerPattern(checkCells));
					
					Cell cell = makeCompTurn(emptyCells);
					return cell;
				} else {
					checkCells.clear();
					emptyCells.clear();
				}
			}
		}
		return null;
	}

	public Cell trySetToMainDiagonal(CellValue cellValue, int numberCheckedCells) {
		List<Cell> checkCells = new ArrayList<>();
		List<Cell> emptyCells = new ArrayList<>();

		for (int i = 0; i < gameTable.getSize() - (winCount - 1); i++) {
			for (int j = 0; j < gameTable.getSize() - (winCount - 1); j++) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i + k, j + k) == cellValue) {
						checkCells.add(new Cell(i + k, j + k));
					} else if (gameTable.getValue(i + k, j + k) == CellValue.EMPTY) {
						emptyCells.add(new Cell(i + k, j + k));
					} else {
						checkCells.clear();
						emptyCells.clear();
						break;
					}
				}
				if ((checkCells.size() == numberCheckedCells) && (emptyCells.size() == winCount - numberCheckedCells)) {
					LOGGER.debug("Found {} empty and {} not empty cells by main diagonal: {} {}", winCount - checkCells.size(), 
							checkCells.size(), checkCells, new LoggerPattern(checkCells));
					Cell cell = makeCompTurn(emptyCells);
					return cell;
				} else {
					checkCells.clear();
					emptyCells.clear();
				}
			}
		}
		return null;
	}

	public Cell trySetToNotMainDiagonal(CellValue cellValue, int numberCheckedCells) {
		List<Cell> checkCells = new ArrayList<>();
		List<Cell> emptyCells = new ArrayList<>();

		for (int i = 0; i < gameTable.getSize() - (winCount - 1); i++) {
			for (int j = gameTable.getSize() - 1; j >= winCount - 1; j--) {
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i + k, j - k) == cellValue) {
						checkCells.add(new Cell(i + k, j - k));
					} else if (gameTable.getValue(i + k, j - k) == CellValue.EMPTY) {
						emptyCells.add(new Cell(i + k, j - k));
					} else {
						checkCells.clear();
						emptyCells.clear();
						break;
					}
				}
				if ((checkCells.size() == numberCheckedCells) && (emptyCells.size() == winCount - numberCheckedCells)) {
					LOGGER.debug("Found {} empty and {} not empty cells by not main diagonal: {} {}", winCount - checkCells.size(), 
							checkCells.size(), checkCells, new LoggerPattern(checkCells));
					Cell cell = makeCompTurn(emptyCells);
					
					return cell;
				} else {
					checkCells.clear();
					emptyCells.clear();
				}
			}
		}
		return null;
	}

	@Override
	public Cell makeFirstTurn() {
		
		int row = DefaultConstants.SIZE / 2;
		int col = DefaultConstants.SIZE / 2;
		Cell cell = new Cell(row, col);
		gameTable.setValue(row, col, CellValue.COMPUTER);
		LOGGER.info("Computer first turn is {}", cell);
		return cell;
	}

	private class LoggerPattern {
		private final List<Cell> cells;
		LoggerPattern(List<Cell> cells) {
			super();
			this.cells = cells;
		}
		@Override
		public String toString() {
			StringBuilder pattern = new StringBuilder("[");
			for(Cell cell : cells) {
				CellValue cellValue = gameTable.getValue(cell.getRowIndex(), cell.getColIndex());
				pattern.append(cellValue == CellValue.EMPTY ? "*" : cellValue.getValue());
			}
			pattern.append("]");
			return pattern.toString();
		}
	}
	
	
	
	
	
}