import java.util.ArrayList;

class Coins extends ArrayList<Coin> {
	public Coins(int c) {
		super(c);
		for (int i = 0; i < c; i++)
			add(new Coin());
	}

	public boolean over() {
		boolean init = true;
		for (Coin c : this)
			if (c.getState() != Coin.STATE.NORMAL)
				if (init) init = false;
				else return false;
		return true;
	}

	public void undoStates() {
		for (Coin c : this)
			c.undoState();
	}

	@Override
	public String toString() {
		String str = "[";
		for (Coin c : this)
			str += c + ", ";
		return str.substring(0, str.length() - 2) + "]";
	}
}