package com.gmail.at.sichyuriyy.tetris.editor.workspace;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.gmail.at.sichyuriyy.tetris.editor.tools.Tool;

public class WorkspaceToolBar extends JToolBar {

	private ArrayList<Tool> tools;
	private ArrayList<JToggleButton> buttons;
	private Tool activeTool;

	public WorkspaceToolBar() {
		tools = new ArrayList<Tool>();
		buttons = new ArrayList<JToggleButton>();
		activeTool = null;
		super.setSize(100, 100);
	}

	public Tool getActiveTool() {
		return activeTool;
	}

	public void addTool(Tool tool, String text) {
		tools.add(tool);
		JToggleButton btn = new JToggleButton(text);
		btn.setMargin(new Insets(5, 5, 5, 5));
		btn.setFocusable(false);
		btn.addMouseListener(new ToolBarButtonMouseListener(tool));
		add(btn);
		buttons.add(btn);
	}

	public void addTool(Tool tool, ImageIcon icon) {
		tools.add(tool);
		JToggleButton btn = new JToggleButton(icon);
		btn.setMargin(new Insets(5, 5, 5, 5));
		btn.setFocusable(false);
		btn.addMouseListener(new ToolBarButtonMouseListener(tool));
		
		add(btn);
		buttons.add(btn);
	}

	class ToolBarButtonMouseListener extends MouseAdapter {

		private Tool tool;

		public ToolBarButtonMouseListener(Tool tool) {
			this.tool = tool;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			for (JToggleButton btn : buttons) {
					btn.getModel().setSelected(false);
			}
			activeTool = tool;
		}

	}

}
