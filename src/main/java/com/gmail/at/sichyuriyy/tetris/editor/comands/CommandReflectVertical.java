package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandReflectVertical implements Command {

	private WorkComponent component;
	
	public CommandReflectVertical(WorkComponent component) {
		this.component = component;
	}
	
	@Override
	public void execute() {
		component.getWorkspace().reflectVertical(component.hashCode());
	}

}
