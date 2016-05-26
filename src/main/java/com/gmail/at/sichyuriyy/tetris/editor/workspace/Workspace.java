package com.gmail.at.sichyuriyy.tetris.editor.workspace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.gmail.at.sichyuriyy.tetris.editor.CopyPasteBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandAddComponent;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandRemoveComponent;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskDestroyingGroup;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskMakingCopy;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskMakingGroup;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskReflectionHorizontal;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskReflectionVertical;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskRotatingLeft;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskRotatingRight;

public class Workspace extends JPanel {

	private int squareSizePx;
	private int[][] content;
	private int saveAreaPositionX;
	private int saveAreaPositionY;
	private int saveAreaWidth;
	private int saveAreaHeight;
	private int widthC;
	private int heightC;
	private WorkspaceToolBar toolBar;
	private JPopupMenu componentPopupMenu;
	private Palette palette;
	private int selectionBorderBeginX;
	private int selectionBorderBeginY;
	private int selectionBorderEndX;
	private int selectionBorderEndY;
	private boolean selection;

	private HashMap<Integer, WorkComponent> components;

	public Workspace(int width, int height, int squareSize) {
		initComponentPopupMenu();
		super.setSize(width, height);
		this.squareSizePx = squareSize;
		this.widthC = width / squareSizePx;
		this.heightC = height / squareSizePx;
		this.content = new int[heightC][widthC];
		this.saveAreaPositionX = 0;
		this.saveAreaPositionY = 0;
		this.saveAreaWidth = Math.min(5, widthC);
		this.saveAreaHeight = Math.min(5, heightC);
		super.setBackground(Color.WHITE);
		super.setLayout(null);
		components = new HashMap<Integer, WorkComponent>();
		super.addComponentListener(new WorkspaceComponentListener());
		
	}

	public Workspace(int width, int height, int squareSize,
			WorkspaceToolBar toolBar, Palette palette) {
		this(width, height, squareSize);
		this.toolBar = toolBar;
		this.palette = palette;
	}

