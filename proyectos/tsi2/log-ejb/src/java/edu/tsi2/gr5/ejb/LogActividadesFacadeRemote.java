/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import edu.tsi2.gr5.vo.LogActividades;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Rodrigo
 */
@Remote
public interface LogActividadesFacadeRemote {

    void create(LogActividades logActividades);

    void edit(LogActividades logActividades);

    void remove(LogActividades logActividades);

    LogActividades find(Object id);

    List<LogActividades> findAll();

    public void loguear(String ws,String oper,String param,Integer result,Integer enCache);

}
