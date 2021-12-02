package com.jbc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbc.controller.client.ClientType;
import com.jbc.messages.requests.LoginForm;
import com.jbc.messages.requests.SignUpCompanyForm;
import com.jbc.messages.requests.SignUpCustomerForm;
import com.jbc.messages.responses.JwtResponse;
import com.jbc.messages.responses.ResponseMessage;
import com.jbc.model.Admin;
import com.jbc.model.Company;
import com.jbc.model.Customer;
import com.jbc.model.Role;
import com.jbc.repo.CompanyRepo;
import com.jbc.repo.CustomerRepo;
import com.jbc.repo.AdminRepo;
import com.jbc.repo.RoleRepo;
import com.jbc.service.userServices.UserPrinciple;
import com.jbc.springSecurity.jwt.JwtProvider;

@CrossOrigin(origins="*",maxAge=3600)
@RestController
@RequestMapping("/apis/auth")
public class AuthRestApis {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		// option number 1) get authentication
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		// option number 2) get authentication
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		
		ClientType role=loginRequest.getRole();
		Collection<? extends GrantedAuthority> authorities = null;
		
		switch (role) {
		case ROLE_Administrator:
			
			//if(adminRepo.existsByEmailAndPassword(loginRequest.getUsername(),loginRequest.getPassword())) {}
			if(userPrinciple.getAuthorities().contains(new SimpleGrantedAuthority(ClientType.ROLE_Administrator.name()))) {
				authorities =new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(ClientType.ROLE_Administrator.name())));
				}
			break;
			
		case ROLE_Customer:
			if(userPrinciple.getAuthorities().contains(new SimpleGrantedAuthority(ClientType.ROLE_Customer.name()))) {
				authorities =new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(ClientType.ROLE_Customer.name())));
			}
		break;
		case ROLE_Company:
			if(userPrinciple.getAuthorities().contains(new SimpleGrantedAuthority(ClientType.ROLE_Company.name()))) {
				authorities =new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(ClientType.ROLE_Company.name())));
			}
		default:
			break;
		}
		
		
		
		
		return ResponseEntity.ok(new JwtResponse(jwt, userPrinciple.getUsername(), authorities));
		//return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}
	
	
	@PostMapping("/registercustomer")
	public ResponseEntity<?> registerCustomerUser(@Valid @RequestBody SignUpCustomerForm signUpRequest) {
		if (customerRepo.isExistByEmail(signUpRequest.getEmail()) || companyRepo.findByEmail(signUpRequest.getEmail()).isPresent()) {
			return new ResponseEntity<>(new ResponseMessage("Error : Email is already exist!"),
					HttpStatus.BAD_REQUEST);
		}
		Customer user = new Customer(signUpRequest.getFirstname(), signUpRequest.getLastname(), 
								signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>(); 
		Role userRole = roleRepo.findByType(ClientType.ROLE_Customer)
					.orElseThrow(() -> new RuntimeException("Error : Role was not found."));
		roles.add(userRole);			
		
	
		customerRepo.save(user);

		return new ResponseEntity<>(new ResponseMessage("Customer : "+ signUpRequest.getFirstname() + " was registered successfully!"), HttpStatus.OK);
	}
	@PostMapping("/registercompany")
	public ResponseEntity<?> registerCompanyUser(@Valid @RequestBody SignUpCompanyForm signUpRequest) {
		if (customerRepo.isExistByEmail(signUpRequest.getEmail()) || companyRepo.findByEmail(signUpRequest.getEmail()).isPresent()) {
			return new ResponseEntity<>(new ResponseMessage("Error : Email is already exist!"),
					HttpStatus.BAD_REQUEST);
		}

	
		// Creating company account
		Company user = new Company(signUpRequest.getCompanyName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>(); 
		
		
			Role userRole = roleRepo.findByType(ClientType.ROLE_Company)
					.orElseThrow(() -> new RuntimeException("Error : Role was not found."));
			roles.add(userRole);			
		
		
		user.setRole(ClientType.ROLE_Company);
		companyRepo.save(user);

		return new ResponseEntity<>(new ResponseMessage("Company "+ signUpRequest.getCompanyName() + " was registered successfully!"), HttpStatus.OK);
	}
	public void createAdmin(Admin admin) {
		
	}
}
