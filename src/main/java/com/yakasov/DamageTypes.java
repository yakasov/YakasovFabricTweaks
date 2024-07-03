package com.yakasov;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface DamageTypes {
    RegistryKey<DamageType> DRAGON = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("dragon"));
    RegistryKey<DamageType> PHANTOM = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("phantom"));
    RegistryKey<DamageType> WARDEN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("warden"));

    static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(DRAGON, new DamageType("dragon", 0.1F));
        damageTypeRegisterable.register(PHANTOM, new DamageType("dragon", 0.1F));
        damageTypeRegisterable.register(WARDEN, new DamageType("dragon", 0.1F));
    }
}
