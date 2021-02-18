package couponsSystem.core.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;
import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Customer;

/**
 * <p>
 * Company coupon system interface towards DAO.
 * 
 * @author Levi Heber
 * @see Customer
 */
public interface CompanyReposetory extends JpaRepository<Company, Integer>, ClientReposetory<Company> {

	boolean existsByEmailOrName(String email, String name);

}
