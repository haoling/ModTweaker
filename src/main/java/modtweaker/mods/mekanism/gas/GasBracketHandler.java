package modtweaker.mods.mekanism.gas;

import java.util.List;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import minetweaker.api.item.IngredientAny;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

@BracketHandler(priority = 100)
public class GasBracketHandler implements IBracketHandler {
    public static IGasStack getGas(String name) {
        Gas gas = GasRegistry.getGas(name);
        if (gas != null) {
            return new MCGasStack(new GasStack(gas, 1));
        } else {
            return null;
        }
    }

    private final IZenSymbol symbolAny;
    private final IJavaMethod method;

    public GasBracketHandler() {
        symbolAny = MineTweakerAPI.getJavaStaticFieldSymbol(IngredientAny.class, "INSTANCE");
        method = MineTweakerAPI.getJavaMethod(GasBracketHandler.class, "getGas", String.class);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        // any symbol
        if (tokens.size() == 1 && tokens.get(0).getValue().equals("*")) {
            return symbolAny;
        }

        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("gas") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, 2, tokens.size());
            }
        }

        return null;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }

        Gas gas = GasRegistry.getGas(valueBuilder.toString());
        if (gas != null) {
            return new GasReferenceSymbol(environment, valueBuilder.toString());
        }

        return null;
    }

    private class GasReferenceSymbol implements IZenSymbol {
        private final IEnvironmentGlobal environment;
        private final String name;

        public GasReferenceSymbol(IEnvironmentGlobal environment, String name) {
            this.environment = environment;
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
        }
    }
}