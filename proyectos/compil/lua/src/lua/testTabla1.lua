t = {}
MAXIMUM = 5
STEP = 1
for i = 1, MAXIMUM, STEP do
	t[i] = math.sqrt(MAXIMUM - i)
end
print("La tabla se inicializa de la siguiente manera: ")
print("t = {}")
print("MAXIMUM = 5")
print("STEP = 1")
print("for i = 1, MAXIMUM, STEP do")
print("     t[i] = math.sqrt(MAXIMUM - i)")
print("end")
 
print("tabla t:",t)
print("key","value")
for key,value in pairs(t) do print(key,value) end
table.sort(t)
print("tabla t despues del sort:")
print("key","value")
for key,value in pairs(t) do print(key,value) end
table.insert(t, 5, 1.8)
table.insert(t, 3)
print("tabla t despues de los insert (t, 5, 1.8) y (t, 3):")
print("key","value")
for key,value in pairs(t) do print(key,value) end
table.remove(t, 6)
table.remove(t, 3)
print("tabla t despues de los remove (t, 6) y (t, 3):")
print("key","value")
for key,value in pairs(t) do print(key,value) end
