t = {} -- construct an empty table and assign it to variable "t" 
print("Tabla inicializada t= {} ---",t) 
--0035AE18

print("Agrego 2 elementos") 
t["foo"] = 123 -- assign the value 123 to the key "foo" in the table
t[3] = "bar" -- assign the value "bar" to the key 3 in the table
print("t['foo'] = ",t["foo"])
--123
print("t[3] = ",t[3])
--bar

print("Tabla inicializada t = {['foo'] = 'bar', [123] = 456}") 
t = {["foo"] = "bar", [123] = 456}
print("t.foo = ", t.foo)
--bar
print("t[123] = ", t[123])
--456

print("Hago t = {foo = 'bar'}") 
t = {foo = "bar"} -- same as ["foo"]="bar" (but not [foo]="bar" , that would use the variable foo)
print("t['foo'] = " , t["foo"])
--bar

print("Tabla inicializada t = {foo = 'bar', [123] = 456}") 
print("Imprimo la tabla t:") 
t = {foo = "bar", [123] = 456}
for key,value in pairs(t) do print(key,value) end
--foo bar
--123 456

print("Tabla inicializada  t = {'a', 'b', 'c'}") 
 t = {"a", "b", "c"}
print(t[1])
--a
print(t[3])
--c

print("Tabla inicializada  t = {[1]='a', [2]='b', [3]='c'}") 
t = {[1]="a", [2]="b", [3]="c"}
print(t[1])
--a
print(t[3])
--c

print("Tabla inicializada  t = {'a', 'c'}") 
 t = {"a", "c"}
print("Agrego b en la segunda posicion") 
table.insert(t, 2, "b")
print("Imprimo a , b , c") 
print(t[1], t[2], t[3])
--a b c


t = {"a", "b", "c"}
print("Remove de la segunda posicion (b)") 
table.remove(t, 2)
print("Tiene que imprimir a, c") 
print(t[1], t[2])
--a c

print("Tabla inicializada  t = {1,2}") 
t = {1,2}
--t["foo"] = 123 -- assign the value 123 to the key "foo" in the table

for key, value in pairs(t) do

        print(key,":",value)

end
