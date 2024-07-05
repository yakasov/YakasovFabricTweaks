package com.yakasov;


import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registry.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DamageTypes implements DamageTypesInterface {
    public void onInitialize() {
//        Registry.register(Registries.DAMAGE_TYPE, DRAGON);
        // Registries.DAMAGE_TYPE is not real so... not sure how to get around this
    }
}

interface DamageTypesInterface {
    RegistryKey<DamageType> DRAGON = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("dragon"));
    RegistryKey<DamageType> PHANTOM = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("phantom"));
    RegistryKey<DamageType> WARDEN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("warden"));
}