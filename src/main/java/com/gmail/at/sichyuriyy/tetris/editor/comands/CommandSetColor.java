package com.gmail.at.sichyuriyy.tetris.editor.comands;

import java.awt.Color;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandSetColor implements Command {

	private WorkComponent component;
	private Color color;
	
	public CommandSetColor(WorkComponent component, Color color) {
		this.component = component;
		this.color = color;
	}
	
	@Override
	public void execute() {
		component.setColor(color);
		
	}
	
	

}
