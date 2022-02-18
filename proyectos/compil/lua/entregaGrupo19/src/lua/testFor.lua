print("for de la tabla t = {['a']=10,['b']=20,30}")
t = {["a"]=10,["b"]=20,30}

for key, value in pairs(t) do

        print(key,value)


end

print("for the_number = 1, MAXIMUM, STEP do")
MAXIMUM = 10
STEP = 1
for the_number = 1, MAXIMUM, STEP do
        print("the number is", the_number)
end

print("for i=1 ,5,2 do -- realiza STEP de a 2")
for i=1 ,5,2 do
        print(i)
end

print("for i=2 ,5 do  -- Es el for sin STEP toma 1")
for i=2 ,5 do
        print(i)
end


