package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.AllItems;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MACERATOR_RECIPES;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;

public class GreateMaceratorRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        MACERATOR_RECIPES
                .recipeBuilder("andesite_alloy")
                .inputItems(AllItems.ANDESITE_ALLOY)
                .outputItems(dust, AndesiteAlloy)
                .duration(20)
                .EUt(2)
                .save(provider);
    }
}
