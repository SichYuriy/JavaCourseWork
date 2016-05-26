package com.gmail.at.sichyuriyy.tetris.editor.tools;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandMoveComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class Move extends ToolAdapter {
	
	private int startPositionX;
	private int startPositionY;
	private int startMousePositionX;
	private int startMousePositionY;
	private boolean isDragged;

	@Override
	public void componentPressed(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e) )
			return;
		
		startPositionX = c.getPositionXPx();
		startPositionY = c.getPositionYPx();
		startMousePositionX = e.getX();
		startMousePositionY = e.getY();
		
		
		int modifires = e.getModifiers();
		if ((modifires & MouseEvent.CTRL_MASK) > 0) {
			if (c.isSelected()) {
				SelectionBuffer.getSelectionBuffer().removeComponent(c);
			} else {
				SelectionBuffer.getSelectionBuffer().addComponent(c);
			}
		} else {
			SelectionBuffer.getSelectionBuffer().clear();
			SelectionBuffer.getSelectionBuffer().addComponent(c);
		}
		
		
	}

	@Override
	public void componentOnDrag(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		if (!isDragged) {
			SelectionBuffer.getSelectionBuffer().clear();
			SelectionBuffer.getSelectionBuffer().addComponent(c);
			isDragged = true;
		}
		c.setPositionPx(startPositionX + (e.getX() - startMousePositionX)
				, startPositionY + (e.getY() - startMousePositionY));
		startPositionX += e.getX() - startMousePositionX;
		startPositionY += e.getY() - startMousePositionY;
		
		
	}

	@Override
	public void componentOnRelease(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		int squareSizePx = c.getWorkspace().getSquareSizePx();
		int positionX = (startPositionX + squareSizePx / 2) / squareSizePx;
		int positionY = (startPositionY + squareSizePx / 2) / squareSizePx;
		if (positionX != c.getPositionX() || positionY != c.getPositionY()) {
			Command redo = new CommandMoveComponent(c, positionX, positionY);
			Command undo = new CommandMoveComponent(c, c.getPositionX(), c.getPositionY());
			UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
			redo.execute();
		} else {
			c.setPosition(c.getPositionX(), c.getPositionY());
		}
		isDragged = false;
		
	}
	
	@Override
	public void workspacenPressed(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		SelectionBuffer.getSelectionBuffer().clear();
		
	}
	
}
