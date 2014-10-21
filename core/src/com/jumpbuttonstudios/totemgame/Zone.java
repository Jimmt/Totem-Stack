package com.jumpbuttonstudios.totemgame;

public enum Zone {
	LOWER(0, -1), UPPER(7, 15), STARS(200, 10), RAIN(300, 5);

	private float y;
	private float cloudFreq;
	private static Zone[] values;

	private Zone(float y, float cloudFreq) {
		this.y = y;
		
		this.cloudFreq = cloudFreq;
	}

	public static Zone[] getArray() {
		if (values != null) {
		} else {
			values = Zone.values();
		}
		return values;
	}

	public float getCloudFrequency() {
		return cloudFreq;
	}

	public float getY() {
		return y;
	}
}
