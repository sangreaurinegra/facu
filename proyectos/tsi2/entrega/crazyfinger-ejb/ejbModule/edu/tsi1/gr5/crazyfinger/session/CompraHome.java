package edu.tsi1.gr5.crazyfinger.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import edu.tsi1.gr5.crazyfinger.entity.Compra;
import edu.tsi1.gr5.crazyfinger.entity.Paqueteturistico;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;

@Name("compraHome")
public class CompraHome extends EntityHome<Compra>
{
	@In(create=true)
	UsuarioHome usuarioHome;
	@In(create=true)
	PaqueteturisticoHome paqueteturisticoHome;
	
	public void setCompraIdCompra(Long id){
		setId(id);
	}
	
	public Long getCompraIdCompra(){
		return (Long) getId();
	}
	
	@Override
	protected Compra createInstance(){
		Compra compra=new Compra();
		return compra;
	}
	
	public void load(){
		if(isIdDefined()){
			wire();
		}
	}
	
	public void wire(){
		getInstance();
		Usuario usuario= usuarioHome.getDefinedInstance();
		if(usuario != null){
			getInstance().setUsuario(usuario);
		}
		Paqueteturistico paqueteTuristico = paqueteturisticoHome.getDefinedInstance();
		if(paqueteTuristico != null){
			getInstance().setPaqueteTuristico(paqueteTuristico);
		}
	}
	
	public boolean isWired(){
		if(getInstance().getUsuario() ==null)
			return false;
		if(getInstance().getPaqueteTuristico()==null)
			return false;
		return true;
	}
	
	public Compra getDefinedInstance(){
		return isIdDefined() ? getInstance() : null;
	}
	
/*    @RequestParameter Long compraId;
	
    @Override
    public Object getId()
    {
        if (compraId == null)
        {
            return super.getId();
        }
        else
        {
            return compraId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }
*/
}
