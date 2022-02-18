print("Diversas asignaciones numericas, texto, tablas de la letra y otras agregadas")
--Una variable numérica:
CanIHazANomNom = 1000

--Una variable de texto:
GIMMEH = "HAI WORLD"

print(CanIHazANomNom,GIMMEH)

--1000    HAI WORLD


--Una variable con una tabla. 

BunchOfText = {"I Do Not Like Them Sam I Am", "I Do Not Like Green Eggs and Ham", "I Would Not Like Them Here or There", "I Would Not Like Them Anywhere"}
print(BunchOfText)


--Una variable booleana
NoYouMoron = false

--Copiando una variable
AreWeGonnaDieIn2012 = NoYouMoron

print(NoYouMoron ,AreWeGonnaDieIn2012 )
--false    false
--mal imprime 0 0

esnil = nil
--Switching de valores. Si el primer valor es nulo, se asigna el Segundo
Apocalypse = esnil  or "2012's a joke."
print(Apocalypse)
--2012's a joke

--Asignando nulo
Nothing = nil
print(Nothing )
--nil

--Asignando una function (las funciones son asignables)
someFunction = function(parameter, anotherParameter) end

--Podemos asignar variables de diferente tipo
Nothing = GIMMEH
print(Nothing)
--HAI WORLD

--Asignación de múltiples variables
A, B, C, D = "a", "b", "oops I took both c and d"

print(A,B,C,D)
--a	b	oops I took both c and d	nil

--asignacion local
local soyLocal = "hola soy local"
print(soyLocal)
--hola soy local
