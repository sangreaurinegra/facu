#!/bin/bash

echo "Ini con $1" > echo.txt

if [ -z "$1" ]
then
        echo "No se recibio el nombre del archivo" >> echo.txt
elif ! [ -f "$1_raw.fits" ]
then
	echo "No se encontro el archivo" >> echo.txt
else
	eval `/root/.ureka/ur_setup`

	touch input.txt
	rm input.txt
	echo "$1" > input.txt

	echo "ECHO 1 DESDE MAP.SH" >> echo.txt

	cl < clInput.txt > cl.out 2> cl.err
	
	echo "ECHO 2 DESDE MAP.SH" >> echo.txt

fi



