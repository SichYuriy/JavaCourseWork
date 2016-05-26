package com.gmail.at.sichyuriyy.tetris.editor.tools;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandAddComponent;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandRemoveComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class Eraser extends ToolAdapter {

	@Override
	public void componentPressed(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		int x = c.getPositionX();
		int y = c.getPositionY();
		Command undo = new CommandAddComponent(c, x, y);
		Command redo = new CommandRemoveComponent(c);
		UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
		redo.execute();
	}

}
