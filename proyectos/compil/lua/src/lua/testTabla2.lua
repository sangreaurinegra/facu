print("La tabla t se inicializa de la siguiente manera:")
print("t = {do, re, me, [solfa]=70, [dore] = mefa}")
t = {"do", "re", "me", ["solfa"]=70, ["dore"] = "mefa"}
print("tabla t:",t)
print("key","value")
for key,value in pairs(t) do print(key,value) end
t[4]="la"
t[5]="si"
print("tabla t despues de las asignaciones t[4]=la y t[5]=si")
print("key","value")
for key,value in pairs(t) do print(key,value) end
t.dore = "laso"
t["solfa"] = "redo"
t[2]="sol"
print("tabla t despues de la asignacion t.dore = laso, t[2]=sol y t[solfa] = redo")
print("key","value")
for key,value in pairs(t) do print(key,value) end


