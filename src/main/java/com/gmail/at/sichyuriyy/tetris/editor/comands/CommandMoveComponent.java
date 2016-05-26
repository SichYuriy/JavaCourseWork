package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandMoveComponent implements Command {

	
	private WorkComponent component;
	private int x;
	private int y;
	
	public CommandMoveComponent(WorkComponent component, int x, int y) {
		this.component = component;
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public void execute() {
		component.getWorkspace().moveComponent(component.hashCode(), x, y);		
	}

}
