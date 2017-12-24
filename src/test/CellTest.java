package test;

import org.junit.Assert;
import org.junit.Test;


import game.Cell;

public class CellTest {
	Cell cell = new Cell(3, 3, 200, new int[][] { { 1, 0, 1 }, { 0, 1, 0 }, { 1, 1, 1 } });

	@Test
	public void testgameRule() throws Exception {
		int[][] expected = new int[][] { { 1, 0, 1 }, { 0, 1, 0 }, { 1, 1, 1 } };
		Assert.assertArrayEquals(expected, cell.getMatrix());
		System.out.println("初始状态");
		cell.gameRule();
		System.out.println("第一次细胞图变换");
		expected = new int[][] { { 0, 1, 0 }, { 0, 0, 0 }, { 1, 1, 1 } };
		Assert.assertArrayEquals(expected, cell.getMatrix());
		cell.gameRule();
		System.out.println("第二次细胞图变换");
		expected = new int[][] { { 0, 0, 0 }, { 1, 0, 1 }, { 0, 1, 0 } };
		Assert.assertArrayEquals(expected, cell.getMatrix());
	}

	@Test
	public void testfindSurvivalNum() throws Exception {
		Assert.assertEquals(1, cell.findSurvivalNum(0, 0));
		Assert.assertEquals(3, cell.findSurvivalNum(0, 1));
		Assert.assertEquals(1, cell.findSurvivalNum(0, 2));
		Assert.assertEquals(4, cell.findSurvivalNum(1, 0));
		Assert.assertEquals(5, cell.findSurvivalNum(1, 1));
		Assert.assertEquals(4, cell.findSurvivalNum(1, 2));
		Assert.assertEquals(2, cell.findSurvivalNum(2, 0));
		Assert.assertEquals(3, cell.findSurvivalNum(2, 1));
		Assert.assertEquals(2, cell.findSurvivalNum(2, 2));
	}

	@Test
	public void testgetFromFile() throws Exception {
		Cell testcell = new Cell();
		String currentPath=this.getClass().getResource("/").getPath();
		System.out.println(currentPath);
		String path=currentPath+"//test.case";
		testcell = cell.getFromFile(path);
		int[][] expected = new int[][] { { 1, 0, 1 }, { 0, 1, 0 }, { 1, 1, 1 } };
		Assert.assertEquals(3, testcell.getHeight());
		Assert.assertEquals(3, testcell.getWidth());
		Assert.assertEquals(200, testcell.getDuration());
		Assert.assertArrayEquals(expected, testcell.getMatrix());
	    //输入path为空时
		testcell = cell.getFromFile("");
        Assert.assertEquals(null,testcell);
	}
}
