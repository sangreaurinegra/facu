Insumos Obligatorio 2

- pct.h archivo con cabezales de funciones y variables de uso general

- Codigos fuentes send.c y receive.c con un ejemplo de como realizar el 
armado de un paquete IP y su envío por la red. La compilación de los mismos
se encuentra presentada en el makefile provisto.

- Codigos fuentes de enviaFile.cc y recibeFile.cc ejemplo de procedimientos que 
utilizan PCT. Estos se compilan con el archivo pct.o, y establecen una conexión 
y envían un archivo pasado por parámetro. 

- Para el funcionamiento de los raw sockets utilizados se requieren privilegios
de root, por lo que las aplicaciones que utilicen PCT debe ejecutarse con 
permisos de root con sudo. 
Ejemplo:
sudo ./enviaFile 3 127.0.0.3 1 127.0.0.1 logo_globedia.gif
sudo ./recibeFile 1 127.0.0.1 rec_globedia.gif
Las direcciones IP utilizadas deben estar definidas para la interfaz lo, por lo 
que son del tipo 127.0.0.X

- Shell script netEmulator.sh que permite generar pérdidas y duplicación en
la red. Los parámetros aplicados a la red se modifican editanto el script y 
modificando las diferentes variables.


