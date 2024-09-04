package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.nugget;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Gold;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Obsidian;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MACERATOR_RECIPES;

public class GreateMaceratorRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        MACERATOR_RECIPES
                .recipeBuilder(Greate.id("obsidian_dust"))
                .inputItems(Blocks.OBSIDIAN.asItem())
                .outputItems(dust, Obsidian)
                .duration(1800)
                .EUt(VA[LV])
                .save(provider);

        MACERATOR_RECIPES.recipeBuilder(Greate.id("netherrack"))
                .inputItems(Blocks.NETHERRACK.asItem())
                .outputItems(AllItems.CINDER_FLOUR)
                .chancedOutput(nugget, Gold, 500, 120)
                .duration(150).EUt(2)
                .save(provider);
    }
}
