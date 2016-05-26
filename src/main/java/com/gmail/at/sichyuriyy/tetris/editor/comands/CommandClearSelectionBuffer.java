package com.gmail.at.sichyuriyy.tetris.editor.comands;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;

public class CommandClearSelectionBuffer implements Command {

	@Override
	public void execute() {
		SelectionBuffer.getSelectionBuffer().clear();
	}

}
