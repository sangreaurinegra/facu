#!/bin/bash

# descargas
#mesDesde = 
#mesHasta = 
#diaDesde
#diaHasta
#horaDesde
#horaHasta

echo "Descargando ..."
# se realiza un hibrido entre fors y rangos porqeu no soporta todos rangos y otras fallas
for i in {01..02};do # meses
 for j in {01..31};do #dias
  wget -P ./descargas http://data.ris.ripe.net/rrc00/2014.$i/updates.2014$i$j.{00..23}{00..55..5}.gz
 done 
done
echo "Fin descarga."
