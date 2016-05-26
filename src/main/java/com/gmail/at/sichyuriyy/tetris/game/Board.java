package com.gmail.at.sichyuriyy.tetris.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private final int BOARD_WIDTH = 12;
	private final int BOARD_HEIGHT = 23;

	private int squareSize = 25;

	private Timer timer;
	private boolean isFallingFinished = false;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private int numLinesRemoved = 0;
	private int curX = 0;
	private int curY = 0;
	private JLabel statusbar;
	private FallingPiece curPiece;
	private Color[][] board;

	private PieceGenerator pieceGenerator;
	private PieceContainer pieceContainer;

	public Board(Game parent) {
		
		
		initBoard(parent);
	}

	private void initBoard(Game parent) {
		this.pieceGenerator = parent.getPieceGenerator();
		setFocusable(true);
		curPiece = pieceGenerator.getPiece();
		pieceGenerator.changePiece();
		timer = new Timer(400, this);
		timer.start();
		statusbar = parent.getStatusBar();
		this.pieceContainer = parent.getPieceContainer();
		pieceContainer.repaint();
		board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
		addKeyListener(new TAdapter());
		clearBoard();
		super.setBackground(Color.BLACK);
		super.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {

			oneLineDown();
		}
	}

	public void start() {
		if (isPaused)
			return;

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();

		newPiece();
		timer.start();
	}

	private void pause() {

		if (!isStarted)
			return;

		isPaused = !isPaused;

		if (isPaused) {

			timer.stop();
			statusbar.setText("paused");
		} else {

			timer.start();
			statusbar.setText(String.valueOf(numLinesRemoved));
		}

		repaint();
	}

	private void doDrawing(Graphics g) {

		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				if (board[y][x] != null) {
					drawSquarePart(g, x, y, board[y][x]);
				}
			}
		}
		if (curPiece.getColor() != null) {
			for (int y = 0; y < curPiece.getHeight(); y++) {
				for (int x = 0; x < curPiece.getWidth(); x++) {
					if (curPiece.getContent()[y][x] == 1) {
						drawSquarePart(g, curX + x, curY + y, curPiece.getColor());
					}
				}
			}

		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}

	private void dropDown() {

		int newY = curY;

		while (newY < BOARD_HEIGHT) {

			if (!tryMove(curPiece, curX, newY + 1))
				break;
			newY++;
		}

		pieceDropped();
	}

	private void oneLineDown() {

		if (!tryMove(curPiece, curX, curY + 1))
			pieceDropped();
	}

	private void clearBoard() {

		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = null;
			}
		}

	}

	private void pieceDropped() {

		for (int y = 0; y < curPiece.getHeight(); y++) {
			for (int x = 0; x < curPiece.getWidth(); x++) {
				if (curPiece.getContent()[y][x] == 1) {
					board[curY + y][curX + x] = curPiece.getColor();
				}
			}
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void newPiece() {

		curPiece = pieceGenerator.getPiece();
		pieceGenerator.changePiece();
		pieceContainer.repaint();
		curX = BOARD_WIDTH / 2 - 2;
		curY = 0;

		if (!tryMove(curPiece, curX, curY)) {

			timer.stop();
			isStarted = false;
			String score = statusbar.getText();
			statusbar.setText(score + " !!!");
		}
	}

	private boolean tryMove(FallingPiece newPiece, int newX, int newY) {

		for (int y = 0; y < newPiece.getHeight(); y++) {
			for (int x = 0; x < newPiece.getWidth(); x++) {
				if (newPiece.getContent()[y][x] != 1) {
					continue;
				}
				int posY = newY + y;
				int posX = newX + x;
				if (posX < 0 || posX >= BOARD_WIDTH || posY < 0 || posY >= BOARD_HEIGHT) {
					return false;
				}
				if (board[posY][posX] != null) {
					return false;
				}
			}
		}
		curPiece = newPiece;
		curX = newX;
		curY = newY;

		repaint();

		return true;
	}

	private void removeFullLines() {

		int numFullLines = 0;

		for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
			boolean lineIsFull = true;

			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (board[i][j] == null) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {

				numFullLines++;
				for (int k = i; k > 0; k--) {
					board[k] = board[k - 1];
				}
				i++;
			}
		}

		if (numFullLines > 0) {

			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setColor(null);
			repaint();
		}
	}

	private void drawSquarePart(Graphics g, int x, int y, Color color) {

		int dx = x * squareSize;
		int dy = y * squareSize;
		g.setColor(color);
		int brighterWidth = 4;
		int darkerWidth = 4;
		g.fillRect(dx + brighterWidth, dy + brighterWidth, squareSize - 1
				- darkerWidth, squareSize - 1 - darkerWidth);

		g.setColor(color.brighter());
		for (int i = 0; i < brighterWidth; i++) {
			g.drawLine(dx + i, dy + i, dx + squareSize - 1 - i, dy + i);
			g.drawLine(dx + i, dy + i, dx + i, dy + squareSize - 1 - i);
		}

		g.setColor(color.darker());
		for (int i = 0; i < darkerWidth; i++) {
			g.drawLine(dx + squareSize - 1 - i, dy + squareSize - 1 - i, dx
					+ squareSize - 1 - i, dy + i);
			g.drawLine(dx + squareSize - 1 - i, dy + squareSize - 1 - i, dx + i, dy
					+ squareSize - 1 - i);
		}

	}

	class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (!isStarted || curPiece.getColor() == null) {
				return;
			}

			int keycode = e.getKeyCode();

			if (keycode == 'p' || keycode == 'P') {
				pause();
				return;
			}

			if (isPaused)
				return;

			switch (keycode) {

			case KeyEvent.VK_LEFT:
				tryMove(curPiece, curX - 1, curY);
				break;

			case KeyEvent.VK_RIGHT:
				tryMove(curPiece, curX + 1, curY);
				break;

			case KeyEvent.VK_DOWN:
				tryMove(curPiece.rotateRight(), curX, curY);
				break;

			case KeyEvent.VK_UP:
				tryMove(curPiece.rotateLeft(), curX, curY);
				break;

			case KeyEvent.VK_SPACE:
				dropDown();
				break;

			case 'd':
				oneLineDown();
				break;

			case 'D':
				oneLineDown();
				break;
			}
		}
	}
}
