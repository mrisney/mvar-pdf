package com.itis.mvar.pdf.repository;

public enum Schema {

	HAWAII("HAWAII"), 
	WYOMING("WYOMING");

	private String text;

	Schema(String text) {
		this.text = text;
	}

	public String toText() {
		return this.text;
	}

	public static Schema fromString(String text) {
		for (Schema schema : Schema.values()) {
			if (schema.text.equalsIgnoreCase(text)) {
				return schema;
			}
		}
		return null;
	}
}