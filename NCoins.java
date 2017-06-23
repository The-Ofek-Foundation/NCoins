public class NCoins {
	public static void main(String... pumpkins) {
		run(Integer.parseInt(pumpkins[0]));
	}

	public static void run(int numCoins) {
		Coins coins = new Coins(numCoins);
		Balance balance = new Balance(coins);
		int[][] possiblePermuations = generatePossiblePermuations(numCoins);
		int minimaxDepth = minimax(coins, balance, possiblePermuations, 0,
			Integer.MAX_VALUE);

		System.out.println("MINIMAX DEPTH FOR " + numCoins + " COINS IS " + minimaxDepth);
	}

	private static int[][] generatePossiblePermuations(int numCoins) {
		int[][] possiblePermuations = new int[100000][numCoins];
		possiblePermuations[0][0] = 1;
		for (int i = 1; i < (numCoins + 1) / 2; i++)
			addPermutations(possiblePermuations, i);
		return possiblePermuations;
	}

	private static void addPermutations(int[][] possiblePermuations, int num) {
		int[] perm = new int[possiblePermuations[0].length];
		for (int i = 0; i < num * 2; i++)
			if (i < num)
				perm[i] = 1;
			else perm[i] = 2;
		do {
			addPerm(possiblePermuations, perm);
		} while (permute(perm));
	}

	private static void addPerm(int[][] possiblePermuations, int[] perm) {
		for (int i = 0; i < possiblePermuations[0].length; i++) {
			possiblePermuations[possiblePermuations[0][0]][i] = perm[i];
			// System.out.print(perm[i] + " ");
		}
		// System.out.println();
		possiblePermuations[0][0]++;
	}

	private static boolean permute(int[] perm) {
		for (int i = perm.length - 2; i >= 0; i--)
			if (perm[i] != 0 && perm[i + 1] == 0) {
				perm[i + 1] = perm[i];
				perm[i] = 0;
				int index = i + 2;
				for (int a = index; a < perm.length; a++)
					if (perm[a] != 0) {
						perm[index] = perm[a];
						if (a != index)
							perm[a] = 0;
						index++;
					}
				return true;
			}

		// reset

		int n;
		for (n = 0; perm[n] == 0; n++);
		for (int i = 0; i + n < perm.length; i++) {
			perm[i] = perm[i + n];
			perm[i + n] = 0;
		}

		// start swapping

		for (int i = perm.length - 2; i > 0; i--)
			if (perm[i] == 1 && perm[i + 1] == 2) {
				perm[i + 1] = 1;
				perm[i] = 2;
				int index = i + 2;
				for (int a = index; a < perm.length; a++)
					if (perm[a] == 1) {
						perm[index] = 1;
						if (a != index)
							perm[a] = 2;
						index++;
					}
				return true;
			}

		return false;
	}

	private static int minimax(Coins coins, Balance balance,
		int[][] possiblePermutations, int depth, int cutoff) {
		// System.out.println(depth);
		// System.out.println(coins);
		if (coins.over() || depth > cutoff)
			return depth;
		int minDepth = Integer.MAX_VALUE;

		for (int i = 1; i < possiblePermutations[0][0]; i++) {
			balance.set(possiblePermutations[i]);
			if (balance.usefulWeighing()) {
				int m = -1;
				for (int a = 0; a < 3; a++) {
					// for (int z : possiblePermutations[i])
					// 	System.out.print(z + " ");
					// System.out.println();
					balance.weighCoins(a);
					int d = minimax(coins, balance, possiblePermutations,
						depth + 1, cutoff);
					if (d > m)
						m = d;
					coins.undoStates();
					if (a < 2)
						balance.set(possiblePermutations[i]);
				}
				if (m < minDepth)
					minDepth = m;
				// if (minDepth == 2) {
				// 	System.out.println("!!!!!!!!!!!");
				// 	System.out.println(depth + " " + i);
				// 	System.out.println(coins);
					// for (int z : possiblePermutations[i])
					// 	System.out.print(z + " ");
					// System.out.println();
				// 	System.out.println("!!!!!!!!!!!");
				// 	return minDepth;
				// }
				if (minDepth == depth + 1)
					return minDepth;
				if (minDepth < cutoff)
					cutoff = minDepth;
			}
		}
		return minDepth;
	}
}
