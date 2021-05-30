package com.arktech.util;

public enum AppUserPermission {
	DELIVERY_READ("delivery:read"),
	DELIVERY_WRITE("delivery:write"),
	DELIVERY_UPDATE("delivery:update"),
	DELIVERY_DELETE("delivery:delete");
	
	private final String permission;
	
	private AppUserPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}
