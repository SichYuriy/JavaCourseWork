package com.gmail.at.sichyuriyy.tetris.editor.tasks;

import java.util.ArrayList;

import com.gmail.at.sichyuriyy.tetris.editor.CopyPasteBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class TaskMakingCopy implements Task {

	@Override
	public void executeTask() {
		ArrayList<WorkComponent> selectedComponents = new ArrayList<WorkComponent>();
		for (WorkComponent component : SelectionBuffer.getSelectionBuffer()
				.getSelectedcomponents()) {
			selectedComponents.add(component);
		}
		if (selectedComponents.size() == 1) {
			CopyPasteBuffer.getBuffer().setCopy(selectedComponents.get(0));
		} else if (selectedComponents.size() > 1) {
			Workspace workspace = selectedComponents.get(0).getWorkspace();
			WorkComponent composite = workspace.makeGroup(selectedComponents);
			CopyPasteBuffer.getBuffer().setCopy(composite);
			workspace.destroyGroup(composite);
			SelectionBuffer.getSelectionBuffer().clear();
			for (WorkComponent c : selectedComponents) {
				SelectionBuffer.getSelectionBuffer().addComponent(c);
			}
		}

	}

}
