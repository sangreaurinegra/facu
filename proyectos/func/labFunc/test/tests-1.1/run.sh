#!/bin/sh
# Tests
echo
echo "/*******/"
echo "| Tests |"
echo "/*******/"
echo
echo Son 12 tests, donde el último llama a edi con un archivo inexistente.
echo Se testea el resultado en el archivo,
echo no el output que genera edi al escribirle a él.
echo
echo El ejecutable edi debe estar en esta carpeta.
echo Los .in son comandos, .out serán los archivo abiertos con ed,
echo los .esp son la salida que me generó a mi y los .diff las diferencias.
echo
echo Comienza en 10 segundos...
echo
sleep 10

echo 1\) Primero se copia el archivo original para ser usado en cada test.
sleep 2
for i in `seq 1 11`
do
  cp original.txt prueba$i.out
done
echo

echo 2\) Se borra la salida de la última prueba, que crea un archivo nuevo.
sleep 2
rm -f prueba12.out
echo

echo 3\) Se ejecutan la pruebas.
echo Si alguna queda esperando una respuesta \(o da "edi: <stdin>: hGetLine: end of file"\), entonces hubo algún error \(puede ser en las pruebas\).
sleep 2
for i in `seq 1 12`
do
  ./edi prueba$i.out < prueba$i.in > /dev/null
done
echo

echo 4\) Comparo con mis resultados con diff \(pueden estar mal\):
sleep 2
for i in `seq 1 12`
do
  diff prueba$i.esp prueba$i.out > prueba$i.diff
  if cmp -s prueba$i.esp prueba$i.out; then
	echo "Prueba $i pasada."
  else
	echo "Prueba $i NO pasada."
  fi
done
echo
echo Por más detalles, ver los .diff.
echo
sleep 1

echo Fin!
sleep 2
