package electrolyte.greate.foundation.data;

import com.gregtechceu.gtceu.GTCEu;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Cogwheels.*;
import static electrolyte.greate.registry.Shafts.ANDESITE_ENCASED_SHAFTS;
import static electrolyte.greate.registry.Shafts.BRASS_ENCASED_SHAFTS;

public class GreateTagGen {

    private static final TagKey<Item> HIDE_FROM_RECIPE_VIEWERS = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation("c", "hidden_from_recipe_viewers"));

    public static class GreateBlockTagGen extends BlockTagsProvider {

        public GreateBlockTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(Provider pProvider) {}
    }

    public static class GreateItemTagGen extends ItemTagsProvider {

        public GreateItemTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> tagLookup, String modId, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, tagLookup, modId, existingFileHelper);
        }

        @Override
        protected void addTags(Provider pProvider) {
            for(int i = 0; i < TM.length; i++) {
                this.tag(HIDE_FROM_RECIPE_VIEWERS)
                        .add(ANDESITE_ENCASED_SHAFTS[i].asItem())
                        .add(BRASS_ENCASED_SHAFTS[i].asItem())
                        .add(ANDESITE_ENCASED_COGWHEELS[i].asItem())
                        .add(ANDESITE_ENCASED_LARGE_COGWHEELS[i].asItem())
                        .add(BRASS_ENCASED_COGWHEELS[i].asItem())
                        .add(BRASS_ENCASED_LARGE_COGWHEELS[i].asItem());
            }
            this.tag(HIDE_FROM_RECIPE_VIEWERS)
                    .add(AllBlocks.ANDESITE_ENCASED_SHAFT.asItem())
                    .add(AllBlocks.BRASS_ENCASED_SHAFT.asItem())
                    .add(AllBlocks.ANDESITE_ENCASED_COGWHEEL.asItem())
                    .add(AllBlocks.ANDESITE_ENCASED_LARGE_COGWHEEL.asItem())
                    .add(AllBlocks.BRASS_ENCASED_COGWHEEL.asItem())
                    .add(AllBlocks.BRASS_ENCASED_LARGE_COGWHEEL.asItem())
                    .add(AllItems.BELT_CONNECTOR.asItem())
                    .add(AllBlocks.COGWHEEL.asItem())
                    .add(AllBlocks.LARGE_COGWHEEL.asItem())
                    .add(AllBlocks.MILLSTONE.asItem())
                    .add(AllBlocks.CRUSHING_WHEEL.asItem())
                    .add(AllBlocks.GEARBOX.asItem())
                    .add(AllBlocks.MECHANICAL_PRESS.asItem())
                    .add(AllBlocks.MECHANICAL_MIXER.asItem())
                    .add(AllBlocks.MECHANICAL_SAW.asItem())
                    .add(AllBlocks.ENCASED_FAN.asItem())
                    .add(AllItems.WHISK.asItem())
                    .add(AllItems.PROPELLER.asItem())
                    .add(AllItems.VERTICAL_GEARBOX.asItem())
                    .add(AllBlocks.SHAFT.asItem())
                    .add(AllItems.COPPER_SHEET.asItem())
                    .add(AllItems.IRON_SHEET.asItem())
                    .add(AllItems.GOLDEN_SHEET.asItem())
                    .add(AllItems.BRASS_SHEET.asItem())
                    .add(AllItems.CRUSHED_COPPER.asItem())
                    .add(AllItems.CRUSHED_IRON.asItem())
                    .add(AllItems.CRUSHED_PLATINUM.asItem())
                    .add(AllItems.CRUSHED_GOLD.asItem())
                    .add(AllItems.CRUSHED_LEAD.asItem())
                    .add(AllItems.CRUSHED_NICKEL.asItem())
                    .add(AllItems.CRUSHED_TIN.asItem())
                    .add(AllItems.CRUSHED_SILVER.asItem())
                    .add(AllItems.CRUSHED_ZINC.asItem())
                    .add(AllItems.WHEAT_FLOUR.asItem())
                    .add(TagEntry.element(GTCEu.id("obsidian_dust")))
                    .add(TagEntry.element(GTCEu.id("netherrack_dust")));
        }
    }
}
