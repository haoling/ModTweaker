package modtweaker.mods.mekanism.util;

import java.util.Map;
import java.util.Map.Entry;

import mekanism.common.recipe.inputs.MachineInput;
import mekanism.common.recipe.machines.MachineRecipe;
import mekanism.common.recipe.outputs.ChanceOutput;
import mekanism.common.recipe.outputs.ChemicalPairOutput;
import mekanism.common.recipe.outputs.FluidOutput;
import mekanism.common.recipe.outputs.GasOutput;
import mekanism.common.recipe.outputs.ItemStackOutput;
import mekanism.common.recipe.outputs.MachineOutput;
import mekanism.common.recipe.outputs.PressurizedOutput;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseMapRemoval;
import minetweaker.mc1102.item.MCItemStack;

@SuppressWarnings("rawtypes")
public class RemoveMekanismRecipe extends BaseMapRemoval<MachineInput, MachineRecipe> {
    public RemoveMekanismRecipe(String name, Map<MachineInput, MachineRecipe> map, Map<MachineInput, MachineRecipe> recipes) {
		super(name, map, recipes);
	}
    
    @Override
    protected String getRecipeInfo(Entry<MachineInput, MachineRecipe> recipe) {
        MachineOutput output = recipe.getValue().recipeOutput;
        
        if(output instanceof ItemStackOutput) {
            return LogHelper.getStackDescription(new MCItemStack(((ItemStackOutput)output).output));
        } else if (output instanceof GasOutput) {
            return LogHelper.getStackDescription(((GasOutput)output).output);
        } else if (output instanceof FluidOutput) {
            return LogHelper.getStackDescription(((FluidOutput)output).output);
        } else if (output instanceof ChemicalPairOutput) {
            return "[" + LogHelper.getStackDescription(((ChemicalPairOutput)output).leftGas) + ", " +
                    LogHelper.getStackDescription(((ChemicalPairOutput)output).rightGas) + "]";
        } else if (output instanceof ChanceOutput) {
            return LogHelper.getStackDescription(new MCItemStack(((ChanceOutput)output).primaryOutput));
        } else if (output instanceof PressurizedOutput) {
            return "[" + LogHelper.getStackDescription(new MCItemStack(((PressurizedOutput)output).getItemOutput())) + ", " +
                    LogHelper.getStackDescription(((PressurizedOutput)output).getGasOutput()) + "]";
        }
        
        return null;
    }
}
