package net.devstudy.jse.lection06_gomoku;

import net.devstudy.jse.lection06_gomoku.impl_my.DefaultGameTable;

public class Test {

	public static void main(String[] args) {
		Cell cell = new Cell(0, 0);
		System.out.println(cell.toString());
		System.out.println(CellValue.COMPUTER);
		GameTable gt = new DefaultGameTable();
		System.out.println(gt.getClass());
		System.out.println(gt.hashCode());
		System.out.println(gt.getSize());
		System.out.println(gt.getValue(1, 1));
		System.out.println(gt.toString());
		gt.reInit();
		System.out.println(gt.toString());
		GameTable gt2 = new DefaultGameTable();
		gt2.getValue(1, 1);
		boolean bl = gt2.emptyCellExists();
		System.out.println(bl);
		System.out.println(gt2.getValue(2, 2));
		gt2.setValue(2, 2, CellValue.COMPUTER);
		System.out.println(gt2.getValue(2, 2));
		
	}

}
