package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.WireProperties;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.utils.GTUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;

public class CableRecipeRemoval {

    public static void disableAssemblerRecipes(Consumer<ResourceLocation> recipe) {
        wireGtSingle.executeHandler(null, PropertyKey.WIRE, (tagPrefix, material, property, consumer) -> CableRecipeRemoval.removeRecipe(tagPrefix, material, property, recipe));
        wireGtDouble.executeHandler(null, PropertyKey.WIRE, (tagPrefix, material, property, consumer) -> CableRecipeRemoval.removeRecipe(tagPrefix, material, property, recipe));
        wireGtQuadruple.executeHandler(null, PropertyKey.WIRE, (tagPrefix, material, property, consumer) -> CableRecipeRemoval.removeRecipe(tagPrefix, material, property, recipe));
        wireGtOctal.executeHandler(null, PropertyKey.WIRE, (tagPrefix, material, property, consumer) -> CableRecipeRemoval.removeRecipe(tagPrefix, material, property, recipe));
        wireGtHex.executeHandler(null, PropertyKey.WIRE, (tagPrefix, material, property, consumer) -> CableRecipeRemoval.removeRecipe(tagPrefix, material, property, recipe));
    }

    public static void removeRecipe(TagPrefix wirePrefix, Material material, WireProperties property, Consumer<ResourceLocation> recipe) {
        if(property.isSuperconductor()) return;
        int voltageTier = GTUtil.getTierByVoltage(property.getVoltage());
        int factor = (int) (wirePrefix.getMaterialAmount(material) * 2 / M);
        if(voltageTier <= LV) {
            recipe.accept(new ResourceLocation("gtceu", "shapeless/" + material.getName() + "_cable_" + factor));
            recipe.accept(new ResourceLocation("gtceu", "packer/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name().substring(6).toLowerCase()));
        }
        if(voltageTier <= EV) {
            recipe.accept(new ResourceLocation("gtceu", "assembler/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name.substring(6).toLowerCase() + "_rubber"));
        }
        recipe.accept(new ResourceLocation("gtceu", "assembler/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name.substring(6).toLowerCase() + "_silicone"));
        recipe.accept(new ResourceLocation("gtceu", "assembler/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name.substring(6).toLowerCase() + "_styrene_butadiene"));
    }
}
