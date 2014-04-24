package com.lister.esb.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Base class for entity objects.
 * 
 * @author sudharsan_s
 *
 */
@MappedSuperclass
public abstract class BaseBO implements Serializable {

    public abstract Long getId();
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -4994510753287603146L;

}
