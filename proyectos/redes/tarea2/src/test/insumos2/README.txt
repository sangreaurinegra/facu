Insumos 2 (Obligatorio 2)

- Se presentan dos aplicaciones transmisor.cc y receptor.cc que la primera
pide texto por stdin y lo transmite al proceso receptor, el que lo despliega.

- Está implementado sobre PCT.

-Para invocarlos ejecutar:
#./transmisor 3 127.0.0.3 1 127.0.0.1
con argumentos <puerto_local> <direccionIP_local> <puerto_remoto>  <direccionIP_remoto>

#./receptor 1 127.0.0.1
cuyos argumentos son <puerto_orig> <direccionIP_local>

- Para finalizar la transmisión se debe ejecutar <enter>.<enter>


