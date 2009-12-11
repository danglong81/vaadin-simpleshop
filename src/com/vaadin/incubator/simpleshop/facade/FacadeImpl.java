package com.vaadin.incubator.simpleshop.facade;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.sessions.Session;

import com.vaadin.incubator.simpleshop.data.AbstractPojo;
import com.vaadin.incubator.simpleshop.data.LogMsg;
import com.vaadin.incubator.simpleshop.util.LogUtil;

public class FacadeImpl implements IFacade {

    protected EntityManagerFactory emf = null;
    protected EntityManager em = null;

    public FacadeImpl() {

    }

    public FacadeImpl(String name) {
        emf = Persistence.createEntityManagerFactory(name);
    }

    public void init(String name) {
        emf = Persistence.createEntityManagerFactory(name);
    }

    public <A extends AbstractPojo> A find(Class<A> clazz, Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(clazz, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> List<A> list(Class<A> clazz) {
        EntityManager em = getEntityManager();
        try {
            ExpressionBuilder builder = new ExpressionBuilder();
            JpaEntityManager jpaEm = JpaHelper.getEntityManager(em);
            Query query = jpaEm.createQuery(builder, clazz);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> List<A> list(String queryStr,
            Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery(queryStr);
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> A find(String queryStr,
            Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery(queryStr);
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }

            return (A) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void store(AbstractPojo pojo) {
        EntityManager em = getEntityManager();
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            } else if (!(pojo instanceof LogMsg)) {
                LogUtil.debug("Transaction was open. Trying to save a "
                        + pojo.getClass().getName());
            }
            if (pojo.getId() != null) {
                em.merge(pojo);
            } else {
                em.persist(pojo);
            }
            em.getTransaction().commit();
            refresh(pojo);
        } catch (Exception e) {
            LogUtil.debug(e);
        } finally {
            em.close();
        }
    }

    public <A extends AbstractPojo> void storeAll(Collection<A> pojos) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            for (AbstractPojo pojo : pojos) {
                if (pojo.getId() != null) {
                    em.merge(pojo);
                } else {
                    em.persist(pojo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            LogUtil.debug(e);
        } finally {
            em.close();
        }
    }

    public void delete(AbstractPojo pojo) {
        // If it isn't stored, it can't be removed
        if (pojo.getId() == null) {
            return;
        }

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Object entity = em.find(pojo.getClass(), pojo.getId());
            em.remove(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public <A extends AbstractPojo> void deleteAll(Collection<A> pojos) {
        if (pojos == null) {
            return;
        }

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (A pojo : pojos) {
                // If it isn't stored, it can't be removed
                if (pojo.getId() == null) {
                    continue;
                }
                Object entity = em.find(pojo.getClass(), pojo.getId());
                if (entity != null) {
                    em.remove(entity);
                }
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    protected EntityManager getEntityManager() {
        if ((em == null || !em.isOpen()) && emf != null) {
            em = emf.createEntityManager();
        }

        return em;
    }

    protected Session getSession() {
        JpaEntityManager emImpl = (JpaEntityManager) getEntityManager()
                .getDelegate();
        return emImpl.getSession();
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.clear();
            em.close();
        }
    }

    public void kill() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> void refresh(A pojo) {
        boolean closeEm = false;
        if (em == null || !em.isOpen()) {
            em = getEntityManager();
            closeEm = true;
        }
        A pojo2 = (A) em.find(pojo.getClass(), pojo.getId());
        em.refresh(pojo2);

        copyFieldsRecursively(pojo, pojo2, pojo.getClass());

        if (closeEm) {
            em.close();
        }
    }

    private <A extends AbstractPojo> void copyFieldsRecursively(A pojo,
            A pojo2, Class<?> c) {
        if (c != null) {
            Field[] fields = c.getDeclaredFields();

            for (Field field : fields) {
                if (!field.isAnnotationPresent(Transient.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(pojo, field.get(pojo2));
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            copyFieldsRecursively(pojo, pojo2, c.getSuperclass());
        }
    }
}
