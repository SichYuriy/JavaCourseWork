package com.gmail.at.sichyuriyy.tetris.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.gmail.at.sichyuriyy.tetris.FigureShape;

public class PieceGenerator {

	private ArrayList<FigureShape> shapes;
	private ArrayList<Color> colors;
	private FallingPiece piece;

	public PieceGenerator(ArrayList<FigureShape> shapes) {
		this.shapes = new ArrayList<FigureShape>(shapes.size());
		this.colors = new ArrayList<Color>();
		for (FigureShape shape : shapes) {
			this.shapes.add(shape);
		}
		addDefaultColors();
		changePiece();

	}

	public FallingPiece getPiece() {
		return piece.createClone();
	}

	public void changePiece() {
		Random rand = new Random(System.currentTimeMillis());
		
		int shapeIndex = rand.nextInt(shapes.size());
		int colorIndex = shapeIndex % colors.size();

		piece = new FallingPiece(shapes.get(shapeIndex), colors.get(colorIndex));
	}

	private void addDefaultColors() {
		colors.add(Color.ORANGE);
		colors.add(new Color(100, 100, 200));
		colors.add(new Color(100, 200, 100));
		colors.add(new Color(200, 50, 50));
		colors.add(new Color(200, 200, 100));
		colors.add(new Color(100, 200, 200));
		colors.add(new Color(200, 100, 200));
		colors.add(new Color(175, 175, 175));
	}

}
