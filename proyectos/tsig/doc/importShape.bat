REM del C:\tsig\petates\shapefiles\sql
REM mkdir C:\tsig\petates\shapefiles\sql
echo generando Sql ..... 
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\00departamento.shp depa > C:\tsig\petates\shapefiles\sql\depa.sql 
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\00cam_dig.shp camdig > C:\tsig\petates\shapefiles\sql\camdig.sql 
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\00loc_pg.shp locpg > C:\tsig\petates\shapefiles\sql\locpg.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\00vias.shp vias > C:\tsig\petates\shapefiles\sql\vias.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\01esp_libres.shp esplibres > C:\tsig\petates\shapefiles\sql\esplibres.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\01manzanas.shp manzanas > C:\tsig\petates\shapefiles\sql\manzanas.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\01parcelas.shp parcelas > C:\tsig\petates\shapefiles\sql\parcelas.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\01usosuelo.shp usosuelo > C:\tsig\petates\shapefiles\sql\usosuelo.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\01_ejes.shp ejes  > C:\tsig\petates\shapefiles\sql\ejes.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\03cam_dig.shp camdig3  > C:\tsig\petates\shapefiles\sql\camdig3.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\03ejes.shp  ejes3  > C:\tsig\petates\shapefiles\sql\ejes3.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\03esp_libres.shp esplibres3 > C:\tsig\petates\shapefiles\sql\esplibres3.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\03manza_pl.shp manzapl > C:\tsig\petates\shapefiles\sql\manzapl.sql
REM call shp2pgsql -s 32721 -I -W LATIN1 C:\tsig\petates\shapefiles\datosTSIG\03parcelas.shp parcelas3 > C:\tsig\petates\shapefiles\sql\parcelas3.sql

echo importando a la base .....

REM call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\dep.sql -U postgres -w admin
REM call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\camdig.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\locpg.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\vias.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\esplibres.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\manzanas.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\parcelas.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\usosuelo.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\ejes.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\camdig3.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\ejes3.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\esplibres3.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\manzapl.sql -U postgres -w admin
call psql -d postgis2 -f C:\tsig\petates\shapefiles\sql\parcelas3.sql -U postgres -w admin

echo FIN

pause
