package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandRotateLeft implements Command {

	private WorkComponent component;
	
	public CommandRotateLeft(WorkComponent component) {
		this.component = component;
	}
	
	@Override
	public void execute() {
		component.getWorkspace().rotateLeft(component.hashCode());
	}

}
