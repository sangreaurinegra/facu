/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tsi2.gr5.ejb;

import edu.tsi2.gr5.vo.Cache;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author dell
 */
@Stateless
public class CacheFacade implements CacheFacadeRemote {

    @PersistenceContext(unitName = "cache-ejbPU")
    private EntityManager em;

    public void create(Cache cache) {
        em.persist(cache);
    }

    public void edit(Cache cache) {
        em.merge(cache);
    }

    public void remove(Cache cache) {
        em.remove(em.merge(cache));
    }

    public Cache find(Object id) {
        return em.find(Cache.class, id);
    }

    public List<Cache> findAll() {
        return em.createQuery("select object(o) from Cache as o").getResultList();
    }

    public String buscarEnCache(String clave) {
        String ret = null;

        try {

            Query q = em.createNamedQuery("Cache.findByClave");
            q.setParameter("clave", clave);
            Cache c = (Cache) q.getSingleResult();
            if(c!=null) ret = c.getValor();
        }catch(NoResultException e){
            System.out.print("ESTA ES MIA :D");
        } finally {
            //em.close();
        }

        return ret;
    }

    public void guardarEnCache(String clave, String valor) {

        if (buscarEnCache(clave) == null) {
            Cache c = new Cache();
            c.setClave(clave);
            c.setValor(valor);
            create(c);
        }

    }
}
