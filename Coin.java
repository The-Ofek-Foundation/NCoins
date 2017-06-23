class Coin {

	public static enum STATE {
		HEAVIER, LIGHTER, NORMAL, UNKNOWN,
	}

	private STATE[] states;
	private int i;

	public Coin() {
		i = 0;
		states = new STATE[10000000];
		states[i] = STATE.UNKNOWN;
	}

	public void setState(STATE state) {
		i++;
		states[i] = state;
	}
	public void incrementState() {
		i++;
		states[i] = states[i - 1];
	}
	public STATE getState() { return states[i]; }
	public void undoState() { i--; }

	@Override
	public String toString() {
		return getState().toString();
	}

}