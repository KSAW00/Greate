package electrolyte.greate.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import net.minecraft.world.item.Item;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.simibubi.create.AllItems.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class ModItems {

    // Alloy
    public static final ItemEntry<Item>[] ALLOYS = new ItemEntry[10];
    public static ItemEntry<Item>
            STEEL_ALLOY,
            ALUMINIUM_ALLOY,
            STAINLESS_STEEL_ALLOY,
            TITANIUM_ALLOY,
            TUNGSTENSTEEL_ALLOY,
            PALLADIUM_ALLOY,
            NAQUADAH_ALLOY,
            DARMSTADTIUM_ALLOY,
            NEUTRONIUM_ALLOY;

    // Whisk
    public static final ItemEntry<Item>[] WHISKS = new ItemEntry[10];
    public static ItemEntry<Item>
            STEEL_WHISK,
            ALUMINIUM_WHISK,
            STAINLESS_STEEL_WHISK,
            TITANIUM_WHISK,
            TUNGSTENSTEEL_WHISK,
            PALLADIUM_WHISK,
            NAQUADAH_WHISK,
            DARMSTADTIUM_WHISK,
            NEUTRONIUM_WHISK;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        ALLOYS[ULV] = ANDESITE_ALLOY;
        ALLOYS[LV] = STEEL_ALLOY = alloy(LV);
        ALLOYS[MV] = ALUMINIUM_ALLOY = alloy(MV);
        ALLOYS[HV] = STAINLESS_STEEL_ALLOY = alloy(HV);
        ALLOYS[EV] = TITANIUM_ALLOY = alloy(EV);
        ALLOYS[IV] = TUNGSTENSTEEL_ALLOY = alloy(IV);
        ALLOYS[LuV] = PALLADIUM_ALLOY = alloy(LuV);
        ALLOYS[ZPM] = NAQUADAH_ALLOY = alloy(ZPM);
        ALLOYS[UV] = DARMSTADTIUM_ALLOY = alloy(UV);
        ALLOYS[UHV] = NEUTRONIUM_ALLOY = alloy(UHV);

        WHISKS[ULV] = WHISK;
        WHISKS[LV] = STEEL_WHISK = whisk(LV);
        WHISKS[MV] = ALUMINIUM_WHISK = whisk(MV);
        WHISKS[HV] = STAINLESS_STEEL_WHISK = whisk(HV);
        WHISKS[EV] = TITANIUM_WHISK = whisk(EV);
        WHISKS[IV] = TUNGSTENSTEEL_WHISK = whisk(IV);
        WHISKS[LuV] = PALLADIUM_WHISK = whisk(LuV);
        WHISKS[ZPM] = NAQUADAH_WHISK = whisk(ZPM);
        WHISKS[UV] = DARMSTADTIUM_WHISK = whisk(UV);
        WHISKS[UHV] = NEUTRONIUM_WHISK = whisk(UHV);
    }

    public static ItemEntry<Item> alloy(int tier) {
        return REGISTRATE
                .item(TM[tier].getName() + "_alloy", Item::new)
                .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                        .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
                .register();
    }

    public static ItemEntry<Item> whisk(int tier) {
        return REGISTRATE
                .item(TM[tier].getName() + "_whisk", Item::new)
                .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                        .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/whisk")))
                .register();
    }
}
