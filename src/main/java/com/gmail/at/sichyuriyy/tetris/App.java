package com.gmail.at.sichyuriyy.tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gmail.at.sichyuriyy.tetris.editor.Editor;
import com.gmail.at.sichyuriyy.tetris.gamesettings.GameSettingsFrame;

public class App extends JFrame {

	private static final int BUTTONS_NUM = 2;
	private JButton editorButton;
	private JButton playButton;

	public App() {
		initUI();
	}

	private void initUI() {
		JPanel content = new JPanel();
		content.setLayout(null);

		initMenu(30, 20, 150, 75);

		content.setSize(220, 150);
		content.add(editorButton);
		content.add(playButton);
		content.setBackground(Color.LIGHT_GRAY);
		add(content);
		setSize(220, 150);
		setResizable(false);
		setTitle("Тетрис");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void initMenu(int x, int y, int width, int height) {
		JFrame thisApp = this;
		int gap = 5;
		int buttonWidth = width;
		int buttonHeight = (height - (BUTTONS_NUM - 1) * gap) / BUTTONS_NUM;
		playButton = new JButton("Грати");
		editorButton = new JButton("Відкрити редактор");
		playButton.setBounds(x, y, buttonWidth, buttonHeight);
		editorButton
				.setBounds(x, y + gap + buttonHeight, buttonWidth, buttonHeight);

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameSettingsFrame gameSettings = new GameSettingsFrame(thisApp);
				gameSettings.setVisible(true);
				setVisible(false);
			}
		});
		editorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Editor editor = new Editor(thisApp);
				editor.setVisible(true);
				thisApp.setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		App ex = new App();
		ex.setVisible(true);
	}

}
