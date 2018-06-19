package net.devstudy.jse.lection06_gomoku.impl_my;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devstudy.jse.lection06_gomoku.Cell;
import net.devstudy.jse.lection06_gomoku.CellValue;
import net.devstudy.jse.lection06_gomoku.GameTable;
import net.devstudy.jse.lection06_gomoku.HumanTurn;

public class DefaultHumanTurn implements HumanTurn {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHumanTurn.class);
	private GameTable gameTable;

	@Override
	public void setGameTable(GameTable gameTable) {
		
		if (gameTable == null) {
            throw new NullPointerException("Game table can't be null");
		}
		this.gameTable = gameTable;
		
	}

	@Override
	public Cell makeTurn(int row, int col) {
		Cell cell = new Cell(row, col);
		gameTable.setValue(row, col, CellValue.HUMAN);
		LOGGER.info("Human turn is {}", cell);
		return cell;
		
		
	}

	
}