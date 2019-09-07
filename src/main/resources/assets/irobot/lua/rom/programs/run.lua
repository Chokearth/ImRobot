local params = {...}

if( #params < 1) then
    println("Usage : run <path>")
else

    p = {}
    for i = 2, #params do
        p[i-1] = params[i]
    end

    os.run(params[1], p)
end