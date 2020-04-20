public class Controller {

	public static void main(String[] args) {

		System.out.println("Sandpile Simulator\n");

		KeyboardInputClass kb = new KeyboardInputClass();

		// Get the neighborhood type for the CA
		String prompt = "Neighborhood types:\n\n";
		prompt += "1. Von Neumann (4 neighbors) (default)\n";
		prompt += "2. Moore (8 neighbors)\n\n";
		prompt += "Choose a neighborhood type: ";

		int neighborhoodType = kb.getInteger(prompt, 1, 2, 1);

		int neighborCount = 4;
		if (neighborhoodType == 2) {
			neighborCount = 8;
		}

		// Get the maximum height for the sand pile (before an avalanche occurs)
		int maxHeight = neighborCount - 1;

		prompt = "Specify maximum height of sandpile (" + (neighborCount - 1) + "-19): ";
		maxHeight = kb.getInteger(prompt, neighborCount - 1, 19, maxHeight);

		// Create the CA
		Sandpile pile = new Sandpile(neighborCount, maxHeight);

		// Do some iterations
		iterate(pile, kb);

		// Exit the program to close any open resources
		System.exit(0);
	}

	private static void iterate(Sandpile pile, KeyboardInputClass kb) {
		String prompt = "Enter number of iterations (Press ENTER to step through or 0 to quit): ";

		while (true) {
			int iterations = kb.getInteger(prompt, 0, Integer.MAX_VALUE, 1);

			if (iterations == 0) {
				break;
			}

			pile.iterate(iterations);
		}
	}
}
