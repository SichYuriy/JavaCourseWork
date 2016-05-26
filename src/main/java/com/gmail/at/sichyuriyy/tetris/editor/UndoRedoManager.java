package com.gmail.at.sichyuriyy.tetris.editor;

import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;

public class UndoRedoManager {

	private int undoSize;
	private int redoSize;
	private int undoIndex;
	private int redoIndex;
	private Command[] redoCommands;
	private Command[] undoCommands;
	private static final int MAX_SIZE = 10;

	private static UndoRedoManager manager;

	public static UndoRedoManager getUndoRedoManager() {
		if (manager == null) {
			manager = new UndoRedoManager();
		}
		return manager;
	}

	private UndoRedoManager() {
		undoSize = 0;
		redoSize = 0;
		undoIndex = 0;
		redoIndex = 0;
		redoCommands = new Command[MAX_SIZE];
		undoCommands = new Command[MAX_SIZE];
	}

	public void pushCommand(Command undoCommand, Command redoCommand) {
		undoIndex++;
		undoIndex %= MAX_SIZE;
		if (undoSize < MAX_SIZE)
			undoSize++;

		redoCommands[redoIndex] = redoCommand;
		undoCommands[undoIndex] = undoCommand;

		redoIndex++;
		redoIndex %= MAX_SIZE;
		redoSize = 0;
	}

	public void undo() {
		if (undoSize > 0) {
			undoCommands[undoIndex].execute();
			undoIndex--;
			if (undoIndex < 0)
				undoIndex = MAX_SIZE - 1;
			redoIndex--;
			if (redoIndex < 0)
				redoIndex = MAX_SIZE - 1;
			redoSize++;
			undoSize--;
		}
	}

	public void redo() {
		if (redoSize > 0) {
			redoCommands[redoIndex].execute();
			redoIndex++;
			redoIndex %= MAX_SIZE;
			undoIndex++;
			undoIndex %= MAX_SIZE;
			redoSize--;
			undoSize++;
		}
	}

}
