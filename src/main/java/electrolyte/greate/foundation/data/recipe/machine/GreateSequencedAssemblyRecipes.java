package electrolyte.greate.foundation.data.recipe.machine;

import com.google.common.collect.ImmutableMap;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.WireProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.PLATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromUnificationEntry;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateSequencedAssemblyRecipes {

    private static final Map<TagPrefix, Integer> INSULATION_AMOUNT = ImmutableMap.of(
            cableGtSingle, 1,
            cableGtDouble, 1,
            cableGtQuadruple, 2,
            cableGtOctal, 3,
            cableGtHex, 5);

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

        wireGtSingle.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtDouble.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtQuadruple.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtOctal.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtHex.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);

    }

    public static void addRecipe(TagPrefix wirePrefix, Material material, WireProperties property, Consumer<FinishedRecipe> provider) {
        if(property.isSuperconductor()) return;
        int cableAmount = (int) (wirePrefix.getMaterialAmount(material) * 2 / M);
        TagPrefix cablePrefix = TagPrefix.get("cable" + wirePrefix.name().substring(4));
        int voltageTier = GTUtil.getTierByVoltage(property.getVoltage());
        int insulationAmount = INSULATION_AMOUNT.get(cablePrefix);

        if(voltageTier >= EV) {
            SequencedAssemblyRecipeBuilder siliconeBuilder = new SequencedAssemblyRecipeBuilder(Greate.id(String.format("%s_cable_%d_silicone", material.getName(), cableAmount)))
                .require(ChemicalHelper.get(wirePrefix, material).getItem())
                .transitionTo(ChemicalHelper.get(wirePrefix, material).getItem())
                .addOutput(ChemicalHelper.get(cablePrefix, material), 1)
                .loops(1);
            if(voltageTier >= LuV) {
                siliconeBuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyphenyleneSulfide, insulationAmount).getItem()));
            }
            siliconeBuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyvinylChloride, insulationAmount).getItem()));
            siliconeBuilder.addStep(FillingRecipe::new, r -> r.require(FluidIngredient.fromFluid(SiliconeRubber.getFluid(), L * insulationAmount / 2)));
            siliconeBuilder.build(provider);

            SequencedAssemblyRecipeBuilder styrenebuilder = new SequencedAssemblyRecipeBuilder(Greate.id(String.format("%s_cable_%d_styrene", material.getName(), cableAmount)))
                    .require(ChemicalHelper.get(wirePrefix, material).getItem())
                    .transitionTo(ChemicalHelper.get(wirePrefix, material).getItem())
                    .addOutput(ChemicalHelper.get(cablePrefix, material), 1)
                    .loops(1);
            if(voltageTier >= LuV) {
                styrenebuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyphenyleneSulfide, insulationAmount).getItem()));
            }
            styrenebuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyvinylChloride, insulationAmount).getItem()));
            styrenebuilder.addStep(FillingRecipe::new, r -> r.require(FluidIngredient.fromFluid(StyreneButadieneRubber.getFluid(), L * insulationAmount / 4)));
            styrenebuilder.build(provider);
        }
    }
}
