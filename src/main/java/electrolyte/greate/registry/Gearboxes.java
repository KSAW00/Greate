package electrolyte.greate.registry;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredVerticalGearboxItem;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class Gearboxes {

    // Gearbox
    public static final BlockEntry<TieredGearboxBlock>[] GEARBOXES = new BlockEntry[10];
    public static BlockEntry<TieredGearboxBlock>
            ANDESITE_GEARBOX,
            STEEL_GEARBOX,
            ALUMINIUM_GEARBOX,
            STAINLESS_STEEL_GEARBOX,
            TITANIUM_GEARBOX,
            TUNGSTENSTEEL_GEARBOX,
            PALLADIUM_GEARBOX,
            NAQUADAH_GEARBOX,
            DARMSTADTIUM_GEARBOX,
            NEUTRONIUM_GEARBOX;

    // Vertical gearbox
    public static final ItemEntry<TieredVerticalGearboxItem>[] VERTICAL_GEARBOXES = new ItemEntry[10];
    public static ItemEntry<TieredVerticalGearboxItem>
            ANDESITE_VERTICAL_GEARBOX,
            STEEL_VERTICAL_GEARBOX,
            ALUMINIUM_VERTICAL_GEARBOX,
            STAINLESS_STEEL_VERTICAL_GEARBOX,
            TITANIUM_VERTICAL_GEARBOX,
            TUNGSTENSTEEL_VERTICAL_GEARBOX,
            PALLADIUM_VERTICAL_GEARBOX,
            NAQUADAH_VERTICAL_GEARBOX,
            DARMSTADTIUM_VERTICAL_GEARBOX,
            NEUTRONIUM_VERTICAL_GEARBOX;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        // Gearbox
        GEARBOXES[ULV] = ANDESITE_GEARBOX = gearbox(ULV);
        GEARBOXES[LV] = STEEL_GEARBOX = gearbox(LV);
        GEARBOXES[MV] = ALUMINIUM_GEARBOX = gearbox( MV);
        GEARBOXES[HV] = STAINLESS_STEEL_GEARBOX = gearbox(HV);
        GEARBOXES[EV] = TITANIUM_GEARBOX = gearbox(EV);
        GEARBOXES[IV] = TUNGSTENSTEEL_GEARBOX = gearbox(IV);
        GEARBOXES[LuV] = PALLADIUM_GEARBOX = gearbox(LuV);
        GEARBOXES[ZPM] = NAQUADAH_GEARBOX = gearbox(ZPM);
        GEARBOXES[UV] = DARMSTADTIUM_GEARBOX = gearbox(UV);
        GEARBOXES[UHV] = NEUTRONIUM_GEARBOX = gearbox(UHV);

        // Vertical gearbox
        VERTICAL_GEARBOXES[ULV] = ANDESITE_VERTICAL_GEARBOX = verticalGearbox(ULV);
        VERTICAL_GEARBOXES[LV] = STEEL_VERTICAL_GEARBOX = verticalGearbox(LV);
        VERTICAL_GEARBOXES[MV] = ALUMINIUM_VERTICAL_GEARBOX = verticalGearbox(MV);
        VERTICAL_GEARBOXES[HV] = STAINLESS_STEEL_VERTICAL_GEARBOX = verticalGearbox(HV);
        VERTICAL_GEARBOXES[EV] = TITANIUM_VERTICAL_GEARBOX = verticalGearbox(EV);
        VERTICAL_GEARBOXES[IV] = TUNGSTENSTEEL_VERTICAL_GEARBOX = verticalGearbox(IV);
        VERTICAL_GEARBOXES[LuV] = PALLADIUM_VERTICAL_GEARBOX = verticalGearbox(LuV);
        VERTICAL_GEARBOXES[ZPM] = NAQUADAH_VERTICAL_GEARBOX = verticalGearbox(ZPM);
        VERTICAL_GEARBOXES[UV] = DARMSTADTIUM_VERTICAL_GEARBOX = verticalGearbox(UV);
        VERTICAL_GEARBOXES[UHV] = NEUTRONIUM_VERTICAL_GEARBOX = verticalGearbox(UHV);
    }

    public static BlockEntry<TieredGearboxBlock> gearbox(int tier) {
        return REGISTRATE
                .block(TM[tier].getName() + "_gearbox", TieredGearboxBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(Properties::noOcclusion)
                .properties(p -> p.mapColor(MapColor.PODZOL).pushReaction(PushReaction.PUSH_ONLY))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(GreateBuilderTransformers.tieredGearbox())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
                .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                        (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static ItemEntry<TieredVerticalGearboxItem> verticalGearbox(int tier) {
        return REGISTRATE
                .item(TM[tier].getName() + "_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, GEARBOXES[tier].get()))
                .transform(GreateBuilderTransformers.tieredGearboxVertical())
                .register();
    }
}
