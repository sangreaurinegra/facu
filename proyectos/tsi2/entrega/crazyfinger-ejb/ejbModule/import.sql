

-- inserto usuario por defecto
INSERT INTO usuario VALUES (1,"admin","admin","http://tsi2009.myopenid.com/",1,1);


-- NEW _ VER ---
 
INSERT INTO feed (idfeed, link, subtitle, title, uid) VALUES (1,'{crazy.domain}rss', 'Las News del dia','Noticias', 1);
INSERT INTO feed (idfeed, link, subtitle, title, uid) VALUES (2,'{crazy.domain}rss', 'Nuestras Ofertas!!','Paquetes Turisticos', 2);
INSERT INTO feed (idfeed, link, subtitle, title, uid) VALUES (3,'{crazy.domain}rss', 'Proyectos de Viajes de nuestros clientes!!','Proyectos de Viaje', 2);


INSERT INTO entry (identry, uid, title, link, author,  summary,feed)
 VALUES (1,'1','Peñarol Noma','{crazy.domain}rss','author maferoga', 'El mejor cuadro del siglo pasado sigue demostrando que es lo asm grande que hay!!!',1);
INSERT INTO entry (identry, uid, title, link, author,  summary,feed)
 VALUES (2,'2','Bailando por un Caño','{crazy.domain}rss','author carlitos','Acopio de armas podria tener connotaciones politicas , artitmeticas o extraterrestres según Batlle, Marenales dijo que no es extraterrestre y pidio pericia psiquiatrica, Lacalle apoyo a Batlle y pidio uno en las rocas, José Mujica dijo que el si es extraterrestre pero nada tenia que ver con las armas y pidió que se cuiden los dichos coloquiales',1);

INSERT INTO entry (identry, uid, title, link, author,  summary,feed)
VALUES (3,'3','Florianopolis para la Familia','{crazy.domain}rss','author maferoga','Vacaciones en Brasil',2);
INSERT INTO entry (identry, uid, title, link, author,  summary,feed)
VALUES (4,'4','Buenos Aires Libros','{crazy.domain}rss','author pepe','Conozca El Ateno la mayor libreria de latino america',2);

INSERT INTO entry (identry, uid, title, link, author,  summary,feed)
VALUES (5,'5','Pepe nos vamos de PezK','{crazy.domain}rss','author maferoga','pesca embarcado inmediaciones de Gorriti ',3);
INSERT INTO entry (identry, uid, title, link, author,  summary,feed)
VALUES (6,'6','Cruzando las cordilleras','{crazy.domain}rss','author pepe','relizaremos un cruce de las cordilleras en bicicleta , venis ?',3);

-- AGREGAMOS LOS MEDIOS DE TRANSPORTE QUE SON FIJOS --

INSERT INTO mediotransporte (id_mediotransporte, tipo, url) VALUES (1, 'avion', 'img/silueta_avion.jpg');
INSERT INTO mediotransporte (id_mediotransporte, tipo, url) VALUES (2, 'barco', 'img/silueta_barco.jpg');
INSERT INTO mediotransporte (id_mediotransporte, tipo, url) VALUES (3, 'tren', 'img/silueta_tren.jpg');


--  AGREGAMOS LUGARES --

INSERT INTO lugar (id_lugar, latitud, longitud, nombre, url, pais) VALUES (7, -34.8580556, -56.170833299999998, 'Montevideo', 'img/mvdeo.jpeg', 'Uruguay');
INSERT INTO lugar (id_lugar, latitud, longitud, nombre, url, pais) VALUES (243, 48.866666700000003, 2.3333333000000001, 'Paris', 'img/paris.jpeg', 'Francia');
INSERT INTO lugar (id_lugar, latitud, longitud, nombre, url, pais) VALUES (244, 51.516666700000002, -0.10000000000000001, 'Londres', 'img/londres.jpeg', 'Reino Unido');
INSERT INTO lugar (id_lugar, latitud, longitud, nombre, url, pais) VALUES (245, -27.5833333, -48.566666699999999, 'Florianopolis', 'img/florianopolis.jpeg', 'Brasil');
INSERT INTO lugar (id_lugar, latitud, longitud, nombre, url, pais) VALUES (246, 21.0833333, -86.766666700000002, 'Cancun', 'img/cancun.jpeg', 'Mexico');
INSERT INTO lugar (id_lugar, latitud, longitud, nombre, url, pais) VALUES (6, -34.587499999999999, -58.672499999999999, 'Buenos Aires', 'img/buenos_aires.jpeg', 'Argentina');



