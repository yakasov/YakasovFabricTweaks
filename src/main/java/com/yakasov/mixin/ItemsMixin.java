package com.yakasov.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static net.minecraft.item.Items.register;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(method = "register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;", at = @At("HEAD"), cancellable = true)
    private static void makeObsidianFireproof(Block block, CallbackInfoReturnable<Item> cir) {
        if (block == Blocks.OBSIDIAN) {
            cir.setReturnValue(register(new BlockItem(Blocks.OBSIDIAN, new Item.Settings().fireproof())));
        }
    }
}