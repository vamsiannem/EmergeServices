package com.lister.esb.repositories;

import com.lister.esb.model.SkuBO;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 12/27/12
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SkuRepository  extends JpaRepository<SkuBO, Long>{
}
