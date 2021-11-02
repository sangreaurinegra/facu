

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
