/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import edu.tsi2.gr5.vo.LogActividades;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rodrigo
 */
@Stateless
public class LogActividadesFacade implements LogActividadesFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(LogActividades logActividades) {
        em.persist(logActividades);
    }

    public void edit(LogActividades logActividades) {
        em.merge(logActividades);
    }

    public void remove(LogActividades logActividades) {
        em.remove(em.merge(logActividades));
    }

    public LogActividades find(Object id) {
        return em.find(LogActividades.class, id);
    }

    public List<LogActividades> findAll() {
        return em.createQuery("select object(o) from LogActividades as o").getResultList();
    }

    public void loguear(String ws,String oper,String param,Integer result,Integer enCache){
        LogActividades log = new LogActividades();
        log.setEnCache(enCache);
        log.setFechaAdicion(new Date());
        log.setOperacion(oper);
        log.setParametros(param);
        log.setResultado(result);
        log.setWebService(ws);
        create(log);
    }

}
