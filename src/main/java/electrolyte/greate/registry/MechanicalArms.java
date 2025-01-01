package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.arm.TieredMechanicalArmBlock;
import electrolyte.greate.content.kinetics.arm.TieredMechanicalArmItem;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class MechanicalArms{

    public static BlockEntry<TieredMechanicalArmBlock>[] MECHANICAL_ARMS = new BlockEntry[10];
    public static BlockEntry<TieredMechanicalArmBlock> ANDESITE_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> STEEL_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> ALUMINIUM_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> STAINLESS_STEEL_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> TITANIUM_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> TUNGSTENSTEEL_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> PALLADIUM_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> NAQUADAH_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> DARMSTADTIUM_MECHANICAL_ARM;
    public static BlockEntry<TieredMechanicalArmBlock> NEUTRONIUM_MECHANICAL_ARM;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        MECHANICAL_ARMS[ULV] = ANDESITE_MECHANICAL_ARM = mechanicalArm(ULV, 0.5);
        MECHANICAL_ARMS[LV] = STEEL_MECHANICAL_ARM = mechanicalArm(LV, 1.0);
        MECHANICAL_ARMS[MV] = ALUMINIUM_MECHANICAL_ARM = mechanicalArm(MV, 1.5);
        MECHANICAL_ARMS[HV] = STAINLESS_STEEL_MECHANICAL_ARM = mechanicalArm(HV, 2.0);
        MECHANICAL_ARMS[EV] = TITANIUM_MECHANICAL_ARM = mechanicalArm(EV, 2.5);
        MECHANICAL_ARMS[IV] = TUNGSTENSTEEL_MECHANICAL_ARM = mechanicalArm(IV, 3.0);
        MECHANICAL_ARMS[LuV] = PALLADIUM_MECHANICAL_ARM = mechanicalArm(LuV, 3.5);
        MECHANICAL_ARMS[ZPM] = NAQUADAH_MECHANICAL_ARM = mechanicalArm(ZPM, 4.0);
        MECHANICAL_ARMS[UV] = DARMSTADTIUM_MECHANICAL_ARM = mechanicalArm(UV, 4.5);
        MECHANICAL_ARMS[UHV] = NEUTRONIUM_MECHANICAL_ARM = mechanicalArm(UHV, 5.0);
    }

    public static BlockEntry<TieredMechanicalArmBlock> mechanicalArm(int tier, double stressImpact) {
        return REGISTRATE.block(TM[tier].getName() + "_mechanical_arm", TieredMechanicalArmBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW))
                .transform(axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .transform(GreateBuilderTransformers.tieredMechanicalArm())
                .blockstate((c, p) -> p.getVariantBuilder(c.get())
                        .forAllStates(s -> ConfiguredModel.builder()
                                .modelFile(AssetLookup.partialBaseModel(c, p))
                                .rotationX(s.getValue(TieredMechanicalArmBlock.CEILING) ? 180 : 0)
                                .build()))
                .item(TieredMechanicalArmItem::new)
                .transform(customItemModel())
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
