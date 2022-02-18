

a="a level0"

function func(a)
        a="a level1"

        if true then
                 local a="a level2"
                 if true then 
	                local a = "a level3"
	                print("scope3 a=" , a)
                 end	
                 print("scope2 a=" , a)
        else
                a = "No entro"
        end
        print("scope1 a=",a)

end


print("scope0 a=", a)
func(a)
print("scope0 a=",a)


