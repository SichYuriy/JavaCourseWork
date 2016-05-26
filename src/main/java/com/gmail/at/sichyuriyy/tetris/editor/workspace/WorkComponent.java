package com.gmail.at.sichyuriyy.tetris.editor.workspace;

import java.awt.Color;
import java.util.ArrayList;

public interface WorkComponent extends MyClonable<WorkComponent> {
	
	public ArrayList<WorkComponent> getChildComponents();
	
	public void setPosition(int x, int y);
	public void setPositionPx(int x, int y);
	public int getPositionY();
	public int getPositionX();
	public int getPositionYPx();
	public int getPositionXPx();
	public void rotatingLeft();
	public void rotatingRight();
	public void rotateLeft(int x, int y);
	public void rotateRight(int x, int y);
	public WorkComponent add(WorkComponent component);
	public void highlight();
	public void unhighlight();
	public void setColor(Color color);
	public ArrayList<SquarePart> getSquareParts();
	public void resetDefaultListeners(WorkComponent component);
	public Workspace getWorkspace();
	public int getWidthC();
	public int getHeightC();
	
	public void reflectHorizontal();
	public void reflectVertical();
	public void reflectHorizontal(int oy);
	public void reflectVertical(int ox);
	
	public boolean isSelected();
	public void select();
	public void unselect();

}
