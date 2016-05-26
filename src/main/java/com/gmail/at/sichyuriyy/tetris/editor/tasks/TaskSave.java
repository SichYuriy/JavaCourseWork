package com.gmail.at.sichyuriyy.tetris.editor.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.gmail.at.sichyuriyy.tetris.FigureShape;
import com.gmail.at.sichyuriyy.tetris.ShapesBuffer;
import com.gmail.at.sichyuriyy.tetris.editor.workspace.Workspace;

public class TaskSave implements Task {

	private Workspace workspace;

	public TaskSave(Workspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public void executeTask() {

		int[][] shape = new int[workspace.getSaveAreaHeight()][workspace
				.getSaveAreaWidth()];
		int x = 0, y = 0;
		for (int i = workspace.getSaveAreaPositionY(); i < workspace
				.getSaveAreaHeight(); i++) {
			x = 0;
			for (int j = workspace.getSaveAreaPositionX(); j < workspace
					.getSaveAreaWidth(); j++) {
				if (i >= 0 && i < workspace.getHeightC() && j >= 0
						&& j < workspace.getWidthC()) {
					shape[y][x] = workspace.getContent()[i][j];
				}
				x++;
			}
			y++;
		}

		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		int size = 0;
		for (y = 0; y < shape.length; y++) {
			for (x = 0; x < shape[y].length; x++) {
				if (shape[y][x] > 0) {
					if (y > maxY)
						maxY = y;
					if (y < minY)
						minY = y;
					if (x > maxX)
						maxX = x;
					if (x < minX)
						minX = x;
					size++;
				}
			}
		}
		if (size == 0) {
			JOptionPane.showMessageDialog(workspace, "Зона збереження нової фігурки порожня!",
          "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int shapeWidth = maxX - minX + 1;
		int shapeHeight = maxY - minY + 1;
		int[][] shapeContent = new int[shapeHeight][shapeWidth];
		y = 0;
		for (int i = minY; i <= maxY; i++) {
			x = 0;
			for (int j = minX; j <= maxX; j++) {
				if (shape[i][j] > 0)
					shapeContent[y][x] = 1;
				x++;
			}
			y++;
		}

		FigureShape fShape = new FigureShape(shapeWidth, shapeHeight, shapeContent);

		if (ShapesBuffer.getBuffer().contains(fShape)) {
			JOptionPane.showMessageDialog(workspace, "Фігурка вже існує!",
          "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ShapesBuffer.getBuffer().add(fShape);
		File file = new File(ShapesBuffer.SHAPES_FILE);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter out = new FileWriter(file, true);
			out.write(shapeWidth + " " + shapeHeight + "\n");
			for (int i = 0; i < shapeHeight; i++) {
				for (int j = 0; j < shapeWidth; j++) {
					out.write(shapeContent[i][j] + " ");
				}
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
			return;
		}
		
		JOptionPane.showMessageDialog(workspace, "Фігурку збережено.",
        "", JOptionPane.INFORMATION_MESSAGE);

	}

}
