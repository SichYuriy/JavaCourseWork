package com.gmail.at.sichyuriyy.tetris.editor.workspace;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

public class Composite implements WorkComponent {
	
	private ArrayList<SquarePart> squareParts;
	private ArrayList<WorkComponent> childComponents;
	private int positionX;
	private int positionY;
	private int positionXPx;
	private int positionYPx;
	private int widthC;
	private int heightC;
	private Workspace workspace;
	private boolean isSelected;
	
	public Composite(Collection<WorkComponent> components) {
		workspace = components.iterator().next().getWorkspace();
		squareParts = new ArrayList<SquarePart>();
		childComponents = new ArrayList<WorkComponent>();
		for (WorkComponent it : components) {
			childComponents.add(it);
			for (SquarePart square : it.getSquareParts()) {
				squareParts.add(square);
			}
		}
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (SquarePart square: squareParts) {
			int x = square.getPositionX();
			int y = square.getPositionY();
			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
			
			square.resetDefaultListeners(this);
		}
		
		positionX = minX;
		positionY = minY;
		positionXPx = minX * workspace.getSquareSizePx();
		positionYPx = minY * workspace.getSquareSizePx();
		widthC = maxX - minX + 1;
		heightC = maxY - minY + 1;
		
	}
	
	public Composite(WorkComponent ...components) {
		workspace = components[0].getWorkspace();
		squareParts = new ArrayList<SquarePart>();
		childComponents = new ArrayList<WorkComponent>();
		for (WorkComponent it : components) {
			childComponents.add(it);
			for (SquarePart square : it.getSquareParts()) {
				squareParts.add(square);
			}
		}
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (SquarePart square: squareParts) {
			int x = square.getPositionX();
			int y = square.getPositionY();
			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
			
			square.resetDefaultListeners(this);
		}
		
		positionX = minX;
		positionY = minY;
		positionXPx = minX * workspace.getSquareSizePx();
		positionYPx = minY * workspace.getSquareSizePx();
		widthC = maxX - minX + 1;
		heightC = maxY - minY + 1;
	}

	@Override
	public void setPosition(int x, int y) {
		int shiftX = x - positionX;
		int shiftY = y - positionY;
		for (WorkComponent it : childComponents) {
			int itX = it.getPositionX() + shiftX;
			int itY = it.getPositionY() + shiftY;
			it.setPosition(itX, itY);
		}
		positionX = x;
		positionY = y;
		positionXPx = x * workspace.getSquareSizePx();
		positionYPx = y * workspace.getSquareSizePx();
		
	}

	@Override
	public void setPositionPx(int x, int y) {
		int shiftX = x - positionXPx;
		int shiftY = y - positionYPx;
		for (WorkComponent it : childComponents) {
			int itX = it.getPositionXPx() + shiftX;
			int itY = it.getPositionYPx() + shiftY;
			it.setPositionPx(itX, itY);
		}
		positionXPx = x;
		positionYPx = y;
		
		
	}

	@Override
	public int getPositionY() {
		return positionY;
	}

	@Override
	public int getPositionX() {
		return positionX;
	}

	@Override
	public void rotatingLeft() {
		int oldX = positionX;
		int oldY = positionY;
		int x = positionX + (widthC - 1) / 2;
		int y = positionY + (heightC - 1) / 2;
		rotateLeft(x, y);
		setPosition(oldX, oldY);
		
	}

	@Override
	public void rotatingRight() {
		int oldX = positionX;
		int oldY = positionY;
		int x = positionX + (widthC - 1)  / 2;
		int y = positionY + (heightC - 1) / 2;
		rotateRight(x, y);
		setPosition(oldX, oldY);
		
	}

	@Override
	public WorkComponent add(WorkComponent component) {
		return new Composite(this, component);
	}

	@Override
	public void highlight() {
		for (WorkComponent it : childComponents) {
			it.highlight();
		}
		
	}

	@Override
	public void unhighlight() {
		for (WorkComponent it : childComponents) {
			it.unhighlight();
		}
	}

	@Override
	public void setColor(Color color) {
		for (WorkComponent it : childComponents) {
			it.setColor(color);
		}
	}

	@Override
	public ArrayList<SquarePart> getSquareParts() {
		return squareParts;
	}

	@Override
	public void resetDefaultListeners(WorkComponent component) {
		for (SquarePart square : squareParts) {
			square.resetDefaultListeners(component);
		}
		
	}

	@Override
	public Workspace getWorkspace() {
		return workspace;
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void select() {
		isSelected = true;
		
	}

	@Override
	public void unselect() {
		isSelected = false;
		
	}

	@Override
	public int getWidthC() {
		return widthC;
	}

	@Override
	public int getHeightC() {
		return heightC;
	}

	@Override
	public ArrayList<WorkComponent> getChildComponents() {
		return childComponents;
	}

	@Override
	public int getPositionYPx() {
		return positionYPx;
	}

	@Override
	public int getPositionXPx() {
		return positionXPx;
	}

	@Override
	public void rotateLeft(int x, int y) {
		int relativePositionX = positionX - x;
		int relativePositionY = positionY - y;
		positionX = relativePositionY + x;
		positionY = -(relativePositionX + widthC - 1) + y;
		positionXPx = positionX * workspace.getSquareSizePx();
		positionYPx = positionY * workspace.getSquareSizePx();
		int p = widthC;
		widthC = heightC;
		heightC = p;
		for (WorkComponent c : childComponents) {
			c.rotateLeft(x, y);
		}
		
	}

	@Override
	public void rotateRight(int x, int y) {
		int relativePositionX = positionX - x;
		int relativePositionY = positionY - y;
		positionX = -(relativePositionY + heightC - 1) + x;
		positionY = relativePositionX + y;
		positionXPx = positionX * workspace.getSquareSizePx();
		positionYPx = positionY * workspace.getSquareSizePx();
		int p = widthC;
		widthC = heightC;
		heightC = p;
		for (WorkComponent c : childComponents) {
			c.rotateRight(x, y);
		}
	}

	@Override
	public void reflectHorizontal() {
		int oldX = positionX;
		int oldY = positionY;
		int oy = positionX;
		reflectHorizontal(oy);
		setPosition(oldX, oldY);
		
	}

	@Override
	public void reflectVertical() {
		int oldX = positionX;
		int oldY = positionY;
		int ox = positionY;
		reflectVertical(ox);
		setPosition(oldX, oldY);
		
	}

	@Override
	public void reflectHorizontal(int oy) {
		int relativeX = positionX - oy;
		positionX = -(relativeX + widthC - 1) + oy;
		positionXPx = positionX * workspace.getSquareSizePx();
		for (WorkComponent it : childComponents) {
			it.reflectHorizontal(oy);
		}
		
	}

	@Override
	public void reflectVertical(int ox) {
		int relativeY = positionY - ox;
		positionY = -(relativeY + heightC - 1) + ox;
		positionYPx = positionX * workspace.getSquareSizePx();
		for (WorkComponent it : childComponents) {
			it.reflectVertical(ox);
		}
		
	}

	@Override
	public WorkComponent createClone() {
		ArrayList<WorkComponent> childComponentsClones = new ArrayList<WorkComponent>();
		for (WorkComponent component : childComponents) {
			childComponentsClones.add(component.createClone());
		}
		WorkComponent res = new Composite(childComponentsClones);
		
		return res;
	}

}
