package com.lister.esb.repositories;

import com.lister.esb.model.BrowseBO;
import com.lister.esb.model.CartBO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/22/12
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BrowseRepository extends JpaRepository<BrowseBO, Long> {

}
