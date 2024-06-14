package electrolyte.greate.infrastructure.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistry;

import static com.simibubi.create.infrastructure.ponder.AllPonderTags.*;
import static electrolyte.greate.GreateValues.BM;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreatePonderTags {

    public static void register() {
        for(int i = 0; i < TM.length; i++) {
            PonderRegistry.TAGS.forTag(KINETIC_RELAYS)
                    .add(SHAFTS[i].get())
                    .add(COGWHEELS[i].get())
                    .add(LARGE_COGWHEELS[i].get())
                    .add(GEARBOXES[i].get());

            PonderRegistry.TAGS.forTag(KINETIC_APPLIANCES)
                    .add(FANS[i])
                    .add(MECHANICAL_PUMPS[i]);

            PonderRegistry.TAGS.forTag(CONTRAPTION_ACTOR)
                    .add(SAWS[i]);

            PonderRegistry.TAGS.forTag(FLUIDS)
                    .add(MECHANICAL_PUMPS[i]);
        }
        for(int i = 0; i < BM.length; i++) {
            PonderRegistry.TAGS.forTag(KINETIC_RELAYS)
                    .add(BELT_CONNECTORS[i].get());

            PonderRegistry.TAGS.forTag(LOGISTICS)
                    .add(BELT_CONNECTORS[i].get());
        }
    }
}
