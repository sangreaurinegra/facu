function get_all_factors(number)
        --[[--
        Gets all of the factors of a given number
        @Parameter: number
        The number to find the factors of
        @Returns: A table of factors of the number
        --]]--
        local factors = {}
        for possible_factor=1, math.sqrt(number), 1 do
                local remainder = number%possible_factor
                if remainder == 0 then
                        local factor, factor_pair =    possible_factor, number/possible_factor
                        table.insert(factors, factor)
                        if factor ~= factor_pair then
                                table.insert(factors, factor_pair)
                        end
                end
        end
        table.sort(factors)
        return factors
end
-- The Meaning of the Universe is 42.
the_universe = 42
factors_of_the_universe = get_all_factors(the_universe)

print("Count",  "The Factors of Life, the Universe, and Everything")
for key, value in pairs(factors_of_the_universe ) do

        print(key,value)

end
--[[--
Count	The Factors of Life, the Universe, and Everything	
1	1	
2	2	
3	3	
4	6	
5	7	
6	14	
7	21	
8	42	
--]]--
