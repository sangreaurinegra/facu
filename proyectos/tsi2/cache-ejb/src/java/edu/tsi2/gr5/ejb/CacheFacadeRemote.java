/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import edu.tsi2.gr5.vo.Cache;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dell
 */
@Remote
public interface CacheFacadeRemote {

    void create(Cache cache);

    void edit(Cache cache);

    void remove(Cache cache);

    Cache find(Object id);

    List<Cache> findAll();

    String buscarEnCache(String clave);

    void guardarEnCache(String clave, String valor);
}