-- AGREGAMOS TRASLADOS --

INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (1, 123, 'Pluna', '9:00', 7, 245, 1, 7, 245);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (2, 234, 'Buquebus', '12:32', 6, 7, 2, 6, 7);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (3, 456, 'AFE', '14:23', 7, 6, 3, 7, 6);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (4, 345, 'Buquebus', '06:00', 245, 7, 2, 245, 7);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (374, 678, 'American Airlines', '5:56', 246, 7, 1, 246, 7);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (379, 876, 'Tam', '14:23', 7, 246, 1, 7, 246);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (485, 1234, 'Air France', '5:56', 243, 7, 1, 243, 7);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (523, 456, 'Air France', '23:45', 243, 246, 1, 243, 246);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (524, 235, 'American Airlines', '7:45', 244, 243, 1, 244, 243);
INSERT INTO traslado (id_traslado, costo, empresa, horario, destino, origen, mediotransporte_id_mediotransporte, idlugarbydestino, idlugarbyorigen) VALUES (525, 876, 'Royal Air Force', '12:12', 7, 244, 1, 7, 244);


-- AGREGAMOS SERVICIOS --

INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (228, 85, 'Pocitos Plaza Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/MVD_POCI-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (229, 169, 'Radisson Montevideo Victoria Plaza Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LAT_MONT-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (230, 175, 'Sheraton Montevideo Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_1527-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (231, 105, 'Tryp Montevideo', 'hotel', 'http://www.hoteles.com/hotels/thumbs/MVD_TRYP-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (232, 60, 'Holiday Inn MONTEVIDEO', 'hotel', 'http://www.hoteles.com/hotels/thumbs/MVD_MONT-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (233, 70, 'Hotel Lafayette', 'hotel', 'http://www.hoteles.com/images/comingSoon.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (234, 63, 'Days Inn Montevideo Ur', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CDT_OBEL-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (235, 151, 'Regency Suites', 'hotel', 'http://www.hoteles.com/hotelimages/s/068000/068088A-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (236, 83, 'Armon Suites', 'hotel', 'http://www.hoteles.com/hotelimages/s/058000/058519A-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (237, 101, 'Nh Columbia', 'hotel', 'http://www.hoteles.com/hotels/thumbs/MVD_NHCO-exter-1-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (238, 80.099999999999994, 'Best Western Pedro Figari', 'hotel', 'http://www.hoteles.com/hotelimages/s/063000/063063A-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (239, 120, 'Four Points Montevideo', 'hotel', 'http://www.hoteles.com/hotelimages/s/025000/025734A-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (240, 110, 'Cala Di Volpe Boutique Hotel', 'hotel', 'http://www.hoteles.com/hotelimages/s/053000/053511A-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (241, 105, 'Regency Zm', 'hotel', 'http://www.hoteles.com/hotelimages/s/075000/075170A-thumb.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (242, NULL, 'Regency Golf', 'hotel', 'http://www.hoteles.com/images/comingSoon.jpg', 7);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (247, 218, 'Marriott Plaza Hotel Buenos Aires', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_MARR-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (248, 213, 'Park Plaza Unique Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_KEPL-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (249, 66, 'Buenos Aires Wilton Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_WILT-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (250, 335, 'Caesar Park Buenos Aires', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_CAES-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (251, 80, 'Loisuites Esmeralda', 'hotel', 'http://www.hoteles.com/hotels/condos/thumbs/14051-pe1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (252, 460, 'Palacio Duhau - Park Hyatt Buenos Aires', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_PAHY-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (253, 67, 'Hotel Republica Wellness & Spa', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_ROBH-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (254, 70, 'RH Rochester Classic', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_ROCH-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (255, 76, 'Axel Hotel Buenos Aires', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_AXEL-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (256, 177, 'Mine Hotel Boutique', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EZE_MHBO-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (257, 183, 'Pestana Buenos Aires Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_PEST-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (258, 280, 'Hollywood Suites & Lofts', 'hotel', 'http://www.hoteles.com/hotels/condos/thumbs/15739-pe1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (259, 129, 'Moreno Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_MORE-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (260, 66, 'RH Rochester Concept', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_CEPT-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (261, 91, 'Conte Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_CONT-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (262, 105, 'Park Chateau Unique Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_KECH-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (263, 125, 'Claridge Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_CLAR-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (264, 120, 'Park Elegance Unique Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_KEEL-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (265, 186, 'Hollywood Suites & Lofts 2 - Las Suites', 'hotel', 'http://www.hoteles.com/hotels/condos/thumbs/16129-pe1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (266, 140, 'Park Central Unique Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_KEPC-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (267, 139, 'Sheraton Libertador Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_2661-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (268, 208, 'Design Suites Buenos Aires', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_DESS-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (269, 175, 'Dazzler Tritone', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_TRIT-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (270, 164, 'Hilton Buenos Aires', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_1471-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (271, 120, 'Savoy Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/BUE_TULI-exter-1-thumb.jpg', 6);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (272, 125, 'Baia Norte Othon Classic', 'hotel', 'http://www.hoteles.com/hotels/thumbs/FLI_BAIA-exter-1-thumb.jpg', 245);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (273, 76, 'Mercure Apt Itacorubi 3', 'hotel', 'http://www.hoteles.com/images/comingSoon.jpg', 245);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (274, 72, 'Mercure Apt Lindacap', 'hotel', 'http://www.hoteles.com/hotels/thumbs/FLN_MEFL-exter-1-thumb.jpg', 245);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (275, 109, 'Sofitel Florianopolis', 'hotel', 'http://www.hoteles.com/hotels/thumbs/GRU_SOFL-exter-1-thumb.jpg', 245);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (276, 165, 'Hotel Butique Quinta das Videiras', 'hotel', 'http://www.hoteles.com/images/comingSoon.jpg', 245);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (277, 212, 'The Strand Palace', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_STRA-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (278, 141, 'Enterprise Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_ENTE-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (279, 236, 'The Grand at Trafalgar Square', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_RAND-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (280, 135, 'The International Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_BRIN-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (281, 237, 'Radisson Edwardian Bloomsbury Street Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_RMAR-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (282, 206, 'The Rembrandt', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_REMB-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (283, 227, 'The Cumberland - a Guoman Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_CUMB-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (284, 230, 'The Kingsley - formerly Thistle Bloomsbury', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_THBL-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (285, 135, 'Shaftesbury Hyde Park International', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_SHBA-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (286, 145, 'Club Quarters, Gracechurch', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_CLUB-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (287, 108, 'Westbury Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_WKEN-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (288, 213, 'Park Plaza Riverbank London', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_RIVE-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (289, 184, 'Quality Crown Hotel Paddington', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_CROW-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (290, 280, 'The May Fair', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_2730-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (291, 276, 'Renaissance Chancery Court', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_1485-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (292, 102, 'The Hyde Park Towers Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_HYPT-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (293, 230, 'Lancaster London formerly Royal Lancaster', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_ROLA-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (294, 89, 'Wedgewood Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_WEDG-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (295, 166, 'Copthorne Tara Hotel London Kensington', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_COPT-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (296, 165, 'Rowhill Grange Hotel & Utopia Spa', 'hotel', 'http://www.hoteles.com/hotels/thumbs/GTW_ROWH-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (297, 136, 'Best Western The Cromwell', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_STUA-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (298, 293, 'The Langham, London', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_LGHM-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (299, 85, 'Boka Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_BOKE-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (300, 179, 'Hotel Russell', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_RUSS-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (301, 145, 'Hilton London Kensington', 'hotel', 'http://www.hoteles.com/hotels/thumbs/LON_HILK-exter-1-thumb.jpg', 244);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (302, 77, 'Hyatt Regency Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_HYAR-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (303, 160, 'GR Solaris Cancun & Spa - All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_GSOL-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (304, 127, 'Gran Melia Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_MSPA-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (305, 133, 'Ambiance Villas', 'hotel', 'http://www.hoteles.com/hotels/condos/thumbs/12473-pe1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (306, 91, 'Hotel Cancun Clipper Club', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_CLIP-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (307, 130, 'Le Meridien Cancun Resort and Spa', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_LEME-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (308, 133, 'Royal Solaris Cancun Resort, Marina & Spa -All In', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_RSOL-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (309, 121, 'NH Krystal Cancun Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_NHKR-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (310, 246, 'Fiesta Americana Grand Coral Beach', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_GCBC-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (311, 129, 'Fiesta Americana Villas Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_FIES-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (312, 71, 'Bel Air Collection Hotel & Spa Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_BELA-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (313, 278, 'Great Parnassus Resort and Spa All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_PARN-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (314, 120, 'Park Royal Cancun All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_PARK-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (315, 378, 'Beach Palace All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_BEAP-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (316, 108, 'InterContinental Presidente Cancun Resort', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_PICC-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (317, 99, 'ME by Melia Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_TURQ-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (318, 99, 'Hotetur Beach Paradise All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_HOTE-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (319, 296, 'Oasis Viva Beach All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_CALI-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (320, 66, 'Cancun Caribe Park Royal Grand', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_HYAT-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (321, 145, 'Fiesta Americana Condesa Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_COCA-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (322, 243, 'Riu Caribe All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_GRAC-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (323, 198, 'Barcelo Tucancun Beach All Inclusive', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_BTBR-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (324, 72, 'Courtyard By Marriott Cancun', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_4354-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (325, 296, 'Oasis Palm Beach The Family Resort & Spa All Incl', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_CABE-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (326, 80, 'Hilton Cancun Golf & Spa Resort', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CUN_HILT-exter-1-thumb.jpg', 246);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (327, 244, 'Hotel Concorde La Fayette', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_CORD-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (328, 276, 'K&K Hotel Cayre', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_CAYR-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (329, 270, 'Saint James & Albany Hotel - Spa', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_JAME-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (330, 390, 'Renaissance Arc De Triomphe', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_4762-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (333, 787, 'Hotel De Crillon', 'hotel', 'http://www.hoteles.com/hotels/thumbs/EVT_2170-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (334, 212, 'Astor Saint-HonorÃ©', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_STHO-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (335, 239, 'Le Meridien Montparnasse', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_LEMM-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (336, 293, 'Hotel du Louvre', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_DULO-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (337, 344, 'Hotel Lutetia', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_LUTI-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (338, 284, 'Concorde St Lazare Opera', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_COST-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (339, 291, 'Hotel Baltimore Paris - MGallery Collection', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_SOBA-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (340, 237, 'Hotel le Marquis', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_LEMA-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (341, 320, 'Artus Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_ARTU-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (342, 174, 'Brebant Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_BREB-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (343, 140, 'Little Palace Hotel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_TULI-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (344, 145, 'Ideal Hotel Design', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_IDEA-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (345, 387, 'Sofitel Paris Le Faubourg', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_SOFA-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (346, 167, 'Hotel Eiffel Capitol', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_TIEC-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (347, 282, 'Pullman Paris Tour Eiffel', 'hotel', 'http://www.hoteles.com/hotels/thumbs/HH_PARIS-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (348, 181, 'Villa Opera Drouot', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_VILO-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (349, 168, 'Villa Luxembourg', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_VILM-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (350, 551, 'Hotel de Vendome', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_VDOM-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (351, 267, 'Hotel Magenta Paris', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_MAGE-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (557, 211, 'Mama Shelter', 'hotel', 'http://www.hoteles.com/hotels/thumbs/CDG_MAMA-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (331, 339, 'The Westin Paris', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_INTE-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (332, 296, 'Hotel Claridge Paris', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_CLAB-exter-1-thumb.jpg', 243);
INSERT INTO servicio (id_servicio, costo, nombre, tipo, url, lugar) VALUES (558, 104, 'Hotel de Maubeuge, Gare du Nord', 'hotel', 'http://www.hoteles.com/hotels/thumbs/PAR_MAUB-exter-1-thumb.jpg', 243);


-- AGREGAMOS ALOJAMIENTOS --

INSERT INTO alojamiento (id_servicio) VALUES (228);
INSERT INTO alojamiento (id_servicio) VALUES (229);
INSERT INTO alojamiento (id_servicio) VALUES (230);
INSERT INTO alojamiento (id_servicio) VALUES (231);
INSERT INTO alojamiento (id_servicio) VALUES (232);
INSERT INTO alojamiento (id_servicio) VALUES (233);
INSERT INTO alojamiento (id_servicio) VALUES (234);
INSERT INTO alojamiento (id_servicio) VALUES (235);
INSERT INTO alojamiento (id_servicio) VALUES (236);
INSERT INTO alojamiento (id_servicio) VALUES (237);
INSERT INTO alojamiento (id_servicio) VALUES (238);
INSERT INTO alojamiento (id_servicio) VALUES (239);
INSERT INTO alojamiento (id_servicio) VALUES (240);
INSERT INTO alojamiento (id_servicio) VALUES (241);
INSERT INTO alojamiento (id_servicio) VALUES (242);
INSERT INTO alojamiento (id_servicio) VALUES (247);
INSERT INTO alojamiento (id_servicio) VALUES (248);
INSERT INTO alojamiento (id_servicio) VALUES (249);
INSERT INTO alojamiento (id_servicio) VALUES (250);
INSERT INTO alojamiento (id_servicio) VALUES (251);
INSERT INTO alojamiento (id_servicio) VALUES (252);
INSERT INTO alojamiento (id_servicio) VALUES (253);
INSERT INTO alojamiento (id_servicio) VALUES (254);
INSERT INTO alojamiento (id_servicio) VALUES (255);
INSERT INTO alojamiento (id_servicio) VALUES (256);
INSERT INTO alojamiento (id_servicio) VALUES (257);
INSERT INTO alojamiento (id_servicio) VALUES (258);
INSERT INTO alojamiento (id_servicio) VALUES (259);
INSERT INTO alojamiento (id_servicio) VALUES (260);
INSERT INTO alojamiento (id_servicio) VALUES (261);
INSERT INTO alojamiento (id_servicio) VALUES (262);
INSERT INTO alojamiento (id_servicio) VALUES (263);
INSERT INTO alojamiento (id_servicio) VALUES (264);
INSERT INTO alojamiento (id_servicio) VALUES (265);
INSERT INTO alojamiento (id_servicio) VALUES (266);
INSERT INTO alojamiento (id_servicio) VALUES (267);
INSERT INTO alojamiento (id_servicio) VALUES (268);
INSERT INTO alojamiento (id_servicio) VALUES (269);
INSERT INTO alojamiento (id_servicio) VALUES (270);
INSERT INTO alojamiento (id_servicio) VALUES (271);
INSERT INTO alojamiento (id_servicio) VALUES (272);
INSERT INTO alojamiento (id_servicio) VALUES (273);
INSERT INTO alojamiento (id_servicio) VALUES (274);
INSERT INTO alojamiento (id_servicio) VALUES (275);
INSERT INTO alojamiento (id_servicio) VALUES (276);
INSERT INTO alojamiento (id_servicio) VALUES (277);
INSERT INTO alojamiento (id_servicio) VALUES (278);
INSERT INTO alojamiento (id_servicio) VALUES (279);
INSERT INTO alojamiento (id_servicio) VALUES (280);
INSERT INTO alojamiento (id_servicio) VALUES (281);
INSERT INTO alojamiento (id_servicio) VALUES (282);
INSERT INTO alojamiento (id_servicio) VALUES (283);
INSERT INTO alojamiento (id_servicio) VALUES (284);
INSERT INTO alojamiento (id_servicio) VALUES (285);
INSERT INTO alojamiento (id_servicio) VALUES (286);
INSERT INTO alojamiento (id_servicio) VALUES (287);
INSERT INTO alojamiento (id_servicio) VALUES (288);
INSERT INTO alojamiento (id_servicio) VALUES (289);
INSERT INTO alojamiento (id_servicio) VALUES (290);
INSERT INTO alojamiento (id_servicio) VALUES (291);
INSERT INTO alojamiento (id_servicio) VALUES (292);
INSERT INTO alojamiento (id_servicio) VALUES (293);
INSERT INTO alojamiento (id_servicio) VALUES (294);
INSERT INTO alojamiento (id_servicio) VALUES (295);
INSERT INTO alojamiento (id_servicio) VALUES (296);
INSERT INTO alojamiento (id_servicio) VALUES (297);
INSERT INTO alojamiento (id_servicio) VALUES (298);
INSERT INTO alojamiento (id_servicio) VALUES (299);
INSERT INTO alojamiento (id_servicio) VALUES (300);
INSERT INTO alojamiento (id_servicio) VALUES (301);
INSERT INTO alojamiento (id_servicio) VALUES (302);
INSERT INTO alojamiento (id_servicio) VALUES (303);
INSERT INTO alojamiento (id_servicio) VALUES (304);
INSERT INTO alojamiento (id_servicio) VALUES (305);
INSERT INTO alojamiento (id_servicio) VALUES (306);
INSERT INTO alojamiento (id_servicio) VALUES (307);
INSERT INTO alojamiento (id_servicio) VALUES (308);
INSERT INTO alojamiento (id_servicio) VALUES (309);
INSERT INTO alojamiento (id_servicio) VALUES (310);
INSERT INTO alojamiento (id_servicio) VALUES (311);
INSERT INTO alojamiento (id_servicio) VALUES (312);
INSERT INTO alojamiento (id_servicio) VALUES (313);
INSERT INTO alojamiento (id_servicio) VALUES (314);
INSERT INTO alojamiento (id_servicio) VALUES (315);
INSERT INTO alojamiento (id_servicio) VALUES (316);
INSERT INTO alojamiento (id_servicio) VALUES (317);
INSERT INTO alojamiento (id_servicio) VALUES (318);
INSERT INTO alojamiento (id_servicio) VALUES (319);
INSERT INTO alojamiento (id_servicio) VALUES (320);
INSERT INTO alojamiento (id_servicio) VALUES (321);
INSERT INTO alojamiento (id_servicio) VALUES (322);
INSERT INTO alojamiento (id_servicio) VALUES (323);
INSERT INTO alojamiento (id_servicio) VALUES (324);
INSERT INTO alojamiento (id_servicio) VALUES (325);
INSERT INTO alojamiento (id_servicio) VALUES (326);
INSERT INTO alojamiento (id_servicio) VALUES (327);
INSERT INTO alojamiento (id_servicio) VALUES (328);
INSERT INTO alojamiento (id_servicio) VALUES (329);
INSERT INTO alojamiento (id_servicio) VALUES (330);
INSERT INTO alojamiento (id_servicio) VALUES (331);
INSERT INTO alojamiento (id_servicio) VALUES (332);
INSERT INTO alojamiento (id_servicio) VALUES (333);
INSERT INTO alojamiento (id_servicio) VALUES (334);
INSERT INTO alojamiento (id_servicio) VALUES (335);
INSERT INTO alojamiento (id_servicio) VALUES (336);
INSERT INTO alojamiento (id_servicio) VALUES (337);
INSERT INTO alojamiento (id_servicio) VALUES (338);
INSERT INTO alojamiento (id_servicio) VALUES (339);
INSERT INTO alojamiento (id_servicio) VALUES (340);
INSERT INTO alojamiento (id_servicio) VALUES (341);
INSERT INTO alojamiento (id_servicio) VALUES (342);
INSERT INTO alojamiento (id_servicio) VALUES (343);
INSERT INTO alojamiento (id_servicio) VALUES (344);
INSERT INTO alojamiento (id_servicio) VALUES (345);
INSERT INTO alojamiento (id_servicio) VALUES (346);
INSERT INTO alojamiento (id_servicio) VALUES (347);
INSERT INTO alojamiento (id_servicio) VALUES (348);
INSERT INTO alojamiento (id_servicio) VALUES (349);
INSERT INTO alojamiento (id_servicio) VALUES (350);
INSERT INTO alojamiento (id_servicio) VALUES (351);
INSERT INTO alojamiento (id_servicio) VALUES (557);
INSERT INTO alojamiento (id_servicio) VALUES (558);

