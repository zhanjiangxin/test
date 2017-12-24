package game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cell {
	//细胞矩阵高度
    private int height;
    //细胞矩阵宽度
    private int width;
    //变换速度
    private int duration;
    //记录所有细胞的存活状态
    private int[][] matrix;
    
    public Cell() {}

    public Cell(int height, int width, int duration,  int[][] matrix) {
        this.height = height;
        this.width = width;
        this.duration = duration;
        this.matrix = matrix;
    }
    
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getDuration() {
        return duration;
    }
   

	/**
     *gameRule()：规则
     *当周围细胞存活数为2时 细胞保持原样
     *当周围细胞存活数为3使 细胞存活
     *当周围细胞数为其他时 细胞死亡
     */
    public void gameRule(){
        int[][] newCell=new int[height][width];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
            	newCell[y][x]=0;
                int nearNum= findSurvivalNum(y,x);
                //等于3，则下一状态总是活
                if(nearNum==3){
                	newCell[y][x]=1;
                }
                //等于2，则与上一状态一样
                else if(nearNum==2){
                	newCell[y][x]=matrix[y][x];
                }
            }
        }
        matrix=newCell;
    }
    /**
     * findLifedNum():统计周围存活的个数
     */
    public int findSurvivalNum(int y, int x){
        int num=0;
        //左边
        if(x!=0){
            num+=matrix[y][x-1];
        }
        //左上角
        if(x!=0&&y!=0){
            num+=matrix[y-1][x-1];
        }
        //上边
        if(y!=0){
            num+=matrix[y-1][x];
        }
        //右上
        if(x!=width-1&&y!=0){
            num+=matrix[y-1][x+1];
        }
        //右边
        if(x!=width-1){
            num+=matrix[y][x+1];
        }
        //右下
        if(x!=width-1&&y!=height-1){
            num+=matrix[y+1][x+1];
        }
        //下边
        if(y!=height-1){
            num+=matrix[y+1][x];
        }
        //左下
        if(x!=0&&y!=height-1){
            num+=matrix[y+1][x-1];
        }
        return num;
    }
    /**
     *  getFromFile():从文件中读取细胞图的信息
     */  
 public Cell getFromFile(String path)
    {
    	  BufferedReader reader = null;
          try {
              reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
              String line = reader.readLine();
              String[] array = line.split(" ");
              int width = Integer.parseInt(array[0]);
              int height = Integer.parseInt(array[1]);
              int duration = Integer.parseInt(array[2]);
              int[][] matrix = new int[height][width];
              for (int i = 0; i < height; i++) {
                  line = reader.readLine();
                  array = line.split(" ");
                  for (int j = 0; j < array.length; j++) {
                      matrix[i][j] = Integer.parseInt(array[j]);
                  }
              }
              Cell cell= new Cell(height, width, duration, matrix);
              return cell;
          } catch (FileNotFoundException e) {
             e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          } finally {
              if (reader != null) {
                  try {
                      reader.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
          return null;
      }

}
