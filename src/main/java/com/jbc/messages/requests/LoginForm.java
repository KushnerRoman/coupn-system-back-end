package com.jbc.messages.requests;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.jbc.controller.client.ClientType;

public class LoginForm {
    @NotBlank
    @Size(min=3, max = 60)
    private String username;

    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private ClientType role;
    
    
    




	public LoginForm(@NotBlank @Size(min = 3, max = 60) String username,
			@NotBlank @Size(min = 4, max = 40) String password, ClientType role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public LoginForm() {
	
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	public ClientType getRole() {
		return role;
	}

	public void setRole(ClientType role) {
		this.role = role;
	}
}