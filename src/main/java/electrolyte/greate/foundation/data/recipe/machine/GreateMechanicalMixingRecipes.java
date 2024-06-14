package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.PLATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromUnificationEntry;
import static electrolyte.greate.registry.ModItems.ALLOYS;

public class GreateMechanicalMixingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new,
                    ALLOYS[tier].getId())
                    .withItemIngredients(createIngFromUnificationEntry(PLATE.getIngredient(tier)), Ingredient.of(Blocks.ANDESITE))
                    .withItemOutputs(new ProcessingOutput(ALLOYS[tier].asStack(), 1))
                    .recipeCircuit(4)
                    .recipeTier(tier)
                    .build(provider);
        }
    }
}
