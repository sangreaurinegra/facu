
use scrumme

INSERT INTO tipo_usuario VALUES (1,0,'ScrumMaster'),(2,0,'Propietario del producto'),(3,0,'Miembro del equipo');


INSERT INTO role VALUES (1,1,'ROLE_USER','rol de usuario final');


INSERT INTO usuario VALUES (1,1,'','f63b096a3073587a26e0e10999a60c7efd3293f7','Maxi F','maxi','\0','mf','maxi.felix@gmail.com','',3);

INSERT INTO role_people VALUES (1,1);

insert into tipo_tarea(version,nombre,color) values (1,'Test','0000ff');

insert into tipo_tarea(version,nombre,color) values (1,'Implementacion','ff00ff');

insert into tipo_tarea(version,nombre,color) values (1,'Documentacion','00ff00');

insert into tipo_tarea(version,nombre,color) values (1,'Otras','00ffff');