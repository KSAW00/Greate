package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.CableRecipeRemoval.disableAssemblerRecipes;

public class GTRecipeRemoval {

    public static void disableGTRecipes(Consumer<ResourceLocation> recipe) {
        disableAssemblerRecipes(recipe);

        recipe.accept(GTCEu.id("macerator/macerate_netherrack"));
    }
}
