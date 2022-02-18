-- Laboratorio 2013 Modulo Direccion
-- Gonzalo Mi�ños 2.843.023-3 gminos@gmail.com - Gabriel Centurion 2.793.486-8 gccomputos@gmail.com

module Direccion where
import ParseLib
import Comando
import Data.Char

--La siguiente gramática representa de manera precisa las direcciones aceptadas por edi (no necesariamente coincide con las de ed)
--	direc = base
--		| direc op numero
--	base = numero
--		| $
--		| .
--		| op numero
--	op = + | -
--Observaciones: numero es un entero sin signo.

---------------- Estructuras -------------------------------------------

--Se define un tipo algebraico que representa la sintaxis abstracta de las direcciones
data Direc = Direc Base [Int]
      deriving (Show, Eq)

-- El tipo de las direcciones es una base seguida de una lista de enteros. Cada entero representa un desplazamiento respecto a la
-- dirección base. Este desplazamiento sera positivo o negativo según el signo del entero.
data Base = Abs Int    -- siempre positivo
          | Ultima
          | Corriente
          | Marca Char    --No implementar
          | Rel Int
      deriving (Show, Eq)


-- Para facilitar el reconocimiento hacemos un lexer basico que elimine los espacios y que agrupe digitos y letras.
-- El parser tomara la salida del lexer.

type Token = String

lexer :: String -> [Token]
lexer "" = []
lexer cs@(a:as)
        | isAlphaNum a = let (us,ws) = span isAlphaNum cs in  us : lexer ws
        | isSpace a    = lexer as
        | otherwise    = [a] : lexer as


----------------------------------------------------------------
-- la base definida según la gramática. Combino todo en una única lista
base :: Parse Token Base
base = nro 
    `alt` ultima 
    `alt` corriente 
    `alt` relativa
nro = numero `build` Abs


--Un numero (sin signo) es un Token formado solo por dígitos
numero :: Parse Token Int
numero =  spot (all isDigit) `build` read


-- Una dirección es como una base seguida por una lista de offset (enteros)
direc = (base >*> opes) `build` uncurry Direc


ultima = token "$" `build` const Ultima

corriente = token "." `build` const Corriente


-- El reconocimiento de las direcciones de la forma 'l donde l es una letra minuscula.   
-- CREO QUE ESTO NO VA ----
marca = ( token "'" >*> spot letraMinuscula) `build` (Marca . head . snd)
        where
            letraMinuscula [c] = isLower c
            letraMinuscula  _  = False

-- Una dir relativa empieza con un signo. Es similar
relativa = (signo >*> numero) `build` (Rel . uncurry (*))
            where             
                signo :: Parse Token Int
                signo = (token "+" `build` const 1) `alt` (token "-" `build` const (-1) )

-- Lista de offsets
opes = list ope
ope :: Parse Token Int
ope = (operador >*> numero) `build` uncurry (*)


-- operador +1 o -1.
operador :: Parse Token Int
operador = (token "+" `build` const 1) `alt` (token "-" `build` const (-1) ) `alt` (succeed 1)      -- operador omitido

------------------------------------------------------------------------------
data Comando = Comando [RangoDireccion] Accion Argumento
		deriving (Show,Eq)


listaDirecciones = list rangosDirecciones
rangosDirecciones = (direc >*> coma) `build` getDireccionMia `alt` (token "," `build` const RangoInicioFinal) `alt` (token ";" `build` const RangoCorrienteFinal)
  
getDireccionMia :: (Direc,Token)->RangoDireccion
getDireccionMia (a,b) = RangoDireccion a

coma = token "," `alt` (succeed [])

argumento =  (listaDirecciones `build` ArgDireccion) `alt` (spot (isChar.head) `build` ArgChar)
direccionTotal = (direccionSimbolo >*> argumento) `build` hacerComando
		where hacerComando ((a,b),c) = Comando a b c

direccionSimbolo = listaDirecciones >*> operando

operando = spot isOperando `build` hacerOperando
		where hacerOperando x
			| x=="a" = Append
			| x=="c" = Change
			| x=="d" = Delete
			| x=="i" = Insert
			| x=="j" = Join
			| x=="m" = Move
			| x=="n" = Printn
			| x=="p" = Printp
			| x=="q" = Quit
			| x=="Q" = QuitUnc
			| x=="t" = Copyt
			| x=="x" = Copyx
			| x=="y" = Copyy
			| x=="w" = Write
			| x=="=" = PrintEqual

data RangoDireccion = RangoDireccion Direc
		| RangoInicioFinal
		| RangoCorrienteFinal
		deriving (Show,Eq)

data Argumento =	ArgDireccion [RangoDireccion]
		|	ArgChar [Char]
		deriving (Show,Eq)

isChar:: Char->Bool
isChar x = elem x ['a'..'z']

isOperando:: Token ->Bool
isOperando x= elem x acc
