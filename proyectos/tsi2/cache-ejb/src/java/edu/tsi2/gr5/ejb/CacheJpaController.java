/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import edu.tsi2.gr5.ejb.exceptions.NonexistentEntityException;
import edu.tsi2.gr5.ejb.exceptions.PreexistingEntityException;
import edu.tsi2.gr5.ejb.exceptions.RollbackFailureException;
import edu.tsi2.gr5.vo.Cache;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;

/**
 *
 * @author dell
 */
public class CacheJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "cache-ejbPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cache cache) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(cache);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCache(cache.getId()) != null) {
                throw new PreexistingEntityException("Cache " + cache + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cache cache) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            cache = em.merge(cache);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cache.getId();
                if (findCache(id) == null) {
                    throw new NonexistentEntityException("The cache with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cache cache;
            try {
                cache = em.getReference(Cache.class, id);
                cache.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cache with id " + id + " no longer exists.", enfe);
            }
            em.remove(cache);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cache> findCacheEntities() {
        return findCacheEntities(true, -1, -1);
    }

    public List<Cache> findCacheEntities(int maxResults, int firstResult) {
        return findCacheEntities(false, maxResults, firstResult);
    }

    private List<Cache> findCacheEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cache as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cache findCache(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cache.class, id);
        } finally {
            em.close();
        }
    }

    public int getCacheCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Cache as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }


    public Cache buscarEnCache(String clave){
        Cache ret=null;

         EntityManager em = getEntityManager();
         try {
            Query q = em.createQuery("select object(o) from Cache as o where o.clave="+clave);
            ret=(Cache) q.getSingleResult();

        } finally {
            em.close();
        }

        return ret;
    }

    public void guardarEnCache(String clave, String valor) throws PreexistingEntityException, RollbackFailureException, Exception{
        Cache c = new Cache();
        c.setClave(clave);
        c.setValor(valor);
        create(c);
    }

}
