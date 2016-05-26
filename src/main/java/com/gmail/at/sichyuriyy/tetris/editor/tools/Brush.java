package com.gmail.at.sichyuriyy.tetris.editor.tools;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandAddComponent;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandRemoveComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.SquarePart;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class Brush extends ToolAdapter {
	
	@Override
	public void workspacenPressed(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return;
		}
		
		Workspace workspace = (Workspace)e.getComponent();
		int positionX = e.getX() / workspace.getSquareSizePx();
		int positionY = e.getY() / workspace.getSquareSizePx();
		
		
		WorkComponent component = new SquarePart(workspace);
		component.setColor(workspace.getPalette().getActiveColor());
		
		if (!workspace.isFieldBusy(positionX, positionY)) {
			Command undo = new CommandRemoveComponent(component);
			Command redo = new CommandAddComponent(component, positionX, positionY);
			UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
			redo.execute();
		}

		
	}

}
