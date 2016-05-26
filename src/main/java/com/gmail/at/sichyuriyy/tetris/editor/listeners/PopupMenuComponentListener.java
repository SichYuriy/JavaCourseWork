package com.gmail.at.sichyuriyy.tetris.editor.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.gmail.at.sichyuriyy.tetris.editor.SelectionBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;

public class PopupMenuComponentListener extends MouseAdapter implements WorkComponentListener {

	private WorkComponent component;

	public PopupMenuComponentListener(WorkComponent component) {
		
		this.component = component;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (!SwingUtilities.isRightMouseButton(e)) {
			return;
		}
		if (!component.isSelected()) {
			SelectionBuffer.getSelectionBuffer().clear();
			SelectionBuffer.getSelectionBuffer().addComponent(component);
		}

		component.getWorkspace().getComponentPopupMenu()
				.show(e.getComponent(), e.getX(), e.getY());
	}

	@Override
	public void setComponent(WorkComponent component) {
		this.component = component;
		
	}

}
