-- 2843023 2793486

module Direccion where
import ParseLib
--import Comando
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
              deriving (Show,Eq)

-- El tipo de las direcciones es una base seguida de una lista de enteros. Cada entero representa un desplazamiento respecto a la
-- dirección base. Este desplazamiento sera positivo o negativo según el signo del entero.
data Base = Abs Int    -- siempre positivo
          | Ultima
          | Corriente
		  | Marca Char
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


--Un numero (en absoluto) está formado solo por dígitos
numero :: Parse Token Int
numero =  spot (all isDigit) `build` read


-- Una dirección es como una base seguida por una lista de offset (enteros)
direc = (base >*> opes) `build` uncurry Direc


ultima = token "$" `build` const Ultima

corriente = token "." `build` const Corriente


-- Una dir relativa empieza con un signo.
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
operador = (token "+" `build` const 1) `alt` (token "-" `build` const (-1) ) `alt` (succeed 1)     

------------------------------------------------------------------------------

isChar:: Char->Bool
isChar x = elem x ['a'..'z']
