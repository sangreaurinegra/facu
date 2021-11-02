package edu.tsi1.gr5.crazyfinger.session;

import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import edu.tsi1.gr5.crazyfinger.entity.Compra;
import edu.tsi1.gr5.crazyfinger.entity.Paqueteturistico;

@Name("compraList")
public class CompraList extends EntityQuery<Compra>
{
    public CompraList()
    {
        setEjbql("select compra from Compra compra");
    }
    
    public List<Compra> listaCompras(String nombre){
		if(nombre.equalsIgnoreCase("admin"))
			return getResultList();
		else{
			Query q = getEntityManager().createQuery(
					"select c from Compra c, IN(c.usuario) u "+
					"where u.nombre = :nombre");

			q.setParameter("nombre", nombre);
			return (List<Compra>)q.getResultList();
		}
	}
}
