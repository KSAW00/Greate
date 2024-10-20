package electrolyte.greate.foundation.data.recipe.removal;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.CableRecipeRemoval.disableAssemblerRecipes;

public class GTRecipeRemoval {

    public static void disableGTRecipes(Consumer<ResourceLocation> recipe) {
        disableAssemblerRecipes(recipe);
    }
}
