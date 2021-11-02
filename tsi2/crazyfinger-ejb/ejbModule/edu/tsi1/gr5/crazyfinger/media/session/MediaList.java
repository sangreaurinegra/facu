package edu.tsi1.gr5.crazyfinger.media.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("mediaList")
public class MediaList extends EntityQuery<Media> {

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
}
