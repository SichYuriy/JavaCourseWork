package com.gmail.at.sichyuriyy.tetris.editor.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.gmail.at.sichyuriyy.tetris.editor.tools.Tool;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class WorkspaceMouseMotionListener implements MouseMotionListener {

	private Workspace workspace;
	
	public WorkspaceMouseMotionListener(Workspace workspace) {
		this.workspace = workspace;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.workspaceOnDrag(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.workspaceOnMove(e);
		
	}
	
	private Tool getActiveTool() {
		return workspace.getToolBar().getActiveTool();
	}

}
