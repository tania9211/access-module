package org.access.api;

public enum Level {
	READ((byte) 1), READ_WRITE((byte) 2), READ_WRITE_DELETE((byte) 3);

	private byte value;

	private Level(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}