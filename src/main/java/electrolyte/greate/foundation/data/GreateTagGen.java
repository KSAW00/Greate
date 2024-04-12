package electrolyte.greate.foundation.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class GreateTagGen extends ItemTagsProvider {

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
        protected void addTags(Provider pProvider) {}
    }
}
