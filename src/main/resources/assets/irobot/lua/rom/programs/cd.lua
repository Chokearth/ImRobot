local params = {...}

if( #params < 1) then
    println("Usage : cd <path>")
else
    fs.cd(params[1])
end