package com.jbc.service;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.companiesExceptions.CompanyAlreadyExistException;
import com.jbc.exceptions.companiesExceptions.CompanyNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.repo.CompanyRepo;
import com.jbc.repo.CouponRepo;
import com.jbc.repo.CustomerRepo;
import com.jbc.service.serviceIntfc.CompanyServiceIntfc;



@Service


public class CompanyService implements CompanyServiceIntfc {
	@Autowired
	private CompanyRepo repo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private CouponRepo couponRepo;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CustomerService customerService;
	
	/**
	 * Checks if is company exist.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if is company exist
	 */

	@Override
	public boolean isCompanyExist(String email,String password) {

		if(repo.findByEmailAndPassword(email, password)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * Adds the company, only if not exist already.
	 *
	 * @param company the company
	 * @see #companyValidation(Company)
	 * @throws CompanyAlreadyExistException
	 */
	@Override
	public void addCompany(Company company) throws CompanyAlreadyExistException {

		if(!companyValidation(company)) {
			repo.save(company);
		}else {
			throw new CompanyAlreadyExistException("company already exist ");
		}
	}
	
	
	/**
	 * Company validation before adding
	 *
	 * @param company the company
	 * @return true, if successful
	 */
	private boolean companyValidation(Company company) {
		//Log.info("validation of the company {}",company.getName());
		if(repo.companyValid(company.getEmail(),company.getName())) {
			return true;
		}
		return false;
	}


	/**
	 * Update company the company if exist by Id.
	 *
	 * @param company the company to update
	 * @throws CompanyNotFoundException the company not found exception
	 */

	@Override
	public void updateCompany(Company company) throws CompanyNotFoundException {

		if(repo.findById(company.getId()).isPresent()) {
			Company upCompany=repo.findById(company.getId()).get();
			upCompany.setName(company.getName());
			upCompany.setEmail(company.getEmail());
			upCompany.setPassword(company.getPassword());
			if(company.getCoupons()!=null) {
				upCompany.getCoupons().addAll(company.getCoupons());
			}
			
			repo.save(upCompany);
		}else {
			throw new CompanyNotFoundException("Company was not found");
		}
	}
	
	/**
	 * Delete company and all coupons from this company.
	 *
	 * @param companyId the company id
	 * @see #couponService
	 * @throws CompanyNotFoundException the company not found exception
	 * @throws CustomerNotFoundException 
	 * @throws GlobalException 
	 */
	@Override
	public void deleteCompany(int companyId) throws CompanyNotFoundException, GlobalException, CustomerNotFoundException {

			
			if (repo.findById(companyId).isPresent()) {
				Company deleCompany =repo.findById(companyId).get();
				List<Coupon> couponList=couponRepo.findAllByCompany(companyId);
				List<Customer> customersList=customerRepo.findAllCustomerByCouponCompanyId(companyId);
				
				
				for (Coupon coupon: couponList) {
					for(Customer customer:customersList ) {
						if(! customer.getCoupons().stream().noneMatch(c->c.getCompanyId().equals((Integer.valueOf(companyId))) )){
							customerService.deletePurchasedCoupon(customer.getId(), coupon.getId());
						}
					}
					
				}couponService.deleteListCoupons(couponList);
				repo.delete(deleCompany);
			}else {
				throw new CompanyNotFoundException("Company by this id: "+companyId+", is bot exist");
			}
			
	
			
			
		}
	
	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 */

	@Override
	public List<Company> getAllCompanies(){

		return repo.findAll();
	}
	
	
	/**
	 * Gets the one company.
	 *
	 * @param currentId the company id
	 * @return the one company
	 * @throws CompanyNotFoundException the company not found exception
	 */

	public Company getOneCompany(int id) throws CompanyNotFoundException {

		return repo.findById(id).orElseThrow(()->new CompanyNotFoundException("Company by id: "+id+", was not found"));
	}


	/**
	 * Gets the id by email and password.
	 *
	 * @param email the email
	 * @param password the password
	 * @return the id by email and password
	 */
	@Override
	public int getIdByEmailAndPassword(String email, String password) {

		
		return repo.getIdByEmailAndPassword(email, password);
	}


	@Override
	public Optional<Company> findCompanyByEmail(String email) throws CompanyNotFoundException {
		
		Company company=repo.findByEmail(email).orElseThrow(()->new CompanyNotFoundException("Company by this email is not exist"));
		
		return Optional.of(company);
		
		
	}


	public Company getOneCompany(String email) throws CompanyNotFoundException {
		
		return repo.findByEmail(email).orElseThrow(()->new CompanyNotFoundException("Company by email: "+email+", was not found"));
	}


	
	
	
	}
	


