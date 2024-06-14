package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Wood;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.PLATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromUnificationEntry;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateSequencedAssemblyRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            int finalTier = tier;
            new SequencedAssemblyRecipeBuilder(LARGE_COGWHEELS[tier].getId())
                    .require(SHAFTS[tier])
                    .transitionTo(COGWHEELS[tier])
                    .addStep(DeployerApplicationRecipe::new, r -> r.require(createIngFromUnificationEntry(finalTier != 0 ? PLATE.getIngredient(finalTier - 1) : new UnificationEntry(plate, Wood))))
                    .addStep(DeployerApplicationRecipe::new, r -> r.require(createIngFromUnificationEntry(finalTier != 0 ? PLATE.getIngredient(finalTier - 1) : new UnificationEntry(plate, Wood))))
                    .addOutput(LARGE_COGWHEELS[tier], 1)
                    .loops(1)
                    .build(provider);
        }
    }
}
