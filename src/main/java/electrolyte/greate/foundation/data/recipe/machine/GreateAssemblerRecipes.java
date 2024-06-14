package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static electrolyte.greate.registry.ModItems.ULV_CONVEYOR_MODULE;
import static electrolyte.greate.registry.ModItems.ULV_ELECTRIC_MOTOR;

public class GreateAssemblerRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        ASSEMBLER_RECIPES
                .recipeBuilder("electric_motor_ulv")
                .inputItems(cableGtSingle, RedAlloy, 2)
                .inputItems(rod, Copper, 2)
                .inputItems(rod, IronMagnetic)
                .inputItems(wireGtSingle, Tin, 4)
                .outputItems(ULV_ELECTRIC_MOTOR)
                .duration(100)
                .EUt(VA[ULV])
                .save(provider);

        final Map<String, Material> rubberMaterials = new Object2ObjectOpenHashMap<>();
        rubberMaterials.put("rubber", Rubber);
        rubberMaterials.put("silicone_rubber", SiliconeRubber);
        rubberMaterials.put("styrene_butadiene_rubber", StyreneButadieneRubber);

        for(Map.Entry<String, Material> materialEntry : rubberMaterials.entrySet()) {
            String name = materialEntry.getKey();

            ASSEMBLER_RECIPES
                    .recipeBuilder("conveyor_module_ulv_" + name)
                    .inputItems(cableGtSingle, RedAlloy)
                    .inputItems(ULV_ELECTRIC_MOTOR, 2)
                    .inputFluids(materialEntry.getValue().getFluid(L * 6))
                    .circuitMeta(1)
                    .outputItems(ULV_CONVEYOR_MODULE)
                    .duration(100)
                    .EUt(VA[ULV])
                    .save(provider);
        }
    }
}
