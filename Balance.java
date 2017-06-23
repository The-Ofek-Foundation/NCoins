import java.util.ArrayList;

public class Balance {
	private Coins coins;
	private ArrayList<Coin> s1, s2;

	public Balance(Coins coins) {
		this.coins = coins;
		s1 = new ArrayList<Coin>();
		s2 = new ArrayList<Coin>();
	}

	public void set(int[] perm) {
		resetBalance();
		for (int i = 0; i < perm.length; i++)
			add(i, perm[i]);
	}

	public void add(int i, int side) {
		if (side == 1)
			s1.add(coins.get(i));
		else if (side == 2)
			s2.add(coins.get(i));
	}

	public void resetBalance(){
		s1.clear();
		s2.clear();
	}

	public boolean usefulWeighing() {
		Coin.STATE ss1 = null, ss2 = null;
		boolean allNormalOutside = true;

		for (Coin c : coins)
			if (s1.contains(c))
				if (ss1 == null)
					ss1 = c.getState();
				else if (ss1 == Coin.STATE.UNKNOWN
					|| ss1 == c.getState());
				else ss1 = Coin.STATE.UNKNOWN;
			else if (s2.contains(c))
				if (ss2 == null)
					ss2 = c.getState();
				else if (ss2 == Coin.STATE.UNKNOWN
					|| ss2 == c.getState());
				else ss2 = Coin.STATE.UNKNOWN;
			else if (c.getState() != Coin.STATE.NORMAL)
				allNormalOutside = false;

		if (ss1 == Coin.STATE.UNKNOWN || ss2 == Coin.STATE.UNKNOWN)
			return true;

		if (ss1 == ss2)
			if (ss1 == Coin.STATE.NORMAL)
				return false;
			else return true;

		return !allNormalOutside;
	}

	public void weighCoins(int result) {
		switch (result) {
			case 0:
				for (Coin coin : coins)
					if (s1.contains(coin) || s2.contains(coin))
						coin.setState(Coin.STATE.NORMAL);
					else coin.incrementState();
				break;
			case 1:
				for (Coin coin : coins)
					if (coin.getState() == Coin.STATE.NORMAL)
						coin.incrementState();
					else if (s1.contains(coin))
						if (coin.getState() == Coin.STATE.LIGHTER)
							coin.setState(Coin.STATE.NORMAL);
						else coin.setState(Coin.STATE.HEAVIER);
					else if (s2.contains(coin))
						if (coin.getState() == Coin.STATE.HEAVIER)
							coin.setState(Coin.STATE.NORMAL);
						else coin.setState(Coin.STATE.LIGHTER);
					else coin.setState(Coin.STATE.NORMAL);
				break;
			case 2:
				for (Coin coin : coins)
					if (coin.getState() == Coin.STATE.NORMAL)
						coin.incrementState();
					else if (s1.contains(coin))
						if (coin.getState() == Coin.STATE.HEAVIER)
							coin.setState(Coin.STATE.NORMAL);
						else coin.setState(Coin.STATE.LIGHTER);
					else if (s2.contains(coin))
						if (coin.getState() == Coin.STATE.LIGHTER)
							coin.setState(Coin.STATE.NORMAL);
						else coin.setState(Coin.STATE.HEAVIER);
					else coin.setState(Coin.STATE.NORMAL);
				break;
		}
	}
}