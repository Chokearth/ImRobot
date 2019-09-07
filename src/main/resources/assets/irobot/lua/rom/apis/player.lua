player = {}

function player.moveToFlatA(x, z)
    basicPlayer.initMoveSystem()
    local prec = 0.2

    repeat
        local px, py, pz = basicPlayer.getCoord()
        local lx, ly, lz = basicPlayer.getLookVec()

        local angle = math.atan2(lx, lz)

        basicPlayer.moveDir((x-px)*math.cos(angle)-(z-pz)*math.sin(angle), 0, (x-px)*math.sin(angle)+(z-pz)*math.cos(angle))
    until (px-x)*(px-x)+(pz-z)*(pz-z) < prec*prec
    basicPlayer.moveDir(0, 0, 0)
    basicPlayer.stopMoveSystem()
end
function player.moveToFlat(x, z)
    local px, py, pz = basicPlayer.getCoord()
    player.moveToFlatA(x+px, z+pz)
end

function player.center()
    local x, y, z = basicPlayer.getCoord()
    player.moveToFlatA(math.floor(x)+0.5, math.floor(z)+0.5)
end

function player.breakBlock()
    sleep(50)
    local x, y, z = basicPlayer.getLookAtCoord()
    basicPlayer.holdLeftClick()
    repeat
        local xt, yt, zt = basicPlayer.getLookAtCoord()
    until not(xt == x and yt == y and zt == z)
    basicPlayer.releasedLeftClick()
end

return player