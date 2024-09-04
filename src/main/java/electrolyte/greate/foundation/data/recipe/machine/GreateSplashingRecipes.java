package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.HV;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Wheat;

public class GreateSplashingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        new TieredProcessingRecipeBuilder<>(TieredSplashingRecipe::new, Greate.id("obsidian"))
                .withItemIngredients(Ingredient.of(Blocks.MAGMA_BLOCK))
                .withSingleItemOutput(new ItemStack(Blocks.OBSIDIAN))
                .recipeTier(HV)
                .build(provider);

        new TieredProcessingRecipeBuilder<>(TieredSplashingRecipe::new, Greate.id("dough"))
                .withItemIngredients(Ingredient.of(ChemicalHelper.get(new UnificationEntry(dust, Wheat), 1)))
                .withSingleItemOutput(AllItems.DOUGH.asStack())
                .build(provider);
    }
}
