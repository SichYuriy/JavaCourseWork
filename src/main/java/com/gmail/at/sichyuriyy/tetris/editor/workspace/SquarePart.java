package com.gmail.at.sichyuriyy.tetris.editor.workspace;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.gmail.at.sichyuriyy.tetris.editor.listeners.PopupMenuComponentListener;
import com.gmail.at.sichyuriyy.tetris.editor.listeners.ToolComponentMouseListener;
import com.gmail.at.sichyuriyy.tetris.editor.listeners.ToolComponentMouseMotionListener;

public class SquarePart extends JComponent implements WorkComponent {

	private Workspace workspace;
	private int componentPositionX;
	private int componentPositionY;
	private int positionXPx;
	private int positionYPx;
	private int componentWidthPx;
	private int componentHeightPx;
	private Color color;
	private static Color DEFAULT_COLOR = new Color(100, 100, 200);
	private boolean selectionFlag;

	private ToolComponentMouseListener mouseListener;
	private ToolComponentMouseMotionListener mouseMotionListener;
	private PopupMenuComponentListener popupMenuListener;

	private WorkComponent parent;
	private ArrayList<SquarePart> list;

	public SquarePart(Workspace workspace) {
		componentWidthPx = workspace.getSquareSizePx();
		componentHeightPx = workspace.getSquareSizePx();
		this.setSize(workspace.getSquareSizePx(), workspace.getSquareSizePx());
		this.workspace = workspace;
		color = DEFAULT_COLOR;
		parent = null;
		list = new ArrayList<SquarePart>();
		list.add(this);
		selectionFlag = false;
		mouseListener = new ToolComponentMouseListener(this);
		mouseMotionListener = new ToolComponentMouseMotionListener(this);
		popupMenuListener = new PopupMenuComponentListener(this);
		super.addMouseListener(mouseListener);
		super.addMouseMotionListener(mouseMotionListener);
		super.addMouseListener(popupMenuListener);

	}

	public void setPosition(int x, int y) {
		this.componentPositionX = x;
		this.componentPositionY = y;
		setPositionPx(x * componentWidthPx, y * componentHeightPx);

	}

	public void setPositionPx(int x, int y) {
		this.positionXPx = x;
		this.positionYPx = y;
		super.setBounds(x, y, this.componentWidthPx, this.componentHeightPx);
		workspace.revalidate();
		workspace.repaint();

	}

	public void rotatingLeft() {
		return;

	}

	public void rotatingRight() {
		return;

	}

	public WorkComponent add(WorkComponent component) {
		return new Composite(this, component);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		int brighterWidth = 4;
		int darkerWidth = 4;
		g.fillRect(brighterWidth, brighterWidth,
				componentWidthPx - 1 - darkerWidth, componentHeightPx - 1 - darkerWidth);

		g.setColor(color.brighter());
		for (int i = 0; i < brighterWidth; i++) {
			g.drawLine(i, i, componentWidthPx - 1 - i, i);
			g.drawLine(i, i, i, componentHeightPx - 1 - i);
		}

		g.setColor(color.darker());
		for (int i = 0; i < darkerWidth; i++) {
			g.drawLine(componentWidthPx - 1 - i, componentHeightPx - 1 - i,
					componentWidthPx - 1 - i, i);
			g.drawLine(componentWidthPx - 1 - i, componentHeightPx - 1 - i, i,
					componentHeightPx - 1 - i);
		}

	}

	public void highlight() {
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	public void unhighlight() {
		this.setBorder(null);
	}

	public void setColor(Color color) {
		this.color = color;
		repaint();
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void setSize(int width, int height) {
		this.componentWidthPx = width;
		this.componentHeightPx = height;
		super.setSize(width, height);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.positionXPx = x;
		this.positionYPx = y;
		this.componentWidthPx = width;
		this.componentHeightPx = height;
		super.setBounds(x, y, width, height);
	}

	public int getPositionY() {
		return componentPositionY;

	}

	public int getPositionX() {
		return componentPositionX;
	}

	public int getPositionXPx() {
		return positionXPx;
	}

	public int getPositionYPx() {
		return positionYPx;
	}

	public WorkComponent getParentComponent() {
		return parent;
	}

	public ArrayList<SquarePart> getSquareParts() {

		return list;
	}

	public void resetDefaultListeners(WorkComponent component) {
		mouseListener.setComponent(component);
		mouseMotionListener.setComponent(component);
		popupMenuListener.setComponent(component);
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public boolean isSelected() {
		return selectionFlag;
	}

	public void select() {

		selectionFlag = true;

	}

	public void unselect() {
		selectionFlag = false;

	}

	@Override
	public int getWidthC() {
		return 1;
	}

	@Override
	public int getHeightC() {
		return 1;
	}

	@Override
	public ArrayList<WorkComponent> getChildComponents() {
		return null;
	}

	@Override
	public void rotateLeft(int x, int y) {
		int relativePositionX = componentPositionX - x;
		int relativePositionY = componentPositionY - y;
		setPosition(relativePositionY + x, -relativePositionX + y);

	}

	@Override
	public void rotateRight(int x, int y) {
		int relativePositionX = componentPositionX - x;
		int relativePositionY = componentPositionY - y;
		setPosition(-relativePositionY + x, relativePositionX + y);
	}

	@Override
	public void reflectHorizontal() {
		return;
	}

	@Override
	public void reflectVertical() {
		return;
	}

	@Override
	public void reflectHorizontal(int oy) {
		int relativeX = componentPositionX - oy;
		setPosition(-relativeX + oy, componentPositionY);
	}

	@Override
	public void reflectVertical(int ox) {
		int relativeY = componentPositionY - ox;
		setPosition(componentPositionX, -relativeY + ox);
	}

	@Override
	public WorkComponent createClone() {
		SquarePart res = new SquarePart(workspace);
		res.color = color;
		res.setPosition(componentPositionX, componentPositionY);
		return res;
	}

}
