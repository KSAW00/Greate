package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ALLOY_SMELTER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.PLATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.ModItems.ALLOYS;

public class GreateAlloySmelterRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            ALLOY_SMELTER_RECIPES
                    .recipeBuilder(ALLOYS[tier].getId())
                    .inputItems((UnificationEntry) PLATE.getIngredient(tier))
                    .inputItems(Blocks.ANDESITE.asItem())
                    .outputItems(ALLOYS[tier])
                    .duration(100)
                    .EUt(VA[tier])
                    .save(provider);
        }
    }
}
