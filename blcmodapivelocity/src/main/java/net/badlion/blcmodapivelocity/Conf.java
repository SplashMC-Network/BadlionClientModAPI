package net.badlion.blcmodapivelocity;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Conf {

	private Map<String, DisallowedMods> modsDisallowed =  new HashMap<>();

	public Map<String, DisallowedMods> getModsDisallowed() {
		return this.modsDisallowed;
	}

	@Override
	public String toString() {
		return "Conf{" +
				"modsDisallowed=" + modsDisallowed +
				'}';
	}

	private static class DisallowedMods {

		private boolean disabled;
		private JsonObject extra_data;
		private JsonObject settings;

		@Override
		public String toString() {
			return "DisallowedMods{" +
					"disabled=" + disabled +
					", extra_data=" + extra_data +
					", settings=" + settings +
					'}';
		}
	}

}
