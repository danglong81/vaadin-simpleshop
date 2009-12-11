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

import com.vaadin.incubator.simpleshop.data.AbstractPojo;
import com.vaadin.incubator.simpleshop.data.LogMsg;
import com.vaadin.incubator.simpleshop.util.LogUtil;

/**
 * An implementation of the IFacade interface. This class acts as a layer
 * between the application logic and the database. This class's responsibilities
 * are to connect to the database and manage the objects which are stored to or
 * deleted/fetched from the database.
 * 
 * @author Kim
 * 
 */
public class JPAFacade implements IFacade {

    protected EntityManagerFactory emf = null;
    protected EntityManager em = null;

    /**
     * Default constructor which does nothing. Make sure to call init() if
     * you've used this constructor.
     */
    public JPAFacade() {

    }

    /**
     * Alternative constructor. Takes as input the persistence-unit name and
     * creates and entity manager factory based on that name.
     * 
     * @param name
     *            Persistence-unit name (defined in the persistence.xml)
     */
    public JPAFacade(String name) {
        init(name);
    }

    /**
     * Initializes this facade. Creates an entity manager factory based on the
     * configurations for the given name
     * 
     * @param name
     *            Persistence-unit name (defined in the persistence.xml)
     */
    public void init(String name) {
        emf = Persistence.createEntityManagerFactory(name);
    }

