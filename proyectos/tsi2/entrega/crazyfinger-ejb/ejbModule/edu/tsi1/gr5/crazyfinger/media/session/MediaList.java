package edu.tsi1.gr5.crazyfinger.media.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("mediaList")
public class MediaList extends EntityQuery<Media> {
	
	@In
	Usuario usuario;

	private static final String EJBQL = "select media from Media media";

	private static final String[] RESTRICTIONS = {
			"lower(media.datosextras) like lower(concat(#{mediaList.media.datosextras},'%'))",
			"lower(media.url) like lower(concat(#{mediaList.media.url},'%'))", };

	private Media media = new Media();

	public MediaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Media getMedia() {
		return media;
	}
	
	List<Media> misMedias;

	public List<Media> getMisMedias() {
		if(misMedias == null)
			misMedias = cargarMisMedias();
		return misMedias;
	}

	public void setMisMedias(List<Media> misMedias) {
		this.misMedias = misMedias;
	}
	
	private List<Media> cargarMisMedias(){
		
		Query q = getEntityManager().createQuery("select m from Media m where m.usuario.idUsuario=:idusuario");
		
		q.setParameter("idusuario", usuario.getIdUsuario());
		List<Media> l = (List<Media>)q.getResultList();
		if (l != null ) {
			return l;
		}
		return new ArrayList<Media>();
	}
}
