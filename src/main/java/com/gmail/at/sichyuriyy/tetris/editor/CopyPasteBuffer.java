package com.gmail.at.sichyuriyy.tetris.editor;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class CopyPasteBuffer {

	private static CopyPasteBuffer buffer;

	public static CopyPasteBuffer getBuffer() {
		if (buffer == null) {
			buffer = new CopyPasteBuffer();
		}
		return buffer;
	}

	private WorkComponent copy;

	private CopyPasteBuffer() {

	}

	public void setCopy(WorkComponent copy) {
		this.copy = copy.createClone();
	}

	public WorkComponent getCopy() {
		if (copy == null) {
			return null;
		}
		return copy.createClone();
	}

	public void clear() {
		copy = null;
	}

}
