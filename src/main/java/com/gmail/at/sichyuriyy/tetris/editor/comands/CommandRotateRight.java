package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandRotateRight implements Command {
private WorkComponent component;
	
	public CommandRotateRight(WorkComponent component) {
		this.component = component;
	}
	
	@Override
	public void execute() {
		component.getWorkspace().rotateRight(component.hashCode());
	}
}
