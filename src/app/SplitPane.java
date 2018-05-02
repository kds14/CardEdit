package app;

import javax.swing.JSplitPane;

public class SplitPane extends JSplitPane {
	final int loc;

	public SplitPane(int loc) {
		this.loc = loc;
		setDividerLocation(loc);
	}

	@Override
	public int getDividerLocation() {
		return loc;
	}
}
