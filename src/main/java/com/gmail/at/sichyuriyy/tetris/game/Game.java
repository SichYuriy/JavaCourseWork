package com.gmail.at.sichyuriyy.tetris.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.at.sichyuriyy.tetris.FigureShape;

public class Game extends JFrame {
	
	private JLabel statusBar;
	private JLabel score;
	private PieceGenerator pieceGenerator;
	private Board gameArea;
	private PieceContainer pieceContainer;
	private JFrame mainApp;
	
	public Game(JFrame mainApp, ArrayList<FigureShape> shapes) {
		this.mainApp = mainApp;
		pieceGenerator = new PieceGenerator(shapes);
		initUI();
	}
	
	

	public PieceGenerator getPieceGenerator() {
		return pieceGenerator;
	}
	
	public JLabel getStatusBar() {
		return statusBar;
	}
	
	public PieceContainer getPieceContainer() {
		return pieceContainer;
	}
	
	private void initUI() {
		JPanel content = new JPanel();
		content.setLayout(null);
		content.setSize(500, 613);
		initStatusBar();
		intitPieceContainer();
		initGameArea();
	
		
		content.add(statusBar);
		content.add(gameArea);
		content.add(pieceContainer);
		content.add(score);
		content.setBackground(Color.LIGHT_GRAY);
		setTitle("Тетрис");
		setSize(500, 603);
		setResizable(false);
		add(content);
		gameArea.start();
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainApp.setVisible(true);
			}

		});
		
		setLocationRelativeTo(null);
		
	}
	
	private void initStatusBar() {
		score = new JLabel("Очки:");
		score.setBounds(330, 350, 100, 100);
		score.setFont(new Font("Font", Font.PLAIN, 30));
		statusBar = new JLabel("0");
		statusBar.setFont(new Font("Font", Font.PLAIN, 30));
		statusBar.setBounds(350, 400, 100, 100);
		statusBar.setFocusable(false);
	}
	
	private void intitPieceContainer() {
		pieceContainer = new PieceContainer(pieceGenerator);
		pieceContainer.setBounds(317, 50, 150, 150);
		pieceContainer.setBackground(Color.BLACK);
		pieceContainer.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	}
	
	private void initGameArea() {
		gameArea = new Board(this);
		gameArea.setBounds(0, 0, 300, 603);
	}
	
}
