package com.vaadin.incubator.simpleshop.data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
abstract public class AbstractPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Column(nullable = false)
	@Version
	protected Long consistencyVersion;

	public AbstractPojo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConsistencyVersion() {
		return consistencyVersion;
	}

	public void setConsistencyVersion(Long consistencyVersion) {
		this.consistencyVersion = consistencyVersion;
	}

}
