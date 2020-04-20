import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

public class ImageDisplay extends Frame {

	Color image[][];
	public int rows, cols;
	public int scaleFactor;

	public boolean keepRefreshing;

	ImageDisplay(int rows, int cols, int scaleFactor, String windowTitle) {

		this.rows = rows;
		this.cols = cols;
		this.scaleFactor = scaleFactor;

		image = new Color[rows][cols];

		keepRefreshing = true;

		setTitle(windowTitle);
		setSize(cols * scaleFactor, rows * scaleFactor);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void closeImageDisplay() {
		keepRefreshing = false;

		setVisible(false);
	}

	@Override
	public void paint(Graphics g) {

		while (keepRefreshing) {
			int newRow = 0;

			for (int row = 0; row < rows; row++) {
				int newColumn = 0;

				for (int column = 0; column < cols; column++) {
					Color pixel = image[row][column];

					// Set all pixels in this block to the same color
					for (int r = newRow; r < newRow + scaleFactor; r++) {
						for (int c = newColumn; c < newColumn + scaleFactor; c++) {

							// Set the color for this pixel
							g.setColor(pixel);

							// Draw the pixel by drawing a line with the same start and end point
							g.drawLine(c, r, c, r);
						}
					}

					newColumn += scaleFactor;
				}

				newRow += scaleFactor;
			}
		}
	}
}
