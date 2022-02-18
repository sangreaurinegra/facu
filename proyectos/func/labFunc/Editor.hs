-- 2843023 2793486

module Editor where
import System.IO
import System.Environment
import Data.List
import Data.Char

-- Editor nombre archivo, lineas archivo, indice , portapapeles , modificado 
data Editor = Editor String [String] Int [String] Bool
		deriving (Show,Eq)
	
{-
main = do	let nomArch = "pg"
		contenido <- readFile (nomArch)
		let editor = Editor nomArch (lines contenido) (length (lines contenido)) [] False

		
		print editor
		print "------ APPEND"
		let editor1 = append editor ["nuevaLinea"]
		print editor1
		let editor2 = append (setPosicion editor1 2) ["nueva -- Linea  -- diome"]
		print editor2
		print "------ CHANGE"
		let editor3 = changes editor2 2 5 ["CHANGE"]
		print editor3
		print "------ DELETE"
		print editor
		let editor4 = deletes editor 1 3
		print "deletes editor 1 3"
		print editor4
		let editor5 = deletes editor 2 25
		print "deletes editor 2 25"
		print editor5
		let editor6 = deletes editor 2 3
		print "deletes editor 2 25"
		print editor6
		let editor7 = deletes editor 25 26
		print "deletes editor 25 26"
		print editor7
		print "------ INSERTS"
		print editor
		let editor8 = inserts (setPosicion editor 2) ["INSERT1","INSERT2","INSERT3"]
		print "inserts (setPosicion editor 2) [INSERT1,INSERT2,INSERT3]"
		print editor8
		let editor9 = inserts (setPosicion editor 0) ["INSERT1","INSERT2","INSERT3"]
		print "inserts (setPosicion editor 0) [INSERT1,INSERT2,INSERT3]"
		print editor9

		print "------ JOINS"
		print editor
		let editor10 = joins editor 2 4
		print "joins editor 2 4"
		print editor10
		let editor11 = joins editor 1 2
		print "joins editor 1 2"
		print editor11
		let editor12 = joins editor 0 2
		print "joins editor 0 2"
		print editor12
		let editor12 = joins editor 9 13
		print "joins editor 9 13"
		print editor12

		print "------ MOVES"
		print editor
		let editor13 = moves editor 4 6 1
		print "moves editor 4 6 1"
		print editor13
		let editor14 = moves editor 4 6 0
		print "moves editor 4 6 0"
		print editor14
		let editor15 = moves editor 4 6 10
		print "moves editor 4 6 10"
		print editor15
		let editor16 = moves editor 4 6 8
		print "moves editor 4 6 8"
		print editor16
		print "------ PrintP"
		print editor
		print "<- printp editor 4 6"
		editor17 <- printp editor 4 6
		print "print editor -- resultante de printp"
		print editor17
		
		print "------ PrintN"
		print editor
		print "<- printn editor 4 6"
		editor18 <- printn editor 4 6
		print "print editor -- resultante de printn"
		print editor18
		print "------ PrintIgual"
		print editor
		print "<- printigual editor 4"
		editor19 <- printigual editor 4 
		print "print editor -- resultante de printigual"
		print editor19
		
		-- copyy :: Editor -> Int -> Int ->  Editor
		print "------ CopyY"
		print editor
		print "<- copyy editor 2 4"
		let editor20 = copyy editor 2 4 
		print "print editor -- resultante de copyy"
		print editor20
		
		-- copyx :: Editor -> Int -> Editor
		print "------ CopyX"
		print editor20
		print "<- copyX editor20 5 (editor20 es el que tiene cargado el portapapeles)"
		let editor21 = copyx editor20 5 
		print "print editor -- resultante de copyx"
		print editor21
		-- print "putStr contenido"
		-- putStr contenido
		
		-- copyt :: Editor -> Int -> Int ->  Editor
		print "------ Copyt"
		print editor
		print "<- copyt editor 2 4 6"
		let editor22 = copyt editor 2 4 13 
		print "print editor -- resultante de copyt"
		print editor22
-}
--a, c, d, i, j, m, n, p, q, Q, t, x, y, =, w

-- `y', `s', `j', `d', or `c' cargan en portapapeles

append :: Editor -> [String] -> Editor
append (Editor a l i b _) s = Editor a (insertar s l i) (i+1) b True
		
getPosicion	::  Editor -> Int
getPosicion (Editor _ _ i _ _) = i

setPosicion	::  Editor -> Int -> Editor
setPosicion (Editor a l i b modif) j = Editor a l j b modif
	
-- inserta la primera lista en la segunda en despues el lugar int
insertar :: [String] -> [String] -> Int -> [String]
insertar s l i 
	| i > length l = l
	| i < 0 = l
	| i == 0 = s++l
	| otherwise = (take i l) ++ s ++ (drop i l)
	
-- 	
changes :: Editor -> Int -> Int -> [String] -> Editor
changes (Editor a l i b _) j k s = Editor a (insertar s (quitar l j k) (j-1)) (i+ length s)	(extraer l j k) True

-- quita los elementos entre los indices j k  
quitar :: [String] -> Int -> Int -> [String]
quitar l j k = (take (j-1) l) ++ (drop k l)


deletes :: Editor -> Int -> Int -> Editor
deletes (Editor a l i b modif) j k
	| (k >= length l)&& (j<= length l)= Editor a (quitar l j k) (j-1) b True -- caso no hay siguiente
	| ((k >= length l)&&(j>= length l))||(j>k)||(j<0)||(k<0) = Editor a l i b modif -- si estan los 2 fuera de rango retorno la entrada
	| otherwise = Editor a (quitar l j k) j (extraer l j k) True -- caso hay siguiente


