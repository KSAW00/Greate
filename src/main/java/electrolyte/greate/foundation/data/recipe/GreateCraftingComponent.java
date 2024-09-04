package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateCraftingComponent {

    public static Component SHAFT;


    public static void init() {
        SHAFT = new Component(Stream.of(new Object[][]{
                {0, SHAFTS[ULV]},
                {1, SHAFTS[LV]},
                {2, SHAFTS[MV]},
                {3, SHAFTS[HV]},
                {4, SHAFTS[EV]},
                {5, SHAFTS[IV]},
                {6, SHAFTS[LuV]},
                {7, SHAFTS[ZPM]},
                {8, SHAFTS[UV]},
                {9, SHAFTS[UHV]}
        }).collect(Collectors.toMap(d -> (Integer) d[0], d -> d[1])));
    }
}
