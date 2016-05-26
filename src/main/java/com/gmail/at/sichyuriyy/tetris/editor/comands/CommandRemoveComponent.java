package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandRemoveComponent implements Command {
	
	private WorkComponent component;
	
	
	public CommandRemoveComponent(WorkComponent component) {
		this.component = component;
	}

	@Override
	public void execute() {
		component.getWorkspace().removeComponent(component.hashCode());
		
	}

}
