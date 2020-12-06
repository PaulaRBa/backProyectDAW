package com.daw.exceptions;

public class NewUserWithUniqueAliasException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NewUserWithUniqueAliasException() {
		super ("El nombre de usuario ya existe, elija otro.");
	}
}
