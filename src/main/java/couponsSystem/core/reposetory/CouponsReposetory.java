package couponsSystem.core.reposetory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Coupon.Category;

/**
 * <p>
 * Coupon coupon system interface towards DAO.
 * 
 * @author Levi Heber
 * @see Coupon
 */
@Transactional
public interface CouponsReposetory extends JpaRepository<Coupon, Integer> {

	Collection<Coupon> findByCompanyId(int companyId);

	@Query("SELECT c1 FROM Customer c2 JOIN c2.coupons c1 where c1.id=:couponId and c2.id=:customerId ")
	Optional<Coupon> findByIdAndCustomerId(int couponId, int customerId);

	void deleteByCompanyId(int companyId);

	Collection<Coupon> findByCompanyIdAndPriceLessThan(Integer companyId, Double price);

	Collection<Coupon> findByCompanyIdAndCategory(Integer companyId, Category category);

	@Query("SELECT c1 FROM Customer c2 JOIN c2.coupons c1 where c2.id=:customerId and c1.category=:category")
	Collection<Coupon> findByCustomerIdAndCategory(int customerId, Category category);

	@Query("SELECT c1 FROM Customer c2 JOIN c2.coupons c1 where c2.id=:customerId and c1.price<:price")
	Collection<Coupon> findByCustomerIdAndPriceLessThan(int customerId, Double price);

	long countByEndDateBefore(LocalDate beforeEndDate);

	void deleteByEndDateBefore(LocalDate beforeEndDate);
}
