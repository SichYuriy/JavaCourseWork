package com.gmail.at.sichyuriyy.tetris.editor;

import java.util.ArrayList;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class SelectionBuffer {

	private ArrayList<WorkComponent> selectedComponents;
	private static SelectionBuffer buffer;

	private SelectionBuffer() {
		selectedComponents = new ArrayList<WorkComponent>();
	}

	public static SelectionBuffer getSelectionBuffer() {
		if (buffer == null) {
			buffer = new SelectionBuffer();
		}
		return buffer;
	}

	public void addComponent(WorkComponent component) {
		component.select();
		component.highlight();
		selectedComponents.add(component);
	}

	public void removeComponent(WorkComponent component) {
		component.unselect();
		component.unhighlight();
		selectedComponents.remove(component);
	}

	public void clear() {
		for (WorkComponent c : selectedComponents) {
			c.unselect();
			c.unhighlight();
		}
		selectedComponents.clear();
	}

	public ArrayList<WorkComponent> getSelectedcomponents() {
		return selectedComponents;
	}

}
