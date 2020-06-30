package Game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Minesweeper implements MouseListener {
	JFrame frame;
	Btn[][] board = new Btn[10][10];
	int openButton;
	Color color=new Color(209, 242, 235);

	public Minesweeper() {
		openButton = 0;
		frame = new JFrame("MÝNESWEEPER");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(10, 10));

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				Btn b = new Btn(row, col);
				frame.add(b);
				b.addMouseListener(this);
				board[row][col] = b;
			}
		}
		generateMine();
		updateCount();

		frame.setVisible(true);
	}

	public void generateMine() {
		int i = 0;
		while (i < 10) {
			int randRow = (int) (Math.random() * board.length);
			int randCol = (int) (Math.random() * board[0].length);

			while (board[randRow][randCol].isMine()) {
				randRow = (int) (Math.random() * board.length);
				randCol = (int) (Math.random() * board[0].length);
			}
			board[randRow][randCol].setMine(true);
			i++;
		}
	}

	public void updateCount() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					counting(row, col);
				}
			}
		}
	}

	public void counting(int row, int col) {
		for (int i = row - 1; i <= row + 1; i++) {
			for (int k = col - 1; k <= col + 1; k++) {
				try {
					int value = board[i][k].getCount();
					board[i][k].setCount(++value);
				} catch (Exception e) {

				}
			}
		}
	}

	public void print() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					ImageIcon mineicon = new ImageIcon("mine.png");
					board[row][col].setText("M");
					board[row][col].setIcon(mineicon);
				} else {
					board[row][col].setText(board[row][col].getCount() + "");
					board[row][col].setEnabled(false);
				}
			}
		}
	}

	public void open(int r, int c) {
		if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c].getText().length() > 0
				|| board[r][c].isEnabled() == false) {
			return;
		} else if (board[r][c].getCount() != 0) {
			board[r][c].setText(board[r][c].getCount() + "");
			board[r][c].setEnabled(false);
			openButton++;
		} else {
			openButton++;
			board[r][c].setEnabled(false);
			open(r - 1, c);
			open(r + 1, c);
			open(r, c - 1);
			open(r, c + 1);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Btn b = (Btn) e.getComponent();
		if (e.getButton() == 1) {
			if (b.isMine()) {
				JOptionPane.showMessageDialog(frame, "--- GAME OVER! ---");
				print();
			} else {
				open(b.getRow(), b.getCol());
				if (openButton == (board.length * board[0].length) - 10) {
					JOptionPane.showMessageDialog(frame, "**** YOU WÝN ****");
					print();
				}
			}
		} else if (e.getButton() == 3) {
			if (!b.isFlag()) {
				ImageIcon flagicon = new ImageIcon("flag.jpg");
				b.setText("F");
				b.setIcon(flagicon);
				b.setFlag(true);
			} else {
				b.setIcon(null);
				b.setText("");
				b.setFlag(false);
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}