package by.segg3r.messages.client;

import java.io.Serializable;

import by.segg3r.messaging.Message;

public class ClientAuthenticationMessage extends Message implements Serializable {

	private static final long serialVersionUID = -7724618385254832763L;

	private String login;
	private String password;

	public ClientAuthenticationMessage(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
