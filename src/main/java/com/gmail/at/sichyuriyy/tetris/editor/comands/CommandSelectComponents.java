package com.gmail.at.sichyuriyy.tetris.editor.comands;

import java.util.ArrayList;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandSelectComponents implements Command {

	private ArrayList<WorkComponent> components;

	public CommandSelectComponents(WorkComponent component) {
		components = new ArrayList<WorkComponent>();
		components.add(component);
	}

	public CommandSelectComponents(ArrayList<WorkComponent> components) {
		this.components = new ArrayList<WorkComponent>();
		for (WorkComponent component : components) {
			this.components.add(component);
		}
	}

	@Override
	public void execute() {
		for (WorkComponent component : components) {
			SelectionBuffer.getSelectionBuffer().addComponent(component);
		}

	}

}
