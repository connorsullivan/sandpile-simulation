import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Sandpile {

	private int[][] pile;

	private int neighborCount;
	private int maxHeight;

	private ImageDisplay imageDisplay = null;

	private static final int rows = 75;
	private static final int cols = 101;

	private static final Color[] colors;

	static {
		colors = new Color[20];

		colors[0] = new Color(0, 0, 0);
		colors[1] = new Color(0, 255, 0);
		colors[2] = new Color(0, 0, 255);
		colors[3] = new Color(147, 112, 219);
		colors[4] = new Color(0, 255, 255);

		colors[5] = new Color(127, 127, 127);
		colors[6] = new Color(230, 230, 250);
		colors[7] = new Color(148, 0, 211);
		colors[8] = new Color(128, 0, 128);
		colors[9] = new Color(255, 0, 255);

		colors[10] = new Color(221, 160, 221);
		colors[11] = new Color(255, 192, 203);
		colors[12] = new Color(135, 38, 87);
		colors[13] = new Color(218, 112, 214);
		colors[14] = new Color(0, 0, 128);

		colors[15] = new Color(65, 105, 225);
		colors[16] = new Color(51, 161, 201);
		colors[17] = new Color(0, 255, 127);
		colors[18] = new Color(0, 201, 87);
		colors[19] = new Color(152, 251, 152);
	}

	public Sandpile(int neighborCount, int maxHeight) {

		if (neighborCount != 4 && neighborCount != 8) {
			throw new IllegalArgumentException("Number of neighbors must be either 4 or 8");
		}

		this.neighborCount = neighborCount;
		this.maxHeight = maxHeight;

		pile = new int[rows][cols];

		draw();
	}

	public void iterate(int iterations) {

		// Locate the center of the sand pile
		Point center = new Point((cols - 1) / 2, (rows - 1) / 2);

		for (int i = 0; i < iterations; i++) {

			// Increment the height of the position
			int height = ++pile[center.y][center.x];

			// Check if the height is over the limit
			if (height > maxHeight) {
				checkHeight();
			}

			// Update the graphics output
			draw();
		}
	}

	private void checkHeight() {

		while (true) {

			ArrayList<Point> avalanchePoints = new ArrayList<>();

			// Traverse the CA and find any avalanche points
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {

					// If a point is above the max height, it will avalanche
					if (pile[i][j] > maxHeight) {

						// Record this position
						avalanchePoints.add(new Point(i, j));

						// Avalanche that position (reduce the height)
						pile[i][j] -= neighborCount;
					}
				}
			}

			// If there weren't any avalanches in this iteration, break from the loop
			if (avalanchePoints.isEmpty()) {
				break;
			}

			// Distribute the sand from each avalanche to the surrounding neighbors
			for (Point p : avalanchePoints) {
				for (Point neighbor : getNeighbors(p)) {
					pile[neighbor.y][neighbor.x]++;
				}
			}
		}
	}

	private List<Point> getNeighbors(Point p) {

		int row = p.y;
		int col = p.x;

		ArrayList<Point> neighbors = new ArrayList<>();

		// Add the 4 neighbors included with both neighborhood types
		neighbors.add(new Point(row - 1, col));
		neighbors.add(new Point(row + 1, col));
		neighbors.add(new Point(row, col - 1));
		neighbors.add(new Point(row, col + 1));

		// Add the extra neighbors if needed for the neighborhood type
		if (neighborCount == 8) {
			neighbors.add(new Point(row - 1, col - 1));
			neighbors.add(new Point(row + 1, col + 1));
			neighbors.add(new Point(row - 1, col + 1));
			neighbors.add(new Point(row + 1, col - 1));
		}

		// Only return neighbors that are inbounds of the sand pile array
		Predicate<Point> isValidNeighbor = n -> n.y >= 0 && n.y < rows && n.x >= 0 && n.x < cols;

		return neighbors.stream().filter(isValidNeighbor).collect(Collectors.toList());
	}

	private void draw() {

		// Perform initial setup (if needed)
		if (imageDisplay == null) {
			imageDisplay = new ImageDisplay(rows, cols, 16, "Sandpile Simulation");
		}

		// Map the Integer array to the graphics output (number -> color)
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				imageDisplay.image[i][j] = colors[pile[i][j]];
			}
		}
	}
}
