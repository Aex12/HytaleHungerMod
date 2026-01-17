package es.xcm.hunger;

import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import es.xcm.hunger.config.HHMConfig;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.lang.reflect.Field;

public class HHMUtils {
    private static EntityEffect starvingEntityEffect;
    private static EntityEffect hungryEntityEffect;
    private static DamageCause starvationDamageCause;

    public static final String starvingEntityEffectId = "Starving";
    public static final String hungryEntityEffectId = "Hungry";


    @NonNullDecl
    public static EntityEffect getStarvingEntityEffect() {
        if (starvingEntityEffect == null) {
            starvingEntityEffect = EntityEffect.getAssetMap().getAsset(starvingEntityEffectId);
            assert starvingEntityEffect != null;
            HHMConfig conf = HytaleHungerMod.get().getConfig();
            try {
                // patch damageCalculator cooldown so that audio syncs properly with starvation tick rate (user configured)
                Field f = EntityEffect.class.getDeclaredField("damageCalculatorCooldown");
                f.setAccessible(true);
                f.setFloat(starvingEntityEffect, conf.getStarvationTickRate());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return starvingEntityEffect;
    }

    @NonNullDecl
    public static EntityEffect getHungryEntityEffect() {
        if (hungryEntityEffect == null) {
            hungryEntityEffect = EntityEffect.getAssetMap().getAsset(hungryEntityEffectId);
            assert hungryEntityEffect != null;
        }
        return hungryEntityEffect;
    }

    public static boolean activeEntityEffectIs (ActiveEntityEffect effect, String entityEffectId) {
        try {
            Field f = ActiveEntityEffect.class.getDeclaredField("entityEffectId");
            f.setAccessible(true);
            String id = (String) f.get(effect);
            return id.equals(entityEffectId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean activeEntityEffectIsStarving (ActiveEntityEffect effect) {
        return activeEntityEffectIs(effect, starvingEntityEffectId);
    }
    public static boolean activeEntityEffectIsHungry (ActiveEntityEffect effect) {
        return activeEntityEffectIs(effect, hungryEntityEffectId);
    }
    public static boolean activeEntityEffectIsHungerRelated (ActiveEntityEffect effect) {
        return activeEntityEffectIsStarving(effect) || activeEntityEffectIsHungry(effect);
    }

    @NonNullDecl
    public static DamageCause getStarvationDamageCause () {
        if (starvationDamageCause != null) return starvationDamageCause;
        starvationDamageCause = new DamageCause("Starvation", "Starvation", false, true, true);
        return starvationDamageCause;
    }
}
