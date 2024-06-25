package com.yakasov.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.DyeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jetbrains.annotations.Nullable;

@Mixin(LivingEntity.class)
public class YellowLeatherFoolsPiglins {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("yakasovtweaks");

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("RETURN"), cancellable = true)
    private void addPiglinYellowLeatherCheck(@Nullable Entity entity, CallbackInfoReturnable<Double> cir) {
        if (entity != null) {
            LivingEntity self = (LivingEntity) (Object) this;
            EntityType<?> entityType = entity.getType();
            double scalingFactor = cir.getReturnValue();

            if (entityType == EntityType.PIGLIN_BRUTE) {
                LOGGER.info("PIGLIN_BRUTE is true!");
            }

            if (entityType == EntityType.PIGLIN_BRUTE && isWearingYellowArmor(self)) {
                LOGGER.info("isWearingYellowArmor is true!");
                scalingFactor *= 0.5;
            }

            cir.setReturnValue(scalingFactor);
        }
    }

    @Unique
    private boolean isWearingYellowArmor(LivingEntity entity) {
        for (ItemStack armorItem : entity.getArmorItems()) {
            if (armorItem.getItem() instanceof ArmorItem armor) {
                ArmorMaterial material = armor.getMaterial().value();
                LOGGER.info("1");
                for (ArmorMaterial.Layer layer : material.layers()) {
                    LOGGER.info("2");
                    LOGGER.info("Check 1 " + (armor.getMaterial() == ArmorMaterials.LEATHER)); // true
                    LOGGER.info("Check 2 " + (layer.isDyeable())); // false
                    LOGGER.info("Check 3 " + (armorItem.getItem() instanceof DyeItem dyeItem)); // false
                    if (armor.getMaterial() == ArmorMaterials.LEATHER && layer.isDyeable() && armorItem.getItem() instanceof DyeItem dyeItem) {
                        LOGGER.info("All checks are true...?");
                        return dyeItem.getColor() == DyeColor.YELLOW;
                    }
                }
            }
        }
        return false;
    }
}