package cc.cosmetica.api.settings;

/**
 * The settings for a cape server.
 */
public class CapeServer {
	public CapeServer(String name, String warning, int checkOrder, CapeDisplay display) {
		this.name = name;
		this.warning = warning;
		this.checkOrder = checkOrder;
		this.display = display;
	}

	private final String name;
	private final String warning;
	private final int checkOrder;
	private final CapeDisplay display;

	/**
	 * @return the decorated name of this cape server.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the warning for enabling this or an empty string to display to users. The actual message should be chosen by the implementation.
	 * @return the warning to display the user for enabling this setting. Will be undecorated, or an empty string if there is no warning.
	 */
	public String getWarning() {
		return this.warning;
	}

	public int getCheckOrder() {
		return this.checkOrder;
	}

	public CapeDisplay getDisplay() {
		return this.display;
	}
}
