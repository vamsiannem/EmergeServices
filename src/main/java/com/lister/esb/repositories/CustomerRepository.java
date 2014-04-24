/**
 * 
 */
package com.lister.esb.repositories;

import com.lister.esb.model.CustomerBO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Customer Repository to manipulate customer entities.
 * 
 * @author sudharsan_s
 *
 */
public interface CustomerRepository extends JpaRepository<CustomerBO, Long> {

}
