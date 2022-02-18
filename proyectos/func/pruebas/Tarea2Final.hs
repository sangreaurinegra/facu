module Main  where
import System.IO
import  System.Environment
import Data.Char
--import List
import Direc
import Parsing

oper = ["a" ,"c","d","i","j","k","l","m","n","p","q","Q"]

data Editor = Editor [(String,[Char])] Int Char
		deriving Eq

data Operando = 	Append
		|	Change
		|	Delete
		|	Insert
		|	Join
		| 	Mark
		|	Print
		|	Move
		|	Printn
		|	Printp
		|	Quit
		|	Quit2
	deriving (Show,Eq)

data DirecMio = DirecMio Direc
		| ComienzoFinal
		| ActualFinal
		deriving Eq

data Argumento =	ArgDir [DirecMio]
		|	ArgChar [Char]
		deriving Eq

data Comando = Comando [DirecMio] Operando Argumento
		deriving Eq

main :: IO()
main = 	
	do 	editor  <-  iniciarEdi
		editor' <- procesarComandos editor
		print editor'
		--escribirEditor editor
		--return "hola"

iniciarEdi ::IO(Editor)
iniciarEdi
	= do 	path <- getArgs
		print path
		archivo <- readFile (head path)
		let ed = Editor (zip (lines archivo) []) (length (lines archivo)) 'n'
                return ed

procesarComandos :: Editor -> IO(String)
procesarComandos editor =
	do 
	comando <- leerComando
	if esValidoComando comando
	then do editor' <- ejecutarComando comando editor
		procesarComandos editor'
	else do procesarError
		procesarComandos editor
	
leerComando :: IO(Comando)
leerComando =
	do	linea <- getLine
		return(fst (last(direcTotal(lexer linea))))


escribirBuffer :: [String] ->IO()
escribirBuffer (x:xs)
	|(x:xs) == [] = putStr " " 
	|otherwise = do	print x
			escribirBuffer xs
			

direc2 = list direc3
direc3 = (direc >*> coma) `build` dameDirecMio `alt` (token "%" `build`  const ComienzoFinal) `alt` (token "," `build` const ComienzoFinal) `alt` (token ";" `build` const ActualFinal) --`alt` succeed []

dameDirecMio :: (Direc,Token)->DirecMio
dameDirecMio (a,b) = DirecMio a

coma = token "," `alt` (succeed [])


direcSimb = direc2 >*> operando
operando = spot esOperando `build` hacerOperando
		where hacerOperando x
				| x=="a" = Append
				| x=="c" = Change
				| x=="i" = Insert
				| x=="j" = Join
				| x=="k" = Mark
				| x=="l" = Print
				| x=="m" = Move
				| x=="n" = Printn
				| x=="p" = Printp
				| x=="q" = Quit
				| x=="Q" = Quit2
						

argumento =  (direc2 `build` ArgDir) `alt` (spot (esChar.head) `build` ArgChar)
direcTotal = (direcSimb >*> argumento) `build` hacerComando
		where hacerComando ((a,b),c) = Comando a b c

esChar:: Char->Bool
esChar x = elem x ['a'..'z']
	
esOperando:: Token ->Bool
esOperando x= elem x oper

corrienteNum ::Editor -> Int
corrienteNum (Editor _ i _) = i

ultimaNum :: Editor -> Int
ultimaNum (Editor xs _ _) = length xs

marcaNum :: Char -> Editor -> Int
marcaNum c (Editor ((a,b):xs) j p) 
	|elem c b = 0
	|otherwise = 1 + marcaNum c (Editor xs j p) 	
	 

darDirecNumero :: Direc->Editor->Int
darDirecNumero (Direc (Abs x) (y:ys)) h =y + darDirecNumero (Direc (Abs x) (ys)) h
darDirecNumero (Direc (Abs x) []) h = x
darDirecNumero (Direc Ultima (y:ys)) h =y + darDirecNumero (Direc Ultima (ys)) h 
darDirecNumero (Direc Ultima []) h = ultimaNum h
darDirecNumero (Direc Corriente (y:ys)) h =y + darDirecNumero (Direc Corriente (ys)) h
darDirecNumero (Direc Corriente []) h = corrienteNum h
darDirecNumero (Direc (Marca c) (y:ys)) h =y + darDirecNumero (Direc (Marca c) (ys)) h
darDirecNumero (Direc (Marca c) []) h = marcaNum c h
darDirecNumero (Direc (Rel i) (y:ys)) h =y + darDirecNumero (Direc (Rel i) (ys)) h
darDirecNumero (Direc (Rel i) []) h = i


esValidoComando(Comando _ c (ArgDir (x:xs))) 
	|((c==Append) || (c==Change))  = False
	| otherwise = True

procesarError = print "Error en la entrada"
	
	
	