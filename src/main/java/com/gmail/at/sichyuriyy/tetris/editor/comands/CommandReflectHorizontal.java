package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandReflectHorizontal implements Command {

	private WorkComponent component;
	
	public CommandReflectHorizontal(WorkComponent component) {
		this.component = component;
	}
	
	@Override
	public void execute() {
		component.getWorkspace().reflectHorizontal(component.hashCode());
	}

}
