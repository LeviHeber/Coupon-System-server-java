package couponsSystem.core.services.Impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.reposetory.CompanyReposetory;
import couponsSystem.core.reposetory.CouponsReposetory;
import couponsSystem.core.reposetory.CustomerReposetory;
import couponsSystem.core.services.ClientService;

/**
 * Primary Facade and logics for all types of system clients, containing
 * variables and methods common to all.
 * <p>
 * Facade-approved operations are performed using the appropriate DAO classes.
 * 
 * @author Levi Heber
 * @see AdminServiceImpl
 * @see CompanyServiceImpl
 * @see CustomerServiceImpl
 * @see DAO
 * @see CustomerReposetory
 * @see CouponsReposetory
 */
/**
 * @author Levi Heber
 *
 */
public abstract class ClientServiceImpl implements ClientService {

	/**
	 * Manipulates the coupon environment in DAO into action for logical testing and
	 * data writing.
	 */
	@Autowired
	protected CompanyReposetory companiesReposetory;

	/**
	 * Manipulates the customer environment in DAO into action for logical testing
	 * and data writing.
	 */
	@Autowired
	protected CustomerReposetory customersReposetory;

	/**
	 * Manipulates the company environment in DAO into action for logical testing
	 * and data writing.
	 */
	@Autowired
	protected CouponsReposetory couponsReposetory;

	/**
	 * Create a new facade with all the required variables.
	 * 
	 */
	@Autowired
	public ClientServiceImpl() {
	}

	/**
	 * client's login verification According to the email and password.
	 * 
	 * @param email The checked client email address.
	 * @param name  The checked client name.
	 * @return boolean of the login verification.
	 * @throws CouponsSystemException When the login process fails.
	 */
	public abstract boolean login(String email, String password) throws CouponsSystemException;

	/**
	 * A generic method for checking the collection and returning it when it has
	 * values, and throwing an error when it is empty.
	 * 
	 * @param <T>                   Type of values in the collection.
	 * @param list                  The requested collection for testing before
	 *                              return.
	 * @param collectionDescription Verbal description of the collection, for
	 *                              inclusion in the error message if it occurs.
	 * @return The requested collection which is not empty.
	 * @throws CouponsSystemException When the collection is empty.
	 */
	protected <T> ArrayList<T> getAll(Collection<T> list, String collectionDescription) throws CouponsSystemException {
		if (!list.isEmpty()) {
			return new ArrayList<T>(list);
		}
		throw new CouponsSystemException("There is no " + collectionDescription);
	}

	/**
	 * A generic method for checking an {@link Optional} and returning it's value,
	 * or throwing an {@link CouponsSystemException} when it is empty.
	 * 
	 * @param <T>      Type of value to get.
	 * 
	 * @param optional The requested {@link Optional} for return it's value.
	 * @param itemDesc Verbal description of the value, for inclusion in the error
	 *                 message if it occurs.
	 * @return The requested value from the {@link Optional}.
	 * @throws CouponsSystemException When the collection is empty.
	 */
	protected <T> T get(Optional<T> optional, String itemDesc) throws CouponsSystemException {
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new CouponsSystemException("the " + itemDesc + "dose not exists");
	}

	/**
	 * A generic method for deleting a particular value when it exists within the
	 * database.
	 * 
	 * @param <T>        Type of value to delete.
	 * @param <ID>       The ID type of <T> value in the database.
	 * @param repository The repostory set to handle that <T> in the database.
	 * @param id         Identifies of the object you want to delete.
	 * @param itemDesc   Verbal description of the value, for inclusion in the
	 *                   error.
	 * @throws CouponsSystemException
	 *                                <li>delete the value fails.
	 *                                <li>the value dose not exists.
	 * @see CompanyReposetory
	 * @see CustomerReposetory
	 * @see CouponsReposetory
	 */
	protected <T, ID> void delete(CrudRepository<T, ID> repository, ID id, String itemDesc)
			throws CouponsSystemException {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				return;
			}
		} catch (Exception e) {
			throw new CouponsSystemException("delete " + itemDesc + " fails");
		}
		throw new CouponsSystemException("the " + itemDesc + " dose not exists");
	}

	/**
	 * A generic method for adding a particular value when it is not exists the
	 * database.
	 * 
	 * @param <T>      Type of value to add.
	 * @param entity   The value entered in the database.
	 * @param exists   The result of a condition according to which in the TRUE the
	 *                 value will be added, and in FALSE the addition will not be
	 *                 made.
	 * @param existsBy Verbal description of the condition, for inclusion in the
	 *                 error.
	 * @return
	 * @throws CouponsSystemException
	 *                                <li>add the value fails.
	 *                                <li>the value already exists.
	 */
	protected <T> T add(T entity, boolean exists, String existsBy) throws CouponsSystemException {
		try {
			if (exists) {
				getRepository(entity).save(entity);
				return entity;
			}
		} catch (Exception e) {
			throw new CouponsSystemException("add " + entity.getClass().getSimpleName() + " fails");
		}
		throw new CouponsSystemException(
				"the " + entity.getClass().getSimpleName() + " " + existsBy + " already exists");
	}

	/**
	 * A generic method for update a particular in the database.
	 * 
	 * @param <T>    Type of value to update.
	 * @param entity The value to be update in the database.
	 * @throws CouponsSystemException update the value fails.
	 */
	protected <T> void update(T entity) throws CouponsSystemException {
		try {
			getRepository(entity).save(entity);
			return;
		} catch (Exception e) {
			throw new CouponsSystemException("update " + entity.getClass().getSimpleName() + " fails");
		}
	}

	/**
	 * Method of returning the repository intended for use with a particular object
	 * ({@link Company}, {@link Customer}, {@link Coupon}) in generic methods:
	 * {@link #add(Object, boolean, String)}, {@link #get(Optional, String)},
	 * {@link #getAll(Collection, String)},
	 * {@link #delete(CrudRepository, Object, String)}, {@link #update(Object)}
	 * 
	 * @param <T>    Type of value to use in the repository.
	 * @param <ID>   The ID type of <T> value in the repository.
	 * @param entity The value to get it repository,
	 * @return the appropriate repository.
	 */
	private <T, ID> CrudRepository<T, ID> getRepository(T entity) {
		Object repository = entity instanceof Company ? companiesReposetory
				: entity instanceof Customer ? customersReposetory
						: entity instanceof Coupon ? couponsReposetory : null;
		return (CrudRepository<T, ID>) repository;
	}
}
