package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandDestroyGroup implements Command {

	private WorkComponent component;
	
	public CommandDestroyGroup(WorkComponent component) {
		this.component = component;
	}
	
	@Override
	public void execute() {
		component.getWorkspace().destroyGroup(component);
	}
	
	

}