    /**
     * {@inheritDoc}
     */
    public <A extends AbstractPojo> A find(Class<A> clazz, Long id) {
        // Get the EntityManager and use its find() method to fetch the object.
        EntityManager em = getEntityManager();
        try {
            return em.find(clazz, id);
        } finally {
            // Once we've done the query, close the EntityManager
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> List<A> list(Class<A> clazz) {
        EntityManager em = getEntityManager();
        try {
            // Use the ExpressionBuilder to create a query which fetches a list
            // of the given objects.
            ExpressionBuilder builder = new ExpressionBuilder();
            JpaEntityManager jpaEm = JpaHelper.getEntityManager(em);
            // Build the query
            Query query = jpaEm.createQuery(builder, clazz);
            // Execute the query and return the result
            return query.getResultList();
        } finally {
            // Once we've done the query, close the EntityManager
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> List<A> list(String queryStr,
            Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            // Create a query object from the query string given as the
            // parameter
            Query query = em.createQuery(queryStr);
            // Check if we have some parameters defined
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    // Inject the parameter to the query
                    query.setParameter(key, parameters.get(key));
                }
            }
            // Execute query and return results
            return query.getResultList();
        } finally {
            // Once we've done the query, close the EntityManager
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> A find(String queryStr,
            Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            // Create a query object from the query string given as the
            // parameter
            Query query = em.createQuery(queryStr);
            // Check if we have some parameters defined
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    // Inject the parameter to the query
                    query.setParameter(key, parameters.get(key));
                }
            }

            // Execute query and return result
            return (A) query.getSingleResult();
        } catch (NoResultException e) {
            // This exception will occur if no results were found with the given
            // query. If this occurs, return null.
            return null;
        } finally {
            // Once we've done the query, close the EntityManager
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void store(AbstractPojo pojo) {
        EntityManager em = getEntityManager();
        try {
            // Check if we have an open transaction
            if (!em.getTransaction().isActive()) {
                // If not, open a new transaction
                em.getTransaction().begin();
            } else if (!(pojo instanceof LogMsg)) {
                // An open transaction is an error situation which should be
                // logged. There is a condition which checks if the pojo is an
                // instance of the LogMsg class. This is necessary, because the
                // LogUtil will use this store method to store the LogMsg it
                // produces. If we didn't have the check, we would get an
                // infinite loop since the row below would cause a new
                // store()-method call and we would end up in this same
                // conditional clause since the transaction is still active.
                LogUtil.debug("Transaction was open. Trying to save a "
                        + pojo.getClass().getName());
            }
            // Check if the entity has an id (primary key). If it has a primary
            // key, then there is an existing instance of this object in the
            // database and we only need to update its state.
            if (pojo.getId() != null) {
                em.merge(pojo);
            } else {
                // An id didn't exist, so we have a new entity in our hands,
                // hence we need to persist it and not merge.
                em.persist(pojo);
            }
            // Commit the transaction
            em.getTransaction().commit();

            // The concurrency version id has now been incremented for pojo,
            // hence we need to refresh the pojo at this point to avoid
            // exceptions caused by the optimistic locking. This refresh won't
            // cause an extra database query, since the object is cached in the
            // JPA providers memory.
            refresh(pojo);
        } catch (Exception e) {
            // Log any possible error situations
            LogUtil.debug(e);
        } finally {
            // Once we've done the query, close the EntityManager
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public <A extends AbstractPojo> void storeAll(Collection<A> pojos) {
        // This method follows the same principles as the store() method. Read
        // store()'s comments for more detailed explanations.
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // Loop through all entities
            for (AbstractPojo pojo : pojos) {
                // Merge or persist the objects depending on if they already
                // exist in the database.
                if (pojo.getId() != null) {
                    em.merge(pojo);
                } else {
                    em.persist(pojo);
                }
            }
            // Commit the transaction.
            em.getTransaction().commit();

            // Refresh all the pojos.
            for (AbstractPojo pojo : pojos) {
                refresh(pojo);
            }
        } catch (Exception e) {
            LogUtil.debug(e);
        } finally {
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(AbstractPojo pojo) {
        // If it isn't stored, it can't be removed
        if (pojo.getId() == null) {
            return;
        }

        EntityManager em = getEntityManager();
        try {
            // Begin the transaction
            em.getTransaction().begin();
            // We need to merge this object to the database session, so that it
            // can be deleted. We do this by actually fetching the object first.
            Object entity = em.find(pojo.getClass(), pojo.getId());
            // Now when we have a fresh instance of the entity which is attached
            // to the JPA provider's session, we can remove the entity.
            em.remove(entity);
            // Commit transaction.
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public <A extends AbstractPojo> void deleteAll(Collection<A> pojos) {
        if (pojos == null) {
            return;
        }
        // This method follows the same principles as the delete() method. Read
        // delete()'s comments for more detailed explanations.

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (A pojo : pojos) {
                // If it isn't stored, it can't be removed
                if (pojo.getId() == null) {
                    continue;
                }
                // Fetch the entity merged to the session
                Object entity = em.find(pojo.getClass(), pojo.getId());
                if (entity != null) {
                    // Remove the entity
                    em.remove(entity);
                }
            }
            // Commit transaction
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Returns an instance of the EntityManager which is open for use
     * 
     * @return
     */
    protected EntityManager getEntityManager() {
        // Check if em is null or if the em has been closed.
        if ((em == null || !em.isOpen()) && emf != null) {
            // create a new em if we didn't have a usable one available.
            em = emf.createEntityManager();
        }

        return em;
    }

    /**
     * Closes the entity manager.
     */
    public void close() {
        // Close the entity manager
        if (em != null && em.isOpen()) {
            em.clear();
            em.close();
        }
    }

    /**
     * Closes the entity manager and the entity manager factory.
     */
    public void kill() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends AbstractPojo> void refresh(A pojo) {
        // A boolean which defines if we should close the EntityManager after
        // the refresh is done.
        boolean closeEm = false;

        // If this method is called independently, in other words, not from
        // within this facade, then we should close the entity manager after the
        // refresh is done. However, if it is called from within this facade,
        // then we should have and open entity manager and the calling method
        // will take care of closing the em.
        if (em == null || !em.isOpen()) {
            em = getEntityManager();
            closeEm = true;
        }
        // Get a fresh instance of the object.
        A pojo2 = (A) em.find(pojo.getClass(), pojo.getId());
        // Make sure its state is up-to-date
        em.refresh(pojo2);

        // Now copy all fields' values from pojo2 back to pojo
        copyFieldsRecursively(pojo, pojo2, pojo.getClass());

        // Close the em if necessary
        if (closeEm) {
            em.close();
        }
    }

    /**
     * Copies all field values recursively from pojo2 to pojo
     * 
     * @param pojo
     *            The object to which we are copying
     * @param pojo2
     *            The object from which we are copying
     * @param c
     *            The class we are currently processing
     */
    private <A extends AbstractPojo> void copyFieldsRecursively(A pojo,
            A pojo2, Class<?> c) {
        // if class is null, then there is nothing left to copy
        if (c != null) {
            // Get all the fields in this class. NOTE! getDeclaredFields() will
            // return all fields whether they are private or not. However,
            // getDeclaredFields() will only return the fields defined in
            // Class<?> c, and not the fields in its superclass(es). Hence, we
            // need to perform this method recursively to be able to get the
            // superclasses' field values too.
            Field[] fields = c.getDeclaredFields();

            // Loop through all fields
            for (Field field : fields) {
                // If the field is transient, then we do not want to copy its
                // value
                if (!field.isAnnotationPresent(Transient.class)) {
                    // The field might be inaccessible, so let's force it to be
                    // accessible.
                    field.setAccessible(true);
                    try {
                        // Copy the value from pojo2 to pojo
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
            // Now perform the same copying of fields to this class's
            // superclass.
            copyFieldsRecursively(pojo, pojo2, c.getSuperclass());
        }
    }
}
