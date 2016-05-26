package com.gmail.at.sichyuriyy.tetris.editor.tools;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandSetColor;
import com.gmail.at.sichyuriyy.tetris.editor.comands.MacroCommand;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.SquarePart;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class Paint extends ToolAdapter {

	@Override
	public void componentPressed(WorkComponent c, MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Color color = c.getWorkspace().getPalette().getActiveColor();
		Command redo = new CommandSetColor(c, color);
		MacroCommand undo = new MacroCommand();
		for (SquarePart it : c.getSquareParts()) {
			undo.addCommand(new CommandSetColor(it, it.getColor()));
		}
		UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
		redo.execute();

	}

}
