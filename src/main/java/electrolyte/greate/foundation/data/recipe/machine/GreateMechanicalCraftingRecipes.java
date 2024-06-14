package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.CIRCUIT;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.CrushingWheels.CRUSHING_WHEELS;
import static electrolyte.greate.registry.ModItems.ALLOYS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateMechanicalCraftingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            MechanicalCraftingRecipeBuilder.shapedRecipe(CRUSHING_WHEELS[tier], 2)
                    .key('A', ALLOYS[tier])
                    .key('C', (TagKey<Item>) CIRCUIT.getIngredient(tier))
                    .key('S', SHAFTS[tier])
                    .patternLine(" AAA ")
                    .patternLine("AACAA")
                    .patternLine("ACSCA")
                    .patternLine("AACAA")
                    .patternLine(" AAA ")
                    .build(provider);
        }
    }
}
