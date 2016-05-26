package com.gmail.at.sichyuriyy.tetris;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShapesBuffer {

	public static final String SHAPES_FILE = "shapes.shapes";

	private static final int NUM_OF_DEFAULT_SHAPES = 7;
	private ArrayList<FigureShape> shapes;

	private static ShapesBuffer buffer;

	private ShapesBuffer() {
		shapes = new ArrayList<FigureShape>();
		loadDefaultShapes();
		loadUserShapes();
	}

	public static ShapesBuffer getBuffer() {
		if (buffer == null) {
			buffer = new ShapesBuffer();
		}
		return buffer;
	}

	public boolean contains(FigureShape shape) {
		for (FigureShape it : shapes) {
			if (it.equals(shape)) {
				return true;
			}
		}
		return false;
	}

	private void loadDefaultShapes() {
		int[][] shape1Arr = { { 1, 1 }, { 1, 1 } };
		int[][] shape2Arr = { { 1, 1 }, { 1, 0 }, { 1, 0 } };
		int[][] shape3Arr = { { 1, 1 }, { 0, 1 }, { 0, 1 } };
		int[][] shape4Arr = { { 1, 1, 0 }, { 0, 1, 1 } };
		int[][] shape5Arr = { { 0, 1, 1 }, { 1, 1, 0 } };
		int[][] shape6Arr = { { 1, 0 }, { 1, 1 }, { 1, 0 } };
		int[][] shape7Arr = { { 1, 1, 1, 1 } };
		shapes.add(new FigureShape(2, 2, shape1Arr));
		shapes.add(new FigureShape(2, 3, shape2Arr));
		shapes.add(new FigureShape(2, 3, shape3Arr));
		shapes.add(new FigureShape(3, 2, shape4Arr));
		shapes.add(new FigureShape(3, 2, shape5Arr));
		shapes.add(new FigureShape(2, 3, shape6Arr));
		shapes.add(new FigureShape(4, 1, shape7Arr));

	}

	public void add(FigureShape shape) {
		if (contains(shape))
			return;
		shapes.add(shape);
	}

	public void loadUserShapes() {
		File file = new File(SHAPES_FILE);
		if (!file.exists())
			return;
		try {
			int width, height;
			int[][] content;
			Scanner in = new Scanner(file);
			while (in.hasNextInt()) {
				width = in.nextInt();
				height = in.nextInt();
				content = new int[height][width];
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						content[i][j] = in.nextInt();
					}
				}
				this.add(new FigureShape(width, height, content));
			}
			in.close();
		} catch (IOException e) {
			return;
		}

	}

	public ArrayList<FigureShape> getShapes() {
		return shapes;
	}

	public ArrayList<FigureShape> getDefaultShapes() {
		ArrayList<FigureShape> res = new ArrayList<FigureShape>();
		for (int i = 0; i < NUM_OF_DEFAULT_SHAPES; i++) {
			res.add(shapes.get(i));
		}
		return res;

	}

}
