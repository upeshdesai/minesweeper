//package minesweeper;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class BoardBuild extends JFrame implements ActionListener {
    static BoardBuild board = new BoardBuild();
    static JPanel p1, p2, p3;
    int miss = 0;

    // top label
    JLabel lbl1 = new JLabel("Minesweeper");

    public static final int BOARD_HEIGHT = 10;
    public static final int BOARD_WIDTH = 10;
    public static final int NUMBER_OF_CELLS = BOARD_HEIGHT * BOARD_WIDTH;
    public static final int BOMB_VALUE = 99; 
    public static final int NO_MINE = 0; 

    // all 100 of the board pieces to click
    JButton[][] btn = new JButton[BOARD_WIDTH][BOARD_HEIGHT];

    int mines[][] = new int[BOARD_WIDTH][BOARD_HEIGHT];// board piece values
    private GenerateMines mineGen;
    JButton btnReset = new JButton("Reset");

    public BoardBuild() {
        p1 = new JPanel();
        p1.add(lbl1, BorderLayout.CENTER);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(BOARD_WIDTH, BOARD_HEIGHT));

        for (int x = 0; x < BOARD_HEIGHT; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                btn[x][y] = new JButton("");
                btn[x][y].addActionListener(this);
                p2.add(btn[x][y]);
            }
        }
        getMines();
        p3 = new JPanel();
        btnReset.addActionListener(this);
        p3.add(btnReset, BorderLayout.CENTER);
    }

    public void getMines() {
        mineGen = new GenerateMines();
        System.out.println("New Mine Values Generated");

        // sets indicator according to how many bombs around
        for (int x = 0; x < BOARD_HEIGHT; x++) { 
            for (int y = 0; y < BOARD_WIDTH; y++) {
                btn[x][y].setText("");
                mines[x][y] = mineGen.getMinePos(x, y);
                if (mines[x][y] >= BOMB_VALUE) 
                    btn[x][y].setForeground(Color.RED);
                else {
                    btn[x][y].setBackground(null);
                    if (mines[x][y] == 1)
                        btn[x][y].setForeground(Color.BLUE);
                    else if (mines[x][y] == 2)
                        btn[x][y].setForeground(Color.GREEN);
                    else if (mines[x][y] == 3)
                        btn[x][y].setForeground(Color.YELLOW);
                    else if (mines[x][y] == 4)
                        btn[x][y].setForeground(Color.ORANGE);
                    else if (mines[x][y] == 5)
                        btn[x][y].setForeground(Color.RED);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int x = 0; x < BOARD_HEIGHT; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if (e.getSource() == btn[x][y]) {
                    btn[x][y].removeActionListener(this);
                    if (mines[x][y] >= BOMB_VALUE) {
                        showBoard();
                        return;
                    } else if (mines[x][y] != NO_MINE) {
                        btn[x][y].setText("" + mines[x][y]);
                        btn[x][y].setBackground(Color.GRAY);
                        miss++;
                    } else if (mines[x][y] == NO_MINE)
                        floodFill(x, y);
                    checkWin();
                    System.out.println(miss);
                }
            }
        }
        if (e.getSource() == btnReset) {
            miss = 0;
            System.out.println(miss);
            getMines();
            for (int x = 0; x < BOARD_HEIGHT; x++) {
                for (int y = 0; y < BOARD_WIDTH; y++) {
                    btn[x][y].addActionListener(this);
                    btn[x][y].setBackground(null);
                }
            }
        }
    }

    public void floodFill(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            for (int z = 1; z < 6; z++) {
                if (mines[x][y] == z) {
                    miss++;
                    System.out.println(miss);
                    btn[x][y].setText(z + "");
                    btn[x][y].setBackground(Color.GRAY);
                    return;
                }
            }
            if (mines[x][y] == 0 && btn[x][y].getBackground() != Color.GRAY) {
                miss++;
                System.out.println(miss);
                btn[x][y].setBackground(Color.GRAY);
                btn[x][y].removeActionListener(this);
                floodFill(x - 1, y);
                floodFill(x + 1, y);
                floodFill(x, y - 1);
                floodFill(x, y + 1);
            } else {
                return;
            }
        }
    }

    public void showBoard() {
        System.out.println("BOOOM");
        JOptionPane.showMessageDialog(this, "Unfortunately, You Lose", "",
                JOptionPane.PLAIN_MESSAGE);
        for (int x = 0; x < BOARD_HEIGHT; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                btn[x][y].setBackground(Color.GRAY);
                if (mines[x][y] != NO_MINE) {
                    if (mines[x][y] >= BOMB_VALUE)
                        btn[x][y].setText("B");
                    else
                        btn[x][y].setText("" + mines[x][y]);
                }
            }
        }
        miss = 0;
    }

    public void checkWin() {
        if (miss == 90) {
            System.out.println("WIN");
            JOptionPane.showMessageDialog(this, "Congratulations, You Win!!",
                    "", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - 450) / 2);
        int y = (int) ((dimension.getHeight() - 500) / 2);

        board.setLocation(x, y);
        board.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        board.add(p1, BorderLayout.NORTH);
        board.add(p2, BorderLayout.CENTER);
        board.add(p3, BorderLayout.SOUTH);
        board.setSize(450, 500);
        board.setVisible(true);
        board.setResizable(true);
    }
}