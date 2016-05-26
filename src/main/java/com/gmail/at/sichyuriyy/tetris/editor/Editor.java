package com.gmail.at.sichyuriyy.tetris.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.gmail.at.sichyuriyy.tetris.editor.comands.Command;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandAddComponent;
import com.gmail.at.sichyuriyy.tetris.editor.comands.CommandRemoveComponent;
import com.gmail.at.sichyuriyy.tetris.editor.listeners.WorkspaceMouseListener;
import com.gmail.at.sichyuriyy.tetris.editor.listeners.WorkspaceMouseMotionListener;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskMakingCopy;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskReflectionHorizontal;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskReflectionVertical;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskRotatingLeft;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskRotatingRight;
import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskSave;
import com.gmail.at.sichyuriyy.tetris.editor.tools.Brush;
import com.gmail.at.sichyuriyy.tetris.editor.tools.Eraser;
import com.gmail.at.sichyuriyy.tetris.editor.tools.Move;
import com.gmail.at.sichyuriyy.tetris.editor.tools.Paint;
import com.gmail.at.sichyuriyy.tetris.editor.tools.SelectionBorder;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Palette;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkComponent;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.WorkspaceToolBar;

public class Editor extends JFrame {

	private JFrame mainApp;
	private JPanel content;
	private Workspace workspace;
	private WorkspaceToolBar toolBar;
	private JMenuBar menuBar;
	private Palette palette;

	public Editor(JFrame mainApp) {
		this.mainApp = mainApp;
		SelectionBuffer.getSelectionBuffer().clear();
		CopyPasteBuffer.getBuffer().clear();
		initUI();
	}

	private void initUI() {
		setSize(700, 500);
		content = new JPanel();
		content.setLayout(new BorderLayout());
		menuBar = initMenu();
		toolBar = initToolBar();
		palette = initPalette();
		workspace = initWorkspace();
		menuBar = initMenu();
		palette.setWorksapce(workspace);
		super.setFocusable(true);
		content.add(toolBar, BorderLayout.WEST);
		content.add(palette, BorderLayout.EAST);
		content.add(workspace, BorderLayout.CENTER);
		super.setJMenuBar(menuBar);

		setTitle("Редактор фігурок");
		addKeyListener(new UndoRedoListener());
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainApp.setVisible(true);
			}

		});

		add(content);
		setLocationRelativeTo(null);

	}

	private JMenuBar initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu editorMenu = new JMenu("Редактор");
		JMenuItem saveMenuItem = new JMenuItem("Зберегти");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TaskSave(workspace).executeTask();
			}
		});
		JMenuItem quitMenuItem = new JMenuItem("Вихід");
		quitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				mainApp.setVisible(true);

			}
		});
		editorMenu.add(saveMenuItem);
		editorMenu.add(quitMenuItem);

		JMenu editMenu = new JMenu("Правка");
		JMenuItem undoMenuItem = new JMenuItem("Назад");
		undoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UndoRedoManager.getUndoRedoManager().undo();
			}
		});
		JMenuItem redoMenuItem = new JMenuItem("Вперед");
		redoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UndoRedoManager.getUndoRedoManager().redo();
			}
		});

		JMenuItem rotateLeftMenuItem = new JMenuItem("Повернути ліворуч");
		rotateLeftMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskRotatingLeft().executeTask();
			}

		});
		JMenuItem rotateRightMenuItem = new JMenuItem("Повернути праворуч");
		rotateRightMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskRotatingRight().executeTask();
			}

		});
		JMenuItem reflectHorizontalMenuItem = new JMenuItem(
				"Відобразити по горизонталі");
		reflectHorizontalMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskReflectionHorizontal().executeTask();
			}

		});
		JMenuItem reflectVerticalMenuItem = new JMenuItem(
				"Відобразити по вертикалі");
		reflectVerticalMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskReflectionVertical().executeTask();
			}

		});
		JMenuItem makeCopyMenuItem = new JMenuItem("Копіювати");
		makeCopyMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskMakingCopy().executeTask();

			}
		});

		JMenuItem pasteMenuItem = new JMenuItem("Вставити");
		pasteMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WorkComponent component = CopyPasteBuffer.getBuffer().getCopy();
				if (component == null) {
					return;
				}
				Command redo = new CommandAddComponent(component);
				Command undo = new CommandRemoveComponent(component);
				UndoRedoManager.getUndoRedoManager().pushCommand(undo, redo);
				redo.execute();

			}

		});
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		editMenu.addSeparator();
		editMenu.add(rotateLeftMenuItem);
		editMenu.add(rotateRightMenuItem);
		editMenu.add(reflectHorizontalMenuItem);
		editMenu.add(reflectVerticalMenuItem);
		editMenu.addSeparator();
		editMenu.add(makeCopyMenuItem);
		editMenu.add(pasteMenuItem);

		menuBar.add(editorMenu);
		menuBar.add(editMenu);

		return menuBar;
	}

	private Workspace initWorkspace() {
		Workspace w = new Workspace(600, 400, 25, toolBar, palette);
		w.addMouseListener(new WorkspaceMouseListener(w));
		w.addMouseMotionListener(new WorkspaceMouseMotionListener(w));
		w.addKeyListener(new UndoRedoListener());
		return w;
	}

	private WorkspaceToolBar initToolBar() {
		WorkspaceToolBar toolBar = new WorkspaceToolBar();

		toolBar.addTool(new Brush(), new ImageIcon("icons/brush.png"));
		toolBar.addTool(new Eraser(), new ImageIcon("icons/eraser.png"));
		toolBar.addTool(new Move(), new ImageIcon("icons/arrows.png"));
		toolBar.addTool(new Paint(), new ImageIcon("icons/paint.png"));
		toolBar.addTool(new SelectionBorder(),
				new ImageIcon("icons/allocation.png"));
		toolBar.setOrientation(JToolBar.VERTICAL);
		return toolBar;
	}

	private Palette initPalette() {
		Palette palette = new Palette();
		return palette;
	}

	class UndoRedoListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			int mod = e.getModifiers();
			if ((key == 'z' || key == 'Z') && ((mod & KeyEvent.CTRL_MASK) > 0)) {
				UndoRedoManager.getUndoRedoManager().undo();
			} else if ((key == 'y' || key == 'Y') && ((mod & KeyEvent.CTRL_MASK) > 0)) {
				UndoRedoManager.getUndoRedoManager().redo();
			}
		}

	}

}
