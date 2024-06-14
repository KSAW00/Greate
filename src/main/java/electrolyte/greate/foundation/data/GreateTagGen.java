package electrolyte.greate.foundation.data;

import com.simibubi.create.AllBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Cogwheels.*;
import static electrolyte.greate.registry.Shafts.ANDESITE_ENCASED_SHAFTS;
import static electrolyte.greate.registry.Shafts.BRASS_ENCASED_SHAFTS;

public class GreateTagGen extends ItemTagsProvider {

    private static final TagKey<Block> HIDE_FROM_RECIPE_VIEWERS = ForgeRegistries.BLOCKS.tags().createTagKey(new ResourceLocation("c", "hidden_from_recipe_viewers"));

    public GreateTagGen(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {}

    public static class GreateBlockTagGen extends BlockTagsProvider {

        public GreateBlockTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(Provider pProvider) {
            for(int i = 0; i < TM.length; i++) {
                this.tag(HIDE_FROM_RECIPE_VIEWERS)
                        .add(AllBlocks.ANDESITE_ENCASED_SHAFT.get())
                        .add(AllBlocks.BRASS_ENCASED_SHAFT.get())
                        .add(AllBlocks.ANDESITE_ENCASED_COGWHEEL.get())
                        .add(AllBlocks.ANDESITE_ENCASED_LARGE_COGWHEEL.get())
                        .add(AllBlocks.BRASS_ENCASED_COGWHEEL.get())
                        .add(AllBlocks.BRASS_ENCASED_LARGE_COGWHEEL.get())
                        .add(ANDESITE_ENCASED_SHAFTS[i].get())
                        .add(BRASS_ENCASED_SHAFTS[i].get())
                        .add(ANDESITE_ENCASED_COGWHEELS[i].get())
                        .add(ANDESITE_ENCASED_LARGE_COGWHEELS[i].get())
                        .add(BRASS_ENCASED_COGWHEELS[i].get())
                        .add(BRASS_ENCASED_LARGE_COGWHEELS[i].get());
            }
        }
    }
}
