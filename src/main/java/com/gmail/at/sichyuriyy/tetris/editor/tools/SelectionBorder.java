package com.gmail.at.sichyuriyy.tetris.editor.tools;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.SquarePart;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class SelectionBorder extends ToolAdapter {

	private int beginX;
	private int beginY;

	@Override
	public void componentPressed(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Workspace workspace = c.getWorkspace();
		workspace.setSelection(true);
		beginX = e.getX() + e.getComponent().getX();
		beginY = e.getY() + e.getComponent().getY();
		workspace.setSelectionBegin(e.getX(), e.getY());
		workspace.setSelectionEnd(e.getX(), e.getY());

		workspace.revalidate();
		workspace.repaint();

	}

	@Override
	public void componentOnRelease(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Workspace workspace = c.getWorkspace();
		workspace.setSelection(false);

		workspace.revalidate();
		workspace.repaint();

	}

	@Override
	public void componentOnDrag(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Workspace workspace = c.getWorkspace();
		int minX = Math.min(beginX, e.getX() + e.getComponent().getX());
		int maxX = Math.max(beginX, e.getX() + e.getComponent().getX());
		int minY = Math.min(beginY, e.getY() + e.getComponent().getY());
		int maxY = Math.max(beginY, e.getY() + e.getComponent().getY());
		selectComponents(workspace, minX, minY, maxX, maxY);
		workspace.setSelectionBegin(minX, minY);
		workspace.setSelectionEnd(maxX, maxY);

		workspace.revalidate();
		workspace.repaint();

	}

	@Override
	public void workspacenPressed(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Workspace workspace = (Workspace) e.getComponent();
		workspace.setSelection(true);
		beginX = e.getX();
		beginY = e.getY();
		workspace.setSelectionBegin(e.getX(), e.getY());
		workspace.setSelectionEnd(e.getX(), e.getY());

		workspace.revalidate();
		workspace.repaint();

	}

	@Override
	public void workspaceOnRelease(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Workspace workspace = (Workspace) e.getComponent();
		workspace.setSelection(false);

		workspace.revalidate();
		workspace.repaint();

	}

	@Override
	public void workspaceOnDrag(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Workspace workspace = (Workspace) e.getComponent();

		int minX = Math.min(beginX, e.getX());
		int maxX = Math.max(beginX, e.getX());
		int minY = Math.min(beginY, e.getY());
		int maxY = Math.max(beginY, e.getY());
		selectComponents(workspace, minX, minY, maxX, maxY);
		workspace.setSelectionBegin(minX, minY);
		workspace.setSelectionEnd(maxX, maxY);

		workspace.revalidate();
		workspace.repaint();

	}

	@Override
	public void workspaceOnMove(MouseEvent e) {

	}

	private void selectComponents(Workspace workspace, int x1, int y1, int x2,
			int y2) {
		SelectionBuffer.getSelectionBuffer().clear();
		int positionX1 = x1 / workspace.getSquareSizePx();
		int positionX2 = x2 / workspace.getSquareSizePx();
		int positionY1 = y1 / workspace.getSquareSizePx();
		int positionY2 = y2 / workspace.getSquareSizePx();
		for (WorkComponent c : workspace.getWorkComponents()) {
			for (SquarePart square : c.getSquareParts()) {
				if (square.getPositionX() >= positionX1
						&& square.getPositionX() <= positionX2
						&& square.getPositionY() >= positionY1
						&& square.getPositionY() <= positionY2) {
					SelectionBuffer.getSelectionBuffer().addComponent(c);
					break;
				}
			}
		}
	}
}
