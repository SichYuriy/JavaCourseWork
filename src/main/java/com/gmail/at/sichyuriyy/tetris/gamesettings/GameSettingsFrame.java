package com.gmail.at.sichyuriyy.tetris.gamesettings;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gmail.at.sichyuriyy.tetris.ShapesBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.Editor;
import com.gmail.at.sichyuriyy.tetris.game.Game;

public class GameSettingsFrame extends JFrame {

	private JFrame mainApp;
	private ShapeViewer shapeViewer;
	private JLabel selectedShapesNum;
	private JLabel selectedShapes;
	private JButton playDefault;
	private JButton playCustom;
	private JButton addShape;
	private static final int BUTTONS_NUM = 3;

	public GameSettingsFrame(JFrame mainApp) {
		this.mainApp = mainApp;

		intitUI();

	}

	private void intitUI() {
		
		
		JPanel content = new JPanel();
		content.setLayout(null);
		initSelectedShapes(15, 205, 150, 30);
		initShapeViewer(15, 15, 150, 185);
		
		
		initMenu(180, 20, 240, 100);
		
		content.add(shapeViewer);
		content.add(selectedShapes);
		content.add(selectedShapesNum);
		content.add(playDefault);
		content.add(playCustom);
		content.add(addShape);
		content.setBackground(Color.LIGHT_GRAY);
		content.setSize(440, 300);
		setSize(440, 300);
		add(content);
		
		setLocationRelativeTo(null);
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainApp.setVisible(true);
			}

		});
		setTitle("Нова гра");
		
		setResizable(false);
	}
	
	private void initShapeViewer(int x, int y, int width, int height) {
		shapeViewer = new ShapeViewer(ShapesBuffer.getBuffer().getShapes(),
				selectedShapesNum);
		shapeViewer.setBounds(x, y, width, height);
		shapeViewer.mySetBackground(Color.cyan);
		shapeViewer.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 200)));
	}
	
	private void initSelectedShapes(int x, int y, int width, int height) {
		Font font = (new Font("Font", Font.PLAIN, 17));
		selectedShapesNum = new JLabel("0");
		selectedShapesNum.setBounds(x + 5, y + 20, width - 5, height);
		selectedShapes = new JLabel("Вибрано фігурок:");
		selectedShapes.setBounds(x, y, width, height);
		selectedShapesNum.setFont(font);
		selectedShapes.setFont(font);
	}
	
	private void initMenu(int x, int y, int width, int height) {
		int gap = 5;
		int buttonWidth = width;
		int buttonHeight = (height - (BUTTONS_NUM - 1) * gap) / BUTTONS_NUM;
		playDefault = new JButton("Грати з стандартними фігурками");
		playCustom = new JButton("Грати з обраними фігурками");
		addShape = new JButton("Додати фігурку");
		playDefault.setBounds(x, y, buttonWidth, buttonHeight);
		playCustom.setBounds(x, y + gap + buttonHeight, buttonWidth, buttonHeight);
		addShape.setBounds(x, y + gap * 2 + buttonHeight * 2, buttonWidth, buttonHeight);
		
		playDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new Game(mainApp, ShapesBuffer.getBuffer().getDefaultShapes());
				game.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		playCustom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (shapeViewer.getSelectedShapes().size() == 0) {
					JOptionPane.showMessageDialog(shapeViewer, "Не вибрано жодної фігурки!",
		          "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Game game = new Game(mainApp, shapeViewer.getSelectedShapes());
				game.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		addShape.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Editor editor = new Editor(mainApp);
				editor.setVisible(true);
				setVisible(false);
				dispose();
				
			}
			
		});
		
	}

}