inserts :: Editor -> [String] -> Editor
inserts (Editor a l i b modif) s 
	| i==0 = Editor a (insertar s l 0) (length s) b True-- caso 0 igual a caso 1
	| (i>length l)||(i<0) = Editor a l i b modif -- fuera de rango no hago nada
	| otherwise = Editor a (insertar s l (i-1)) ((i-1)+(length s)) b True

	
joins :: Editor -> Int -> Int -> Editor
joins (Editor a l i b modif) j k
	| ((k >= length l)&&(j>= length l))||(j>k)||(j<=0)||(k<=0) = Editor a l i b modif -- si estan los 2 fuera de rango retorno la entrada
	| otherwise = Editor a (juntar l j k) j (extraer l j k) True


juntar :: [String] -> Int -> Int -> [String]
juntar l j k = (take (j-1) l)  ++ [(juntartodo(take ((k-j)+1) $ drop (j-1) l))] ++ (drop k l)	
	
	
juntartodo []    = ""
juntartodo [x]   = x
juntartodo (x:xy) = x++juntartodo xy	
	
-- mueve el rango (j,k) despues de la linea m 	
moves :: Editor -> Int -> Int -> Int -> Editor
moves (Editor a l i b modif) j k m 
	| ((k >= length l)&&(j>= length l))||(j>k)||(j<0)||(k<0)||(m<0) = Editor a l i b modif -- si estan los 2 fuera de rango retorno la entrada
	|  m < j = Editor a (mover l j k m ) (m+(k-j)+1) b True
	|  m > k = Editor a (mover l j k m ) m b True
	
mover :: [String] -> Int -> Int -> Int -> [String]	
mover l j k m
	| (k<j)||(m<0)||(j<=0)||(k<=0)||(j>length l)||(k>length l)||(m>length l) = l
	| m < j = (take m l) ++ (extraer l j k) ++ (drop m $ quitar l j k) 
	| m > k = (quitar (take m l) j k ) ++ (extraer l j k) ++ (drop m l)
	| otherwise = l

-- retorna la lista indicada por el rango 
extraer :: [String] -> Int -> Int -> [String]
extraer l i j
	|(j<i)||(j<0)||(i<0)||(j>length l)||(i>length l) = l -- si esta fuera de rango retorno lista inicial
	| otherwise = take ((j-i)+1) $ drop (i-1) l


printn :: Editor -> Int -> Int -> IO(Editor)
printn (Editor a l i b modif) j k = do 
							putStr (unlines (agregarnum (extraer l j k) j ))
							return (Editor a l k b modif)	
							

agregarnum :: [String] -> Int ->[String]
agregarnum l i
	| l == [] = []
	| otherwise = ((show i)++"\t"++(head l)): agregarnum (tail l) (i+1)	
	
printp :: Editor -> Int -> Int -> IO(Editor)
printp (Editor a l i b modif) j k = do 
							putStr (unlines (extraer l j k))
							return (Editor a l k b modif)
	
printigual :: Editor -> Int -> IO(Editor)
printigual (Editor a l i b modif) j = do 
							putStr ((!! (j-1)) l ++"\n")
							return (Editor a l i b modif)	 -- por ahora retorno una copia del editor por si tengo que modificar el indice lo coan no tengo claro
							
							
copyt :: Editor -> Int -> Int -> Int -> Editor
copyt (Editor a l i b modif) j k m 
	| ((k >= length l)&&(j>= length l))||(j>k)||(j<0)||(k<0)||(m<0) = (Editor a l i b modif) -- si estan los 2 fuera de rango retorno la entrada
	| otherwise = (Editor a (copiar l j k m ) (m+(k-j)+1) b True)
							
copiar :: [String] -> Int -> Int -> Int -> [String]							
copiar l j k m
	| (k<j)||(m<0)||(j<=0)||(k<=0)||(j>length l)||(k>length l)||(m>length l) = l
	| otherwise = (take m l) ++ (extraer l j k) ++ (drop m l)

	
-- Aca siguen los comandos  x , y	
	
-- copia contenido indicado por el rango en el portapapeles (.,.)y
copyy :: Editor -> Int -> Int ->  Editor
copyy (Editor a l i b modif) j k  
	| ((k >= length l)&&(j>= length l))||(j>k)||(j<0)||(k<0) = (Editor a l i b modif) -- si estan los 2 fuera de rango retorno la entrada
	| otherwise = (Editor a l i (extraer l j k) modif)

-- agrega el contenido del portapapeles despues de la linea indicada (.)x
copyx :: Editor -> Int -> Editor
copyx (Editor a l i b modif) j  
	| (j>= length l)||(j<0) = (Editor a l i b modif) -- si estan los 2 fuera de rango retorno la entrada
	| otherwise = (Editor a (insertar b l j) (j+length b) b True)

guardarArchivo :: Editor -> Int -> Int -> IO(Editor)
guardarArchivo (Editor a l i b modif) j k = do	let aGuardar = extraer l j k
						let bufferArchivo = unlines (aGuardar)
						print (length bufferArchivo)
						writeFile a bufferArchivo
						if ((length aGuardar)==(length l)) 
						then return (Editor a l i b False) 
						else return (Editor a l i b True)

						
						





	
