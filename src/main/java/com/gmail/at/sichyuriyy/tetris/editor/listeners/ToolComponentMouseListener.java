package com.gmail.at.sichyuriyy.tetris.editor.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gmail.at.sichyuriyy.tetris.editor.tools.Tool;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class ToolComponentMouseListener implements MouseListener, WorkComponentListener {
	
	private WorkComponent component;
	
	
	public ToolComponentMouseListener(WorkComponent component) {
		this.component = component;
	}

	public void mouseClicked(MouseEvent e) {
			
	}

	public void mousePressed(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		
		activeTool.componentPressed(component, e);	
		
	}

	public void mouseReleased(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		
		activeTool.componentOnRelease(component, e);
	}

	public void mouseEntered(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		
		activeTool.componentEntered(component, e);
		
	}

	public void mouseExited(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		
		activeTool.componentExited(component, e);
		
	}
	
	private Tool getActiveTool() {
		return component.getWorkspace().getToolBar().getActiveTool();
	}
	
	@Override
	public void setComponent(WorkComponent component) {
		this.component = component;
	}

	

}
