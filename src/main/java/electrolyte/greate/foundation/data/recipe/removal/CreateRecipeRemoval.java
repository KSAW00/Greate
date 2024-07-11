package electrolyte.greate.foundation.data.recipe.removal;

import com.simibubi.create.Create;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class CreateRecipeRemoval {

    public static void disableCreateRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crafting/materials/andesite_alloy"));
        recipe.accept(Create.asResource("crafting/materials/andesite_alloy_from_zinc"));

        recipe.accept(Create.asResource("crafting/kinetics/basin"));
        recipe.accept(Create.asResource("crafting/kinetics/belt_connector"));
        recipe.accept(Create.asResource("crafting/kinetics/brass_hand"));
        recipe.accept(Create.asResource("crafting/kinetics/chute"));
        recipe.accept(Create.asResource("crafting/kinetics/cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/deployer"));
        recipe.accept(Create.asResource("crafting/kinetics/depot"));
        recipe.accept(Create.asResource("crafting/kinetics/encased_fan"));
        recipe.accept(Create.asResource("crafting/kinetics/gearbox"));
        recipe.accept(Create.asResource("crafting/kinetics/gearboxfrom_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/large_cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/large_cogwheel_from_little"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_crafter"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_mixer"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_press"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_pump"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_saw"));
        recipe.accept(Create.asResource("crafting/kinetics/millstone"));
        recipe.accept(Create.asResource("crafting/kinetics/propeller"));
        recipe.accept(Create.asResource("crafting/kinetics/shaft"));
        recipe.accept(Create.asResource("crafting/kinetics/spout"));
        recipe.accept(Create.asResource("crafting/kinetics/vertical_gearboxfrom_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/whisk"));
        recipe.accept(Create.asResource("crafting/kinetics/wrench"));

        recipe.accept(Create.asResource("cutting/andesite_alloy"));
        recipe.accept(Create.asResource("cutting/stripped_oak_log"));
        recipe.accept(Create.asResource("cutting/stripped_oak_wood"));
        recipe.accept(Create.asResource("cutting/stripped_spruce_log"));
        recipe.accept(Create.asResource("cutting/stripped_spruce_wood"));
        recipe.accept(Create.asResource("cutting/stripped_birch_log"));
        recipe.accept(Create.asResource("cutting/stripped_birch_wood"));
        recipe.accept(Create.asResource("cutting/stripped_jungle_log"));
        recipe.accept(Create.asResource("cutting/stripped_jungle_wood"));
        recipe.accept(Create.asResource("cutting/stripped_acacia_log"));
        recipe.accept(Create.asResource("cutting/stripped_warped_stem"));
        recipe.accept(Create.asResource("cutting/stripped_mangrove_log"));
        recipe.accept(Create.asResource("cutting/stripped_crimson_hyphae"));
        recipe.accept(Create.asResource("cutting/stripped_acacia_wood"));
        recipe.accept(Create.asResource("cutting/stripped_dark_oak_log"));
        recipe.accept(Create.asResource("cutting/stripped_dark_oak_wood"));
        recipe.accept(Create.asResource("cutting/stripped_mangrove_wood"));
        recipe.accept(Create.asResource("cutting/stripped_cherry_log"));
        recipe.accept(Create.asResource("cutting/stripped_cherry_wood"));
        recipe.accept(Create.asResource("cutting/stripped_crimson_stem"));
        recipe.accept(Create.asResource("cutting/stripped_warped_hyphae"));
        recipe.accept(Create.asResource("cutting/stripped_bamboo_block"));

        recipe.accept(Create.asResource("mechanical_crafting/crushing_wheel"));

        recipe.accept(Create.asResource("milling/andesite"));
        recipe.accept(Create.asResource("milling/bone"));
        recipe.accept(Create.asResource("milling/charcoal"));
        recipe.accept(Create.asResource("milling/clay"));
        recipe.accept(Create.asResource("milling/coal"));
        recipe.accept(Create.asResource("milling/cobblestone"));
        recipe.accept(Create.asResource("milling/granite"));
        recipe.accept(Create.asResource("milling/gravel"));
        recipe.accept(Create.asResource("milling/lapis_lazuli"));
        recipe.accept(Create.asResource("milling/wool"));
        recipe.accept(Create.asResource("milling/sugar_cane"));
        recipe.accept(Create.asResource("milling/terracotta"));
        recipe.accept(Create.asResource("milling/wheat"));

        recipe.accept(Create.asResource("mixing/andesite_alloy"));
        recipe.accept(Create.asResource("mixing/andesite_alloy_from_zinc"));

        recipe.accept(Create.asResource("pressing/iron_ingot"));
        recipe.accept(Create.asResource("pressing/gold_ingot"));
        recipe.accept(Create.asResource("pressing/copper_ingot"));
        recipe.accept(Create.asResource("pressing/brass_ingot"));
        recipe.accept(Create.asResource("pressing/sugar_cane"));
    }
}
