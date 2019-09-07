function os.version()
    return "BrainOs 0.1"
end

function print(str)
    term.print(str)
end
function println(str)
    term.println(str)
end

function sleep(time)
    EId = os.startTimer(time)
    repeat
        local answer = os.pullEvent("timer"..EId)
    until answer == true
end

co = null
function addCo(fn, args)
    local f = loadfile(fn, "t")
    if(f == nil) then return end
    debug.setupvalue(f, 1, {print = print})

    local argsS = ""
    if args ~= nil then
        for i = 1, #args do
            argsS = argsS.."\""..args[i].."\""
            if i ~= #args then argsS = argsS.."," end
        end
    end

    co = coroutine.create(load("assert(loadfile(\""..fn.."\"))("..argsS..")"))
    debug.sethook(co, coroutine.yield, "", 3)

end
function runLuaCommand(c)
    f = load(c)
    if(f == nil) then return end
    debug.setupvalue(f, 1, {print = print})

    co = coroutine.create(load(c))
    debug.sethook(co, coroutine.yield, "", 1)

end

function tick()
    if(co ~= null) then
        if (coroutine.resume(co) == false) then
            co = null
        end
    end
end

os.loadApi("rom/apis/objects.lua")

basicPlayer.releasedLeftClick()
basicPlayer.releasedRightClick()
basicPlayer.stopMoveSystem()