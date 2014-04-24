package com.lister.esb.repositories;

import com.lister.esb.model.ProgramBO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/16/12
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */


public interface ProgramRepository extends JpaRepository<ProgramBO, Long> {

}
