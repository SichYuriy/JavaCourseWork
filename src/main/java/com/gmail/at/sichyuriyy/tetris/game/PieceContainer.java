package com.gmail.at.sichyuriyy.tetris.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PieceContainer extends JPanel {
	
	private final int MAX_WIDTH = 5;
	private final int MAX_HEIGHT = 5;
	private PieceGenerator pieceGenerator;
	
	public PieceContainer(PieceGenerator pieceGenerator) {
		this.pieceGenerator = pieceGenerator;
	}
	
	private int squareWidth() {
		return (super.getWidth()- 10) / MAX_WIDTH;
	}
	
	private int squareHeight() {
		return (super.getHeight() - 10) / MAX_HEIGHT;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int [][]pieceContent = pieceGenerator.getPiece().getContent();
		for (int y = 0; y < pieceContent.length; y++) {
			for (int x = 0; x < pieceContent[y].length; x++) {
				if (pieceContent[y][x] == 1) {
					drawSquarePart(g, x, y);
				}
			}
		}
	}
	
	private void drawSquarePart(Graphics g, int x, int y) {
		Color color = pieceGenerator.getPiece().getColor();
		g.setColor(color);
		int dx = x * squareWidth() + 5;
		int dy = y * squareHeight() + 5;
		int brighterWidth = 3;
		int darkerWidth = 3;
		g.fillRect(dx + brighterWidth, dy + brighterWidth, squareWidth() - 1
				- darkerWidth, squareHeight() - 1 - darkerWidth);

		g.setColor(color.brighter());
		for (int i = 0; i < brighterWidth; i++) {
			g.drawLine(dx + i, dy + i, dx + squareWidth() - 1 - i, dy + i);
			g.drawLine(dx + i, dy + i, dx + i, dy + squareHeight() - 1 - i);
		}

		g.setColor(color.darker());
		for (int i = 0; i < darkerWidth; i++) {
			g.drawLine(dx + squareWidth() - 1 - i, dy + squareHeight() - 1 - i, dx
					+ squareWidth() - 1 - i, dy + i);
			g.drawLine(dx + squareWidth() - 1 - i, dy + squareHeight() - 1 - i, dx + i, dy
					+ squareHeight() - 1 - i);
		}
	}

}
