package couponsSystem.core.reposetory;

import java.util.Optional;

/**
 * Generic interface with general repostory methods for all system clients,
 * Designed to be expanded and added to each one individually
 * 
 * @author Levi Heber
 * @see CompanyReposetory
 * @see CustomerReposetory
 * @param <T> Type of client accept
 */
public interface ClientReposetory<T> {

	Optional<T> findByEmailAndPassword(String email, String password);

	Optional<T> findByEmail(String email);

}
