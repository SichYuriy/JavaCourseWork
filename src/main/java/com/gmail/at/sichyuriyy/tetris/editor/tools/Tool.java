package com.gmail.at.sichyuriyy.tetris.editor.tools;

import java.awt.event.MouseEvent;

import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public interface Tool {
	
	public void componentPressed(WorkComponent c, MouseEvent e);
	public void componentOnRelease(WorkComponent c, MouseEvent e);
	public void componentEntered(WorkComponent c, MouseEvent e);
	public void componentExited(WorkComponent c, MouseEvent e);
	public void componentOnDrag(WorkComponent c, MouseEvent e);
	public void componentOnMove(WorkComponent c, MouseEvent e);
	
	public void workspacenPressed(MouseEvent e);
	public void workspaceOnRelease(MouseEvent e);
	public void workspaceEntered(MouseEvent e);
	public void workspaceExited(MouseEvent e);
	public void workspaceOnDrag(MouseEvent e);
	public void workspaceOnMove(MouseEvent e);


}
