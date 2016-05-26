package com.gmail.at.sichyuriyy.tetris;

public class FigureShape {

	private int width;
	private int height;
	private int[][] content;

	public FigureShape(int width, int height, int[][] content) {
		this.width = width;
		this.height = height;
		this.content = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.content[i][j] = content[i][j];
			}
		}
	}

	public boolean equals(FigureShape shape) {
		boolean res = false;
		if (this.width == shape.width && this.height == shape.height) {
			res = true;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (this.content[i][j] != shape.content[i][j]) {
						res = false;
					}
				}
			}
			if (res == true)
				return res;
			res = true;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (this.content[i][j] != shape.content[height - 1 - i][width - j - 1]) {

						res = false;
					}
				}
			}
			if (res == true)
				return res;
		}

		if (this.width == shape.height && this.height == shape.width) {
			int[][] shapeContent = new int[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					shapeContent[i][j] = shape.content[shape.height - 1 - j][i];
				}
			}

			res = true;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (this.content[i][j] != shapeContent[i][j]) {
						res = false;
					}
				}
			}
			if (res == true)
				return res;
			res = true;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (this.content[i][j] != shapeContent[height - 1 - i][width - j - 1]) {
						res = false;
					}
				}
			}
			if (res == true)
				return res;
		}
		return false;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getContent() {
		return content;
	}
}
