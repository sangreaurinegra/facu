Programacion Funcional
----------------------
Abril 2002


Parser para las direcciones de la tarea 2.

Este modulo se presenta como una ayuda y guia para construir
el parser de los comandos. 

Puede ser modificado, corregido, extendido, etc.
(incluso puede prescindirse totalmente de el)

-------------


    Este archivo esta en formato literario, toda linea que no comience
    con el simbolo ">" es considerada un comentario.

    Para utilizar este modulo basta con incluir la siguiente
    clausula en su programa:

        import Direc



    Se utiliza el modulo Parsing, con los combinadores
    basicos de Parsing presentados en el libro de Thomsom.

    En este modulo:

    o  Se construye un parser para las direcciones.

    o  Se define el tipo Direc que representa la sintaxis abstracta
       de las direcciones.

    o  Se define una funcion lexer que parte el string original en tokens.
       Cada token es un string.

    La funcion que realiza el reconocimiento de las direcciones de linea
    es:

          direccion :: Parser String Direc

    Esta funcion retorna una lista de todos los pares de reconocimiento
    tal como se presenta en Thomsom (Cap. 17)

> 	module Direc where
>
> 	import ParseLib
>	import Data.Char
> --       import List

La siguiente es la gramatica que se presenta en la letra:

     direc     =   base
               |   direc numero
               |   direc op numero


     base      =   numero
               |   $
               |   .
               |   'lc
               |   op numero

     op        =  + | - | ^ 

Es posible construir el parser siguiendo directamente la gramatica
anterior. Sin embargo se presenta un problema con la recursion por
la izquierda en la definicion de direc que puede llevar a un loop
infinito en el proceso de parsing.

En su lugar reinterpretamos la definicion de direc a un 
estilo BNF sin utilizar recursion:

      direc ::=  base [op_1] numero_1 ... [op_k] numero_k   (k>=0)

Esto significa que una direccion es una "base" seguida de una lista
(posiblemente vacia) de pares "operador numero" donde el operador
puede omitirse.


Definimos el tipo Direc siguiendo esta idea:

>	data Direc = Direc Base [Int]
>	      deriving (Show,Eq)

El tipo de las direcciones es una base seguida de una lista de enteros.
Cada entero representa un desplazamiento respecto a la direccion
base. Este desplazamiento sera positivo o negativo segun el signo
del entero.


El tipo Base refleja los casos de la gramatica.
	      
>	data Base = Abs Int    -- siempre positivo
>	          | Ultima
>	          | Corriente
> --	          | Marca Char
>	          | Rel Int
>	     deriving (Show,Eq)
>

Ejemplos

    10      ---> Direc (Abs 10) []
   -10      ---> Direc (Rel -10) []
   $-5 2    ---> Direc  Ultima [-5,2]
   +4 -3    ---> Direc (Rel 4) [-3]
   'a+3-2 1 ---> Direc (Marca 'a') [3,-2,1]


Para facilitar el reconocimiento hacemos un lexer basico
que elimine los espacios y que agrupe digitos y letras.

El parser tomara la salida del lexer.

>	type Token = String
>
>	lexer :: String -> [Token]
>       lexer "" = []
>	lexer cs@(a:as) 
>           | isDigit a = let (us,ws) = span isDigit cs
>		             in  us : lexer ws
>	    | isSpace a    = lexer as
>           | otherwise    = [a] : lexer as


Ejemplos:

lexer "hola,num 1234+12"  ---> ["hola",",","num","1234","+","12"]
lexer "'a-14"             ---> ["'","a","-","14"]

-------------------------------------------

Definicion del Parser de direcciones:

Un numero (sin signo) es un Token que esta formado solo por digitos:
Directamente le aplicamos la funcion estandar "read" para
retornar un Int

>       numero :: Parse Token Int
> 	numero =  spot (all isDigit) `build` read

Una direccion es una base seguida por una lista
de desplazamientos (enteros)


>	direc = (base >*> opes) `build` uncurry Direc


La base se reconoce siguiendo las alternativas dadas por la gramatica

>       base :: Parse Token Base
>	base = num 
>               `alt` 
>	       ultima 
>                `alt` 
>	       corriente 
> --              `alt` 
> --	       marca
>                `alt`
>	       relativa
	
>	num = numero `build` Abs
	
Reconocemos los simbolos $ y . realizando la conversion
al tipo Direc.

La funcion estandar const resulta de utilidad para
retornar un valor que no depende del token reconocido.

>	ultima = token "$" `build` const Ultima	
>
>	corriente = token "." `build` const Corriente

El reconocimiento de las direcciones de la forma 'l
donde l es una letra minuscula.
    
> {-	marca = ( token "'" 
>	         >*>
>	         spot letraMinuscula
>	        ) `build` (Marca . head . snd)
>	     where
>                letraMinuscula [c] = isLower c
>	         letraMinuscula  _  = False         -}

La direccion relativa empieza con un signo. Es similar
al reconocimiento de los desplazamientos (ver mas abajo)
 pero no se puede omitir el signo:

> 	relativa = (signo
>	            >*>
>	            numero
>	           ) `build` (Rel . uncurry (*))
>             where             
>       	signo :: Parse Token Int
>       	signo = (token "+" `build` const 1)
>	                `alt`
>	                (token "-" `build` const (-1) )

Reconocimiento de la lista de desplazamientos:

>
>	opes = list ope
>
>       ope :: Parse Token Int
>       ope = (operador
>               >*>
>               numero
>	       )
>              `build` uncurry (*)
>

El operador se reconoce como +1 o -1.

>       operador :: Parse Token Int
>       operador = (token "+" `build` const 1)
>	            `alt`
>	           (token "-" `build` const (-1) )
>	            `alt`
>	           (succeed 1)      -- operador omitido


>       isChar :: Char->Bool
>       isChar x = elem x ['a'..'z']
