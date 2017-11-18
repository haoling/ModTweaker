package modtweaker.mods.mekanism;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.mekanism.gas.GasBracketHandler;
import modtweaker.mods.mekanism.gas.IGasDefinition;
import modtweaker.mods.mekanism.gas.IGasStack;
import modtweaker.mods.mekanism.handlers.Crusher;
import modtweaker.mods.mekanism.handlers.EnergizedSmelter;
import modtweaker.mods.mekanism.handlers.Enrichment;
import modtweaker.mods.mekanism.handlers.Infuser;
import modtweaker.mods.mekanism.handlers.Sawmill;

public class Mekanism {
    public Mekanism() {
        MineTweakerAPI.registerBracketHandler(new GasBracketHandler());
        MineTweakerAPI.registerClass(IGasDefinition.class);
        MineTweakerAPI.registerClass(IGasStack.class);
        MineTweakerAPI.registerClass(Crusher.class);
        MineTweakerAPI.registerClass(EnergizedSmelter.class);
        MineTweakerAPI.registerClass(Enrichment.class);
        MineTweakerAPI.registerClass(Infuser.class);
        MineTweakerAPI.registerClass(Sawmill.class);
    }
}
