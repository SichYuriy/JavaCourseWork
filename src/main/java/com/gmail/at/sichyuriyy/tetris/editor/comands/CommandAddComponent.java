package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandAddComponent implements Command {

	private WorkComponent component;
	private int x;
	private int y;

	public CommandAddComponent(WorkComponent component) {
		this.component = component;
		x = 0;
		y = 0;

	}

	public CommandAddComponent(WorkComponent component, int x, int y) {
		this.component = component;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		component.getWorkspace().addComponent(component, x, y);

	}

}
