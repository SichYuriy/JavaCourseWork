package com.gmail.at.sichyuriyy.tetris.editor.comands;

import java.util.ArrayList;

public class MacroCommand implements Command {

	private ArrayList<Command> commands;
	
	public MacroCommand() {
		commands = new ArrayList<Command>();
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	@Override
	public void execute() {
		for (Command command : commands) {
			command.execute();
		}
	}
	
	

}
