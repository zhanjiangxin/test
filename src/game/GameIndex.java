package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;


public class GameIndex extends JFrame{
    private JButton startGameBtn = new JButton("Start Game");
    private boolean isStart = false;
    private boolean stop = true;
    private Cell cell;
    private JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
    private JPanel gridPanel = new JPanel();
    private JTextField[][] textMatrix;
    private static final int DEFAULT_DURATION = 200;

    //动画间隔
    private int duration = DEFAULT_DURATION;

    public GameIndex() {
        setTitle("Game Of Life");
        startGameBtn.addActionListener(new StartGameActioner());
        buttonPanel.add(startGameBtn);
        buttonPanel.setBackground(Color.WHITE);
        getContentPane().add("South", buttonPanel);
        String currentPath=this.getClass().getResource("/").getPath();
		String path=currentPath+"//cell.txt";
        cell=new Cell().getFromFile(path);
        initGridLayout();
        showMatrix();
        gridPanel.updateUI();
        this.setSize(800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void showMatrix() {
        int[][] matrix = cell.getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    textMatrix[y][x].setBackground(Color.BLACK);
                } else {
                    textMatrix[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * 创建显示的gridlayout布局
     */
    private void initGridLayout() {
        int rows = cell.getHeight();
        int cols = cell.getWidth();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add("Center", gridPanel);
    }


    private class StartGameActioner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isStart) {
                //获取时间
                new Thread(new GameControlTask()).start();
                isStart = true;
                stop = false;
                startGameBtn.setText("Stop Game");
            } else {
                stop = true;
                isStart = false;
                startGameBtn.setText("Start Game");
            }
        }
    }

    private class GameControlTask implements Runnable {

        @Override
        public void run() {

            while (!stop) {
                cell.gameRule();
                showMatrix();
                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

}
