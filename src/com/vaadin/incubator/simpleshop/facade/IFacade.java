package com.vaadin.incubator.simpleshop.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.vaadin.incubator.simpleshop.data.AbstractPojo;

public interface IFacade {

    public void init(String name);

    public <A extends AbstractPojo> A find(Class<A> clazz, Long id);

    public <A extends AbstractPojo> List<A> list(Class<A> clazz);

    public <A extends AbstractPojo> List<A> list(String queryStr,
            Map<String, Object> parameters);

    public <A extends AbstractPojo> A find(String queryStr,
            Map<String, Object> parameters);

    public void store(AbstractPojo pojo);

    public <A extends AbstractPojo> void storeAll(Collection<A> pojos);

    public void delete(AbstractPojo pojo);

    public <A extends AbstractPojo> void deleteAll(Collection<A> pojos);

    public <A extends AbstractPojo> void refresh(A pojo);

    public void close();

    public void kill();
}
