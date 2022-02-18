module DireccionG where
import ParseLib
import Data.Char

data Direc = Direc Base [Int]
	deriving (Show,Eq)

data Base = Abs Int    -- siempre positivo
		| Ultima
		| Corriente
		| Rel Int
	deriving (Show,Eq)


type Token = String

lexer :: String -> [Token]
lexer "" = []
lexer cs@(a:as) 
	| isDigit a = let (us,ws) = span isDigit cs -- realizamos la modificacion por idDigit para que el parse separe los com de los num
				in  us : lexer ws
	| isSpace a    = lexer as
	| otherwise    = [a] : lexer as

oper = ["a","c","d","i","j","m","n","p","q","Q","t","x","y","w","="]


numero :: Parse Token Int
numero =  spot (all isDigit) `build` read


direc :: Parse String Direc
direc = (base >*> opes) `build` uncurry Direc


base :: Parse Token Base
base = num `alt` ultima `alt` corriente `alt` relativa

num :: Parse Token Base
num = numero `build` Abs

ultima :: Parse Token Base
ultima = token "$" `build` const Ultima

corriente :: Parse Token Base
corriente = token "." `build` const Corriente

relativa :: Parse Token Base
relativa = (signo >*> numero) `build` (Rel . convert)
			where 
				convert = uncurry (*)

opes = list ope

ope :: Parse Token Int
ope = (operador >*> numero) `build` uncurry (*)

operador :: Parse Token Int
operador = signo `alt` (succeed 1)

signo :: Parse Token Int
signo = (token "-" `build` const (-1) ) `alt` (token "+" `build` const 1)




