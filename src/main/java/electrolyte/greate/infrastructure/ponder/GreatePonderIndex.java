package electrolyte.greate.infrastructure.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.*;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.PumpScenes;
import electrolyte.greate.infrastructure.ponder.scenes.TieredFanScenes;

import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.CrushingWheels.CRUSHING_WHEELS;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Gearboxes.VERTICAL_GEARBOXES;
import static electrolyte.greate.registry.MechanicalMixers.MECHANICAL_MIXERS;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;
import static electrolyte.greate.registry.Millstones.MILLSTONES;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.*;

public class GreatePonderIndex {

    private static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Create.ID);

    public static void register() {
        HELPER.forComponents(SHAFTS).addStoryBoard("shaft/relay", KineticsScenes::shaftAsRelay, AllPonderTags.KINETIC_RELAYS);
        HELPER.forComponents(SHAFTS).addStoryBoard("shaft/encasing", KineticsScenes::shaftsCanBeEncased);
        HELPER.forComponents(ANDESITE_ENCASED_SHAFTS).addStoryBoard("shaft/encasing", KineticsScenes::shaftsCanBeEncased);
        HELPER.forComponents(BRASS_ENCASED_SHAFTS).addStoryBoard("shaft/encasing", KineticsScenes::shaftsCanBeEncased);
        HELPER.forComponents(COGWHEELS)
                .addStoryBoard("cog/small", KineticsScenes::cogAsRelay, AllPonderTags.KINETIC_RELAYS)
                .addStoryBoard("cog/speedup", KineticsScenes::cogsSpeedUp)
                .addStoryBoard("cog/encasing", KineticsScenes::cogwheelsCanBeEncased);
        HELPER.forComponents(LARGE_COGWHEELS)
                .addStoryBoard("cog/speedup", KineticsScenes::cogsSpeedUp)
                .addStoryBoard("cog/large", KineticsScenes::largeCogAsRelay, AllPonderTags.KINETIC_RELAYS)
                .addStoryBoard("cog/encasing", KineticsScenes::cogwheelsCanBeEncased);
        HELPER.forComponents(BELT_CONNECTORS)
                .addStoryBoard("belt/connect", BeltScenes::beltConnector, AllPonderTags.KINETIC_RELAYS)
                .addStoryBoard("belt/directions", BeltScenes::directions)
                .addStoryBoard("belt/transport", BeltScenes::transport, AllPonderTags.LOGISTICS)
                .addStoryBoard("belt/encasing", BeltScenes::beltsCanBeEncased);
        HELPER.forComponents(GEARBOXES).addStoryBoard("gearbox", KineticsScenes::gearbox, AllPonderTags.KINETIC_RELAYS);
        HELPER.forComponents(VERTICAL_GEARBOXES).addStoryBoard("gearbox", KineticsScenes::gearbox, AllPonderTags.KINETIC_RELAYS);
        HELPER.forComponents(FANS)
                .addStoryBoard("fan/direction", FanScenes::direction, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("fan/processing", TieredFanScenes::processing);
        HELPER.forComponents(MILLSTONES).addStoryBoard("millstone", ProcessingScenes::millstone);
        HELPER.forComponents(CRUSHING_WHEELS).addStoryBoard("crushign_wheel", ProcessingScenes::crushingWheels);
        HELPER.forComponents(MECHANICAL_MIXERS).addStoryBoard("mechanical_mixer/mixing", ProcessingScenes::mixing);
        HELPER.forComponents(MECHANICAL_PRESSES).addStoryBoard("mechanical_press/compacting", ProcessingScenes::compacting);
        HELPER.forComponents(SAWS)
                .addStoryBoard("mechanical_saw/processing", MechanicalSawScenes::processing, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_saw/breaker", MechanicalSawScenes::treeCutting)
                .addStoryBoard("mechanical_saw/contraption", MechanicalSawScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);
        HELPER.forComponents(MECHANICAL_PUMPS)
                .addStoryBoard("mechanical_pump/flow", PumpScenes::flow, AllPonderTags.FLUIDS, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_pump/speed", PumpScenes::speed);
    }
}
