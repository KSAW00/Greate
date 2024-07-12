package electrolyte.greate.foundation.data.recipe.machine;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import electrolyte.greate.foundation.data.recipe.GreateRecipes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.UHV;
import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static electrolyte.greate.GreateValues.BM;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.conversionCycle;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromTag;
import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Gearboxes.VERTICAL_GEARBOXES;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.MechanicalMixers.MECHANICAL_MIXERS;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;
import static electrolyte.greate.registry.Millstones.MILLSTONES;
import static electrolyte.greate.registry.ModItems.*;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateCraftingTableRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for (int tier = 0; tier < TM.length; tier++) {
            Material tierMaterial = TM[tier];
            VanillaRecipeHelper.addShapedRecipe(provider, ALLOYS[tier].getId(), ALLOYS[tier].asStack(), "NA", "AN", "fh", 'N', new UnificationEntry(plate, tierMaterial), 'A', new ItemStack(Blocks.ANDESITE));
            VanillaRecipeHelper.addShapedRecipe(provider, SHAFTS[tier].getId(), SHAFTS[tier].asStack(4), "s ", " A", 'A', new UnificationEntry(plate, tierMaterial));

            Material previousTierMaterial = tier - 1 == -1 ? Wood : TM[tier - 1];
            VanillaRecipeHelper.addShapelessRecipe(provider, COGWHEELS[tier].getId(), COGWHEELS[tier].asStack(), SHAFTS[tier].asStack(), new UnificationEntry(plate, previousTierMaterial), GreateRecipes.createIngFromTag("forge", "tools/files"));
            VanillaRecipeHelper.addShapelessRecipe(provider, LARGE_COGWHEELS[tier].getId(), LARGE_COGWHEELS[tier].asStack(), SHAFTS[tier].asStack(), new UnificationEntry(plate, previousTierMaterial), new UnificationEntry(plate, previousTierMaterial), GreateRecipes.createIngFromTag("forge", "tools/files"));
            VanillaRecipeHelper.addShapelessRecipe(provider, LARGE_COGWHEELS[tier].getId().withSuffix("_from_little"), LARGE_COGWHEELS[tier].asStack(), COGWHEELS[tier].asStack(), new UnificationEntry(plate, previousTierMaterial), GreateRecipes.createIngFromTag("forge", "tools/files"));
            VanillaRecipeHelper.addShapedRecipe(provider, GEARBOXES[tier].getId(), GEARBOXES[tier].asStack(), " S ", "SCS", "wSh", 'S', SHAFTS[tier].asStack(), 'C', AllBlocks.ANDESITE_CASING);
            VanillaRecipeHelper.addShapedRecipe(provider, WHISKS[tier].getId(), WHISKS[tier].asStack(), "fId", "PIP", "PPP", 'I', new UnificationEntry(ingot, tierMaterial), 'P', new UnificationEntry(plate, tierMaterial));
            VanillaRecipeHelper.addShapedRecipe(provider, PROPELLERS[tier].getId(), PROPELLERS[tier].asStack(), "SAS", "ARA", "SAS", 'S', new UnificationEntry(screw, tierMaterial), 'A', ALLOYS[tier], 'R', new UnificationEntry(rod, tierMaterial));
            conversionCycle(provider, ImmutableList.of(GEARBOXES[tier], VERTICAL_GEARBOXES[tier]));

            // Machines
            VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_PUMPS[tier].getId(), MECHANICAL_PUMPS[tier].asStack(), " RS", "wPC", " RS", 'S', new UnificationEntry(screw, tierMaterial), 'R', new UnificationEntry(ring, Rubber), 'P', AllBlocks.FLUID_PIPE, 'C', COGWHEELS[tier].asStack());
            if(tier != 0) {
                VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_PRESSES[tier].getId(), MECHANICAL_PRESSES[tier].asStack(), " S ", "CMC", "wBh", 'S', SHAFTS[tier].asStack(), 'C', CIRCUIT.getIngredient(tier), 'M', CASING.getIngredient(tier), 'B', new UnificationEntry(block, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_MIXERS[tier].getId(), MECHANICAL_MIXERS[tier].asStack(), " S ", "CMC", "wWh", 'S', SHAFTS[tier].asStack(), 'C', CIRCUIT.getIngredient(tier), 'M', CASING.getIngredient(tier), 'W', WHISKS[tier].asStack());
                VanillaRecipeHelper.addShapedRecipe(provider, MILLSTONES[tier].getId(), MILLSTONES[tier].asStack(), "CAC", "WHW", "wSh", 'A', COGWHEELS[tier].asStack(), 'W', Ingredient.of(ItemTags.WOODEN_SLABS), 'H', CASING.getIngredient(tier), 'C', CIRCUIT.getIngredient(tier), 'S', SHAFTS[tier].asStack());
                VanillaRecipeHelper.addShapedRecipe(provider, WHISKS[tier].getId(), WHISKS[tier].asStack(), "fId", "PIP", "PPP", 'I', new UnificationEntry(ingot, tierMaterial), 'P', new UnificationEntry(plate, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, PROPELLERS[tier].getId(), PROPELLERS[tier].asStack(), "SAS", "ARA", "SAS", 'S', new UnificationEntry(screw, tierMaterial), 'A', ALLOYS[tier], 'R', new UnificationEntry(rod, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, FANS[tier].getId(), FANS[tier].asStack(), " S ", "CMC", "wPh", 'S', SHAFTS[tier], 'C', CIRCUIT.getIngredient(tier), 'M', CASING.getIngredient(tier), 'P', PROPELLERS[tier]);

                if(tier != 9) {
                    VanillaRecipeHelper.addShapedRecipe(provider, SAWS[tier].getId(), SAWS[tier].asStack(), "GSG", "MCM", "OHO", 'G', CIRCUIT.getIngredient(tier), 'S', new UnificationEntry(toolHeadBuzzSaw, tierMaterial), 'M', MOTOR.getIngredient(tier), 'C', CASING.getIngredient(tier), 'H', SHAFTS[tier].asStack(), 'O', CONVEYOR.getIngredient(tier));
                }
            }
        }

        // ULS machines
        VanillaRecipeHelper.addShapedRecipe(provider, SAWS[0].getId(), SAWS[0].asStack(), "GSG", "OCO", "MHM", 'G', new UnificationEntry(TagPrefix.pipeSmallFluid, GTMaterials.TinAlloy), 'S', new UnificationEntry(toolHeadBuzzSaw, AndesiteAlloy), 'M', new UnificationEntry(plate, WroughtIron), 'C', CASING.getIngredient(ULV), 'H', SHAFTS[ULV].asStack(), 'O', new UnificationEntry(plate, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_PRESSES[0].getId(), MECHANICAL_PRESSES[0].asStack(), "PSP", "CMC", "wBh", 'P', new UnificationEntry(plate, AndesiteAlloy), 'S', SHAFTS[0].asStack(), 'C', new UnificationEntry(plate, WroughtIron), 'M', CASING.getIngredient(0), 'B', new UnificationEntry(block, TM[0]));
        VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_MIXERS[0].getId(), MECHANICAL_MIXERS[0].asStack(), "PSP", "CMC", "wWh", 'P', new UnificationEntry(plate, AndesiteAlloy), 'S', SHAFTS[0].asStack(), 'C', new UnificationEntry(plate, WroughtIron), 'M', CASING.getIngredient(0), 'W', WHISKS[0].asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, MILLSTONES[0].getId(), MILLSTONES[0].asStack(), "CAC", "WHW", "wSh", 'A', COGWHEELS[0].asStack(), 'C', new UnificationEntry(plate, AndesiteAlloy), 'H', CASING.getIngredient(0), 'W', new UnificationEntry(plate, WroughtIron), 'S', SHAFTS[0].asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, FANS[0].getId(), FANS[0].asStack(), "ASA", "CMC", "wPh", 'S', SHAFTS[0], 'A', new UnificationEntry(plate, AndesiteAlloy), 'C', new UnificationEntry(plate, WroughtIron), 'M', CASING.getIngredient(0), 'P', PROPELLERS[0]);

        VanillaRecipeHelper.addShapedRecipe(provider, AllItems.WRENCH.getId(), AllItems.WRENCH.asStack(), "PP", "PC", " S", 'P', new UnificationEntry(plate, Gold), 'C', COGWHEELS[ULV], 'S', new UnificationEntry(rod, Wood));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.CHUTE.getId(), AllBlocks.CHUTE.asStack(), "PGP", "PCP", "wPh", 'P', new UnificationEntry(plate, Iron), 'G', new UnificationEntry(gearSmall, Iron), 'C', createIngFromTag("forge", "chests/wooden"));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.BASIN.getId(), AllBlocks.BASIN.asStack(), "AhA", "AAA", 'A', new UnificationEntry(plate, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, AllItems.BRASS_HAND.getId(), AllItems.BRASS_HAND.asStack(), " A ", "PPP", "hPf", 'A', new UnificationEntry(plate, AndesiteAlloy), 'P', new UnificationEntry(plate, Brass));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.DEPLOYER.getId(), AllBlocks.DEPLOYER.asStack(), " C ", " R ", "hAf", 'C', CIRCUIT.getIngredient(ULV), 'R', AllItems.BRASS_HAND, 'A', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.DEPOT.getId(), AllBlocks.DEPOT.asStack(), " A ", "hCf", 'A', new UnificationEntry(plate, AndesiteAlloy), 'C', AllBlocks.ANDESITE_CASING.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.SPOUT.getId(), AllBlocks.SPOUT.asStack(), " C ", "hPf", 'C', MECHANICAL_PUMPS[ULV], 'P', AllBlocks.COPPER_CASING.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.MECHANICAL_CRAFTER.getId(), new ItemStack(AllBlocks.MECHANICAL_CRAFTER.asItem(), 3), " C ", " R ", "wAh", 'C', CIRCUIT.getIngredient(ULV), 'R', Blocks.CRAFTING_TABLE, 'A', AllBlocks.BRASS_CASING);

        if(GTCEuAPI.isHighTier()) {
            VanillaRecipeHelper.addShapedRecipe(provider, SAWS[9].getId(), SAWS[9].asStack(), "GSG", "MCM", "OHO", 'G', CIRCUIT.getIngredient(UHV), 'S', new UnificationEntry(toolHeadBuzzSaw, Neutronium), 'M', MOTOR.getIngredient(UHV), 'C', CASING.getIngredient(UHV), 'H', SHAFTS[UHV].asStack(), 'O', CONVEYOR.getIngredient(UHV));
        }

        for (int beltTier = 0; beltTier < BM.length; beltTier++) {
            Material beltMaterial = BM[beltTier];
            VanillaRecipeHelper.addShapedRecipe(provider, BELT_CONNECTORS[beltTier].getId(), BELT_CONNECTORS[beltTier].asStack(), "PPP", "PPP", "f h", 'P', new UnificationEntry(plate, beltMaterial));
        }
    }
}
