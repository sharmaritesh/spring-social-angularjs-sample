package com.rites.sample.ssa.model;

import java.io.Serializable;

public class ConnectionInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String provider;
	private String displayName = "No Name";
	private boolean isConfigured;
	
	public ConnectionInfo() {
	}

	public ConnectionInfo(String provider, String displayName) {
		this.provider = provider;
		this.displayName = displayName;
	}

	public boolean isConfigured() {
		return isConfigured;
	}

	public void setConfigured(boolean isConfigured) {
		this.isConfigured = isConfigured;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public ConnectionInfo markAsConfigured() {
		this.isConfigured = true;
		return this;
	}
}
