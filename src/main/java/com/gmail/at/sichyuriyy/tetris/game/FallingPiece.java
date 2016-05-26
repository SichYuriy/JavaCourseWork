package com.gmail.at.sichyuriyy.tetris.game;

import java.awt.Color;

import com.gmail.at.sichyuriyy.tetris.FigureShape;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.MyClonable;

public class FallingPiece implements MyClonable<FallingPiece>{
	
	private Color color;
	private int width;
	private int height;
	private int [][]content;
	
	public FallingPiece(FigureShape shape, Color color) {
		this.width = shape.getWidth();
		this.height = shape.getHeight();
		this.content = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.content[i][j] = shape.getContent()[i][j];
			}
		}
		this.color = color;
	}
	
	private FallingPiece() {
		
	}
	
	public int [][]getContent() {
		return content;
	}
	
	public Color getColor() {
		return color;
	}
	
	public FallingPiece rotateLeft() {
		FallingPiece res = new FallingPiece();
		res.color = this.color;
		res.width = this.height;
		res.height = this.width;
		res.content = new int[res.height][res.width];
		for (int i = 0; i < res.height; i++) {
			for (int j = 0; j < res.width; j++) {
				res.content[i][j] = this.content[j][res.height - 1 - i];
			}
		}
		
		return res;
	}
	
	public FallingPiece rotateRight() {
		FallingPiece res = new FallingPiece();
		res.color = this.color;
		res.width = this.height;
		res.height = this.width;
		res.content = new int[res.height][res.width];
		for (int i = 0; i < res.height; i++) {
			for (int j = 0; j < res.width; j++) {
				res.content[i][j] = this.content[res.width - 1 - j][i];
			}
		}
		
		return res;
	}

	@Override
	public FallingPiece createClone() {
		FallingPiece res = new FallingPiece();
		res.width = width;
		res.height = height;
		res.content = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				res.content[i][j] = this.content[i][j];
			}
		}
		res.color = color;
		return res;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}
