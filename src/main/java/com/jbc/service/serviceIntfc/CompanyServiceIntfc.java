package com.jbc.service.serviceIntfc;

import java.util.List;
import java.util.Optional;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.companiesExceptions.CompanyAlreadyExistException;
import com.jbc.exceptions.companiesExceptions.CompanyNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.model.Company;

public interface CompanyServiceIntfc {

	/**
	 * Adds the company, only if not exist already.
	 *
	 * @param company the company
	 * @see isCompanyExist();
	 * @throws CompanyNotFoundException the company not found exception
	 * @throws CompanyAlreadyExistException 
	 */
	void addCompany(Company company) throws  CompanyAlreadyExistException;

	/**
	 * Checks if is company exist.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if is company exist
	 */
	boolean isCompanyExist(String email, String password);

	/**
	 * Update company the company if exist by Id.
	 *
	 * @param company the company to update
	 * @throws CompanyNotFoundException the company not found exception
	 */
	void updateCompany(Company company) throws CompanyNotFoundException;

	/**
	 * Delete company and all coupons from this company.
	 *
	 * @param companyId the company id
	 * @see #couponService
	 * @throws CompanyNotFoundException the company not found exception
	 * @throws CustomerNotFoundException 
	 * @throws GlobalException 
	 */
	void deleteCompany(int companyId) throws CompanyNotFoundException, GlobalException, CustomerNotFoundException;

	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 */
	List<Company> getAllCompanies();

	
	

	/**
	 * Gets the id by email and password.
	 *
	 * @param email the email
	 * @param password the password
	 * @return the id by email and password
	 */
	int getIdByEmailAndPassword(String email, String password);
	
	
	/**
	 * Gets the company user by email.
	 *
	 * @param email the email
	 * @return Optional company user.
	 * @throws CompanyNotFoundException 
	 */
	Optional<Company> findCompanyByEmail(String email) throws CompanyNotFoundException;


	/**
	 * Gets the one company.
	 *
	 * @param currentId the company id
	 * @return the one company
	 * @throws CompanyNotFoundException the company not found exception
	 */
	Company getOneCompany(int id) throws CompanyNotFoundException;
	

}
