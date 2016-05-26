package com.gmail.at.sichyuriyy.tetris.editor.tasks;

import java.util.ArrayList;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandClearSelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandDestroyGroup;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandMakeGroup;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandRotateLeft;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandRotateRight;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandSelectComponents;
import com.gmail.at.sichyuriyy.tetris.editor.comands.MacroCommand;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class TaskRotatingRight implements Task {

	@Override
	public void executeTask() {
		ArrayList<WorkComponent> selectedComponents = new ArrayList<WorkComponent>();
		for (WorkComponent component : SelectionBuffer.getSelectionBuffer()
				.getSelectedcomponents()) {
			selectedComponents.add(component);
		}
		if (selectedComponents.size() == 1) {
			WorkComponent component = selectedComponents.get(0);
			Command undo = new CommandRotateLeft(component);
			Command redo = new CommandRotateRight(component);
			UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
			redo.execute();
		} else if (selectedComponents.size() > 1) {
			Workspace workspace = selectedComponents.get(0).getWorkspace();
			WorkComponent composite = workspace.makeGroup(selectedComponents);
			workspace.rotateRight(composite.hashCode());
			workspace.destroyGroup(composite);
			SelectionBuffer.getSelectionBuffer().clear();
			for (WorkComponent component : selectedComponents) {
				SelectionBuffer.getSelectionBuffer().addComponent(component);
			}

			Command makeGroup = new CommandMakeGroup(selectedComponents);
			Command rotateLeft = new CommandRotateLeft(composite);
			Command destroyGroup = new CommandDestroyGroup(composite);
			Command clearSelection = new CommandClearSelectionBuffer();
			Command selectComponents = new CommandSelectComponents(selectedComponents);
			Command rotateRight = new CommandRotateRight(composite);
			MacroCommand redo = new MacroCommand();
			redo.addCommand(makeGroup);
			redo.addCommand(rotateRight);
			redo.addCommand(destroyGroup);
			redo.addCommand(clearSelection);
			redo.addCommand(selectComponents);

			MacroCommand undo = new MacroCommand();
			undo.addCommand(makeGroup);
			undo.addCommand(rotateLeft);
			undo.addCommand(destroyGroup);
			undo.addCommand(clearSelection);
			undo.addCommand(selectComponents);
			UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
		}

	}

}
