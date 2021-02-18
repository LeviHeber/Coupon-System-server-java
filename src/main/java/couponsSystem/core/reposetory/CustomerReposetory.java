package couponsSystem.core.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;
import couponsSystem.core.entites.Customer;

/**
 * <p>
 * Customer coupon system interface towards DAO.
 * 
 * @author Levi Heber
 * @see Customer
 */
public interface CustomerReposetory extends JpaRepository<Customer, Integer>, ClientReposetory<Customer>{

	boolean existsByEmail(String email);
	
}
