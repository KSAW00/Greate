package electrolyte.greate.foundation.data.recipe.removal;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.CableRecipeRemoval.disableAssemblerRecipes;
import static electrolyte.greate.foundation.data.recipe.removal.CreateRecipeRemoval.disableCreateRecipes;

public class GreateRecipeRemoval {

    public static void init(Consumer<ResourceLocation> recipe) {
        disableCreateRecipes(recipe);
        disableAssemblerRecipes(recipe);
    }
}
