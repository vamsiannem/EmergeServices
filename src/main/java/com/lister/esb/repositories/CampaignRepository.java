/**
 * 
 */
package com.lister.esb.repositories;

import com.lister.esb.model.CampaignBO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Campaign Repository to manipulate campaign entities.
 * 
 * @author sudharsan_s
 *
 */
public interface CampaignRepository extends JpaRepository<CampaignBO, Long> {
	
}