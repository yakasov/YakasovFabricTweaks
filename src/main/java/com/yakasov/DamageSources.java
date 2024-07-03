package com.yakasov;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;

public class DamageSources {
    public final Registry<DamageType> registry;
    private final DamageSource dragon;
    private final DamageSource phantom;
    private final DamageSource warden;

    public DamageSources(DynamicRegistryManager registryManager) {
        this.registry = registryManager.get(RegistryKeys.DAMAGE_TYPE);
        this.dragon = new DamageSource(this.registry.entryOf(DamageTypes.DRAGON));
        this.phantom = new DamageSource(this.registry.entryOf(DamageTypes.PHANTOM));
        this.warden = new DamageSource(this.registry.entryOf(DamageTypes.WARDEN));
    }

    public DamageSource dragon() { return this.dragon; }
    public DamageSource phantom() { return this.phantom; }
    public DamageSource warden() { return this.warden; }
}
