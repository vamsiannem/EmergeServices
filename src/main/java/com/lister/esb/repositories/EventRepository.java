/**
 *
 */
package com.lister.esb.repositories;

import com.lister.esb.model.EventBO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Customer Repository to manipulate customer entities.
 *
 * @author sudharsan_s
 *
 */
public interface EventRepository extends JpaRepository<EventBO, Long> {

}
