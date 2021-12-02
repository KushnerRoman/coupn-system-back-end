package com.jbc.messages.requests;

import java.util.Set;

import javax.validation.constraints.*;

public class SignUpCustomerForm {
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    
    
    public SignUpCustomerForm(@NotBlank @Size(min = 3, max = 50) String firstname,
			@NotBlank @Size(min = 3, max = 50) String lastname, @NotBlank @Size(max = 60) @Email String email,
			Set<String> role, @NotBlank @Size(min = 6, max = 40) String password) {
	
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
		this.password = password;
	}

    
	public SignUpCustomerForm() {
	
	}


	public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
    	return this.role;
    }
    
    public void setRole(Set<String> role) {
    	this.role = role;
    }
}