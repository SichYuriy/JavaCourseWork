package com.gmail.at.sichyuriyy.tetris.editor.workspace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.at.sichyuriyy.tetris.editor.tasks.TaskSave;

public class Palette extends JPanel {

	private Color activeColor;
	private Workspace workspace;
	private Color[] colors;

	public Palette() {
		int boxSize = 25;
		
		super.setLayout(new BorderLayout());
		JPanel paletteContent = new JPanel();
		paletteContent.setLayout(new GridLayout(5, 2, 5, 5));
		super.setAlignmentY(0f);

		colors = new Color[8];
		colors[0] = new Color(75, 75, 75);
		colors[1] = Color.ORANGE;
		colors[2] = new Color(100, 100, 200);
		colors[3] = new Color(100, 200, 100);
		colors[4] = new Color(200, 50, 50);
		colors[5] = new Color(200, 200, 100);
		colors[6] = new Color(100, 200, 200);
		colors[7] = new Color(200, 100, 200);

		JPanel activeColorBox = new JPanel();
		activeColorBox.setBorder(BorderFactory.createRaisedBevelBorder());
		paletteContent.add(activeColorBox);
		paletteContent.add(new JLabel(""));
		for (int i = 0; i < colors.length; i++) {
			final Color tempColor = colors[i];
			JPanel colorBox = new JPanel();
			colorBox.setBackground(tempColor);
			colorBox.setBorder(BorderFactory.createLoweredBevelBorder());
			colorBox.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					activeColor = tempColor;
					activeColorBox.setBackground(tempColor);
				}
			});
			colorBox.setPreferredSize(new Dimension(boxSize, boxSize));
			paletteContent.add(colorBox);
		}

		super.add(paletteContent, BorderLayout.NORTH);
		JButton save = new JButton("Зберегти");
		save.setPreferredSize(new Dimension(55, 55));
		save.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TaskSave(workspace).executeTask();

			}
		});
		save.setFocusable(false);
		super.add(save, BorderLayout.SOUTH);
		super.add(new JPanel(), BorderLayout.CENTER);

		activeColor = colors[0];
		activeColorBox.setBackground(colors[0]);

	}
	
	public void setWorksapce(Workspace workspace) {
		this.workspace = workspace;
	}

	public Color getActiveColor() {
		return activeColor;
	}

}
