objects = {}

function objects.blockPos(ix, iy, iz)
    local self = {x = ix, y = iy, z = iz }

    local x = function(v)
        self.x = v or self.x
        return self.x
    end
    local y = function(v)
        self.y = v or self.y
        return self.y
    end
    local z = function(v)
        self.z = v or self.z
        return self.z
    end

    return {
        x, y, z
    }
end

function objects.block(iname, ipos)
    local self = {name = iname, pos = ipos }

    local name = function(v)
        self.name = v or self.name
        return name
    end
    local pos = function(v)
        self.pos = v or self.pos
        return pos
    end

    return{
        name, pos
    }
end