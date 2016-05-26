package com.gmail.at.sichyuriyy.tetris.editor.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.gmail.at.sichyuriyy.tetris.editor.tools.Tool;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class ToolComponentMouseMotionListener implements MouseMotionListener, WorkComponentListener {
	
	private WorkComponent component;
	
	public ToolComponentMouseMotionListener(WorkComponent component) {
		this.component = component;
	}
	
	
	public void mouseDragged(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.componentOnDrag(component, e);
		
	}
	
	
	public void mouseMoved(MouseEvent e) {
		Tool activeTool = getActiveTool();
		if (activeTool == null)
			return;
		activeTool.componentOnMove(component, e);
		
	}
	
	private Tool getActiveTool() {
		return component.getWorkspace().getToolBar().getActiveTool();
	}
	
	@Override
	public void setComponent(WorkComponent component) {
		this.component = component;
	}

}
