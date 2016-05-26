package com.gmail.at.sichyuriyy.tetris.editor.comands;

import java.util.ArrayList;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CommandMakeGroup implements Command {

	private ArrayList<WorkComponent> group;
	
	public CommandMakeGroup(ArrayList<WorkComponent> group) {
		this.group = group;
	}
	
	@Override
	public void execute() {
		group.get(0).getWorkspace().makeGroup(group);
	}

}