	public void setToolBar(WorkspaceToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public WorkspaceToolBar getToolBar() {
		return toolBar;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (int i = 0; i < super.getWidth(); i += squareSizePx) {

			g.drawLine(i, 0, i, super.getHeight() - 1);

		}
		for (int i = 0; i < super.getHeight(); i += squareSizePx) {
			g.drawLine(0, i, super.getWidth() - 1, i);
		}
	}

	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN.darker());
		g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND));
		g2d.drawRect(saveAreaPositionX * squareSizePx, saveAreaPositionY,
				(saveAreaPositionX + saveAreaWidth) * squareSizePx,
				(saveAreaPositionY + saveAreaHeight) * squareSizePx);
		g2d.setColor(Color.RED);
		float []dash = {2f, 0f, 2f};
		g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND, 1.0f, dash, 2f));
		if (selection) {
			int width = selectionBorderEndX - selectionBorderBeginX;
			int height = selectionBorderEndY - selectionBorderBeginY;
			g2d.drawRect(selectionBorderBeginX, selectionBorderBeginY,
					width, height);
		}
	}

	public void addComponent(WorkComponent component) {
		addComponent(component, 0, 0);
	}

	public void addComponent(WorkComponent component, int x, int y) {
		component.setPosition(x, y);

		this.addComponentToContent(component);
		for (SquarePart squarePart : component.getSquareParts()) {
			super.add(squarePart);
		}
		components.put(component.hashCode(), component);
		super.repaint();

	}

	public void removeComponent(int hash) {

		WorkComponent component = components.get(hash);
		if (component == null)
			return;
		if (component.isSelected()) {
			SelectionBuffer.getSelectionBuffer().removeComponent(component);
		}

		for (SquarePart squarePart : component.getSquareParts()) {
			super.remove(squarePart);
		}

		this.substractComponentFromContent(component);
		components.remove(hash);
		super.repaint();
	}

	public void moveComponent(int hash, int x, int y) {
		WorkComponent component = components.get(hash);
		if (component == null)
			return;
		this.substractComponentFromContent(component);

		component.setPosition(x, y);

		this.addComponentToContent(component);
	}

	public int getSquareSizePx() {
		return squareSizePx;
	}

	public WorkComponent makeGroup(ArrayList<WorkComponent> group) {
		SelectionBuffer.getSelectionBuffer().clear();
	
		for (WorkComponent component : group) {
			components.remove(component.hashCode());
			
		}
		
		WorkComponent composite = new Composite(group);
		components.put(composite.hashCode(), composite);
		SelectionBuffer.getSelectionBuffer().addComponent(composite);
		return composite;
	}

	public ArrayList<WorkComponent> destroyGroup(WorkComponent component) {
		components.remove(component.hashCode());
		ArrayList<WorkComponent> group = component.getChildComponents();
		if (group == null)
			return null;
		SelectionBuffer.getSelectionBuffer().clear();
		for (WorkComponent it : group) {
			it.resetDefaultListeners(it);
			components.put(it.hashCode(), it);
			
		}
		System.out.println();
		SelectionBuffer.getSelectionBuffer().addComponent(group.get(0));
		return group;
	}

	public int getWidthC() {
		return widthC;
	}

	public int getHeightC() {
		return heightC;
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		widthC = width / squareSizePx;
		heightC = height / squareSizePx;
	}

	public boolean isFieldBusy(int x, int y) {
		if (x < 0 || x >= widthC || y < 0 || y >= heightC) {
			return true;
		}

		if (content[y][x] > 0) {
			return true;
		} else {
			return false;
		}

	}

	private void updateContent() {
		for (int i = 0; i < heightC; i++) {
			for (int j = 0; j < widthC; j++) {
				content[i][j] = 0;
			}
		}
		components.forEach((Integer hash, WorkComponent component) -> {
			this.addComponentToContent(component);
		});
	}

	public void rotateLeft(int hash) {
		WorkComponent component = components.get(hash);
		if (component == null) {
			return;
		}
		this.substractComponentFromContent(component);
		component.rotatingLeft();
		this.addComponentToContent(component);
		revalidate();
		repaint();
	}

	public void rotateRight(int hash) {
		WorkComponent component = components.get(hash);
		if (component == null) {
			return;
		}
		this.substractComponentFromContent(component);
		component.rotatingRight();
		this.addComponentToContent(component);
		revalidate();
		repaint();
	}

	public void reflectHorizontal(int hash) {
		WorkComponent component = components.get(hash);
		if (component == null) {
			return;
		}

		this.substractComponentFromContent(component);
		component.reflectHorizontal();
		this.addComponentToContent(component);
		revalidate();
		repaint();
	}

	public void reflectVertical(int hash) {
		WorkComponent component = components.get(hash);
		if (component == null) {
			return;
		}

		this.substractComponentFromContent(component);
		component.reflectVertical();
		this.addComponentToContent(component);
		revalidate();
		repaint();
	}

	public int getSaveAreaWidth() {
		return saveAreaWidth;
	}

	public int getSaveAreaHeight() {
		return saveAreaHeight;
	}

	public int[][] getContent() {
		return content;
	}

	public int getSaveAreaPositionX() {
		return saveAreaPositionX;
	}

	public int getSaveAreaPositionY() {
		return saveAreaPositionY;
	}

	public void showContent() {
		for (int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[i].length; j++) {
				System.out.print(content[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("-------------");
	}

	private void initComponentPopupMenu() {
		componentPopupMenu = new JPopupMenu();

		JMenuItem makeGroup = new JMenuItem("Згрупувати");
		makeGroup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskMakingGroup().executeTask();
			}
		});

		JMenuItem destroyGroup = new JMenuItem("Розгрупувати");
		destroyGroup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskDestroyingGroup().executeTask();
			}
		});

		JMenuItem rotateLeft = new JMenuItem("Повернути ліворуч");
		rotateLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskRotatingLeft().executeTask();
			}

		});
		JMenuItem rotateRight = new JMenuItem("Повернути праворуч");
		rotateRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskRotatingRight().executeTask();
			}

		});
		JMenuItem reflectHorizontal = new JMenuItem("Відобразити по горизонталі");
		reflectHorizontal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskReflectionHorizontal().executeTask();
			}

		});
		JMenuItem reflectVertical = new JMenuItem("Відобразити по вертикалі");
		reflectVertical.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskReflectionVertical().executeTask();
			}

		});

		JMenuItem makeCopy = new JMenuItem("Копіювати");
		makeCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskMakingCopy().executeTask();

			}
		});

		JMenuItem paste = new JMenuItem("Вставити");
		paste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WorkComponent component = CopyPasteBuffer.getBuffer().getCopy();
				if (component == null) {
					return;
				}
				Command redo = new CommandAddComponent(component);
				Command undo = new CommandRemoveComponent(component);
				UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
				redo.execute();

			}

		});

		componentPopupMenu.add(makeGroup);
		componentPopupMenu.add(destroyGroup);
		componentPopupMenu.addSeparator();
		componentPopupMenu.add(rotateLeft);
		componentPopupMenu.add(rotateRight);
		componentPopupMenu.add(reflectHorizontal);
		componentPopupMenu.add(reflectVertical);
		componentPopupMenu.addSeparator();
		componentPopupMenu.add(makeCopy);
		componentPopupMenu.add(paste);
	}

	public JPopupMenu getComponentPopupMenu() {
		return componentPopupMenu;
	}

	private void substractComponentFromContent(WorkComponent component) {
		for (SquarePart square : component.getSquareParts()) {
			int positionX = square.getPositionX();
			int positionY = square.getPositionY();
			if (positionX >= 0 && positionX < widthC && positionY >= 0
					&& positionY < heightC) {
				content[positionY][positionX]--;
			}
		}
	}

	private void addComponentToContent(WorkComponent component) {
		for (SquarePart square : component.getSquareParts()) {
			int positionX = square.getPositionX();
			int positionY = square.getPositionY();
			if (positionX >= 0 && positionX < widthC && positionY >= 0
					&& positionY < heightC) {
				content[positionY][positionX]++;
			}
		}
	}

	class WorkspaceComponentListener extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent e) {
			widthC = e.getComponent().getWidth() / squareSizePx;
			heightC = e.getComponent().getHeight() / squareSizePx;
			content = new int[heightC][widthC];
			updateContent();

		}
	}

	public Palette getPalette() {
		return palette;
	}
	
	public void setSelection(boolean selection) {
		this.selection = selection;
	}
	
	public void setSelectionBegin(int x, int y) {
		selectionBorderBeginX = x;
		selectionBorderBeginY = y;
	}
	
	public void setSelectionEnd(int x, int y) {
		selectionBorderEndX = x;
		selectionBorderEndY = y;
	}
	
	public ArrayList<WorkComponent> getWorkComponents() {
		ArrayList<WorkComponent> res = new ArrayList<WorkComponent>();
		components.forEach((Integer hash, WorkComponent c) -> {
			res.add(c);
		});
		return res;
	}

}
