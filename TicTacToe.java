// package GUIPackage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    private ArrayList<JButton> grid;
    private char player;
    private JLabel status;
    private int scoreX = 0;
    private int scoreO = 0;
    private JLabel scoreDisplay;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel board = new JPanel(new GridLayout(3, 3));
        board.setPreferredSize(new Dimension(300, 300));
        board.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        board.setBackground(new Color(250, 250, 250));
        grid = new ArrayList<>();
        player = 'X';

        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton();
            btn.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
            btn.setBackground(new Color(245, 245, 245));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(60, 179, 113), 3));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(230, 230, 250));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(245, 245, 245));
                }
            });
            btn.addActionListener(this);
            grid.add(btn);
            board.add(btn);
        }

        status = new JLabel("Player X's Turn", SwingConstants.CENTER);
        status.setFont(new Font("Verdana", Font.BOLD, 24));
        status.setForeground(new Color(72, 61, 139));
        status.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        scoreDisplay = new JLabel("Score - Player X: 0 | Player O: 0", SwingConstants.CENTER);
        scoreDisplay.setFont(new Font("Verdana", Font.PLAIN, 18));
        scoreDisplay.setForeground(new Color(105, 105, 105));

        JButton resetBtn = new JButton("Reset Game");
        resetBtn.setFont(new Font("Arial", Font.BOLD, 18));
        resetBtn.setBackground(new Color(255, 182, 193));
        resetBtn.setFocusPainted(false);
        resetBtn.addActionListener(e -> resetGame());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(245, 245, 245));
        top.add(status, BorderLayout.NORTH);
        top.add(scoreDisplay, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245, 245, 245));
        bottom.add(resetBtn);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(255, 255, 255));
        center.add(top);
        center.add(Box.createVerticalStrut(20));
        center.add(board);
        center.add(Box.createVerticalStrut(20));
        center.add(bottom);

        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.getText().equals("")) {
            btn.setText(String.valueOf(player));
            btn.setForeground(player == 'X' ? new Color(255, 69, 0) : new Color(30, 144, 255));
            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "Player " + player + " wins!");
                if (player == 'X') scoreX++;
                else scoreO++;
                updateScore();
                resetGame();
            } else if (boardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetGame();
            } else {
                player = (player == 'X') ? 'O' : 'X';
                status.setText("Player " + player + "'s Turn");
            }
        }
    }

    private boolean checkWin() {
        int[][] wins = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] c : wins) {
            if (!grid.get(c[0]).getText().equals("") &&
                grid.get(c[0]).getText().equals(grid.get(c[1]).getText()) &&
                grid.get(c[0]).getText().equals(grid.get(c[2]).getText())) {
                highlightWin(c);
                return true;
            }
        }
        return false;
    }

    private void highlightWin(int[] c) {
        for (int i : c) {
            grid.get(i).setBackground(new Color(144, 238, 144));
        }
    }

    private boolean boardFull() {
        for (JButton btn : grid) {
            if (btn.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        for (JButton btn : grid) {
            btn.setText("");
            btn.setBackground(new Color(245, 245, 245));
        }
        player = 'X';
        status.setText("Player X's Turn");
    }

    private void updateScore() {
        scoreDisplay.setText("Score - Player X: " + scoreX + " | Player O: " + scoreO);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
