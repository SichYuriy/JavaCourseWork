package com.gmail.at.sichyuriyy.tetris.editor.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gmail.at.sichyuriyy.tetris.editor.tools.Tool;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class WorkspaceMouseListener implements MouseListener {
	
	private Workspace workspace;
	
	public WorkspaceMouseListener(Workspace workspace) {
		this.workspace = workspace;
	}

	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mousePressed(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.workspacenPressed(e);
		
	}

	public void mouseReleased(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.workspaceOnRelease(e);
	}

	public void mouseEntered(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.workspaceEntered(e);
		
	}

	public void mouseExited(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.workspaceExited(e);
		
	}
	
	private Tool getActiveTool() {
		return workspace.getToolBar().getActiveTool();
	}

}
