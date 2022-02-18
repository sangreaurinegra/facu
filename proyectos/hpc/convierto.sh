#!/bin/bash

FAILURES=0

mkdir -p descargas2bgp

echo "Convirtiendo"
for mrt in `ls descargas`; do
    echo -n "      Convirtiendo $mrt..."
    OUT=$mrt.bgp
    ./bgpdump -vm descargas/$mrt > descargas2bgp/$OUT
    
   #Aca tirarlos para la tabla log_Bgp
   # cmp -i 10 test_out/$OUT test_expect/$OUT
   # if [ $? == 0 ]; then
   #     echo "success"
   # else
   #     FAILURES=$(( $FAILURES + 1 ))
   # fi
done

#if [ $FAILURES != 0 ]; then
#    echo !!! $FAILURES failures !!!
#    exit 1
#else
#    exit 0
#fi
