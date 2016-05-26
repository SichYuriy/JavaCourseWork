package com.gmail.at.sichyuriyy.tetris.editor.tasks;

import java.util.ArrayList;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.UndoRedoManager;
import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandDestroyGroup;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandMakeGroup;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class TaskMakingGroup implements Task{

	@Override
	public void executeTask() {
		
		ArrayList<WorkComponent> selectedComponents = new ArrayList<WorkComponent>();
		for (WorkComponent component : SelectionBuffer.getSelectionBuffer()
				.getSelectedcomponents()) {
			selectedComponents.add(component);
		}
		if (selectedComponents.size() > 1) {
			Command redo = new CommandMakeGroup(selectedComponents);
			WorkComponent res = selectedComponents.get(0).getWorkspace()
					.makeGroup(selectedComponents);
			Command undo = new CommandDestroyGroup(res);
			UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
		}
		
	}

}
