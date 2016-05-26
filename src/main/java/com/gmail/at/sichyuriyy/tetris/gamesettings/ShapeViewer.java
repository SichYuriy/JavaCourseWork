package com.gmail.at.sichyuriyy.tetris.gamesettings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.at.sichyuriyy.tetris.FigureShape;

public class ShapeViewer extends JPanel {

	private static final int MAX_WIDTH = 5;
	private static final int MAX_HEIGHT = 5;
	private ArrayList<FigureShape> shapes;
	private boolean[] selectedShapes;
	private int position;
	private JCheckBox checkBox;
	private int selectedShapesNum;
	private JButton buttonPrev;
	private JButton buttonNext;
	private JLabel status;
	private PictureOfShape top;
	private JPanel bottom;

	public ShapeViewer(ArrayList<FigureShape> s, JLabel stat) {
		this.status = stat;
		selectedShapesNum = 0;
		status.setText("0");
		shapes = new ArrayList<FigureShape>();
		for (FigureShape shape : s) {
			this.shapes.add(shape);
		}
		selectedShapes = new boolean[shapes.size()];
		checkBox = new JCheckBox();
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedShapes[position] = !selectedShapes[position];
				if (selectedShapes[position])
					selectedShapesNum++;
				else 
					selectedShapesNum--;
				status.setText("" + selectedShapesNum);
				repaint();
			}
		});
		
		buttonPrev = new JButton("<=");
		buttonPrev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (position > 0) {
					position--;
					checkBox.setSelected(selectedShapes[position]);
					repaint();
				}
				
			}
		});
		
		buttonNext = new JButton("=>");
		buttonNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (position < shapes.size() - 1) {
					position++;
					checkBox.setSelected(selectedShapes[position]);
					repaint();
				}
				
			}
		});
		setLayout(new BorderLayout());
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		bottom.setAlignmentX(FlowLayout.CENTER);
		bottom.add(buttonPrev);
		bottom.add(checkBox);
		bottom.add(buttonNext);
		top = new PictureOfShape();
		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		
	}
	
	public void mySetBackground(Color color) {
		super.setBackground(color);
		bottom.setBackground(color);
		top.setBackground(color);
		checkBox.setBackground(color);
		
	}
	class PictureOfShape extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int[][] pieceContent = shapes.get(position).getContent();
			for (int y = 0; y < pieceContent.length; y++) {
				for (int x = 0; x < pieceContent[y].length; x++) {
					if (pieceContent[y][x] == 1) {
						drawSquarePart(g, x, y);
					}
				}
			}
		}

		private void drawSquarePart(Graphics g, int x, int y) {
			Color color;
			if (selectedShapes[position])
				color = new Color(220, 50, 50);
			else
				color = new Color(160, 160, 160);
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
				g.drawLine(dx + squareWidth() - 1 - i, dy + squareHeight() - 1 - i, dx
						+ i, dy + squareHeight() - 1 - i);
			}
		}
	}

	private int squareWidth() {
		return (this.getWidth() - 10) / MAX_WIDTH;
	}

	private int squareHeight() {
		return (this.getHeight() - 10 - bottom.getHeight()) / MAX_HEIGHT;
	}
	
	public ArrayList<FigureShape> getSelectedShapes() {
		ArrayList<FigureShape> res = new ArrayList<FigureShape>();
		for (int i = 0; i < shapes.size(); i++) {
			if (selectedShapes[i]) {
				res.add(shapes.get(i));
			}
		}
		
		return res;
	}

}
