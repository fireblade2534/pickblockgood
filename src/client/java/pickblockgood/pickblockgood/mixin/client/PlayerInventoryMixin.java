package pickblockgood.pickblockgood.mixin.client;

import pickblockgood.pickblockgood.PickblockgoodClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;


@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Shadow @Final private PlayerEntity player;

    @Shadow @Final private DefaultedList<ItemStack> main;

    @Shadow private int selectedSlot;

    @WrapMethod(method = "getSwappableHotbarSlot")
    private int injected(Operation<Integer> original){
        PickblockgoodClient.LOGGER.info("afwweaf");

		for (int i = 0; i < 9; i++) {
			int j = (this.selectedSlot + i) % 9;
			if (this.main.get(j).isEmpty()) {
				return j;
			}
		}

        boolean isCreative = player.getAbilities().creativeMode;
        List<String> doNotReplaceItemTags=PickblockgoodClient.config.doNotReplaceItemTagsList();

        outerLoop:
		for (int ix = 0; ix < 9; ix++) {
            int j = (this.selectedSlot + ix) % 9;
            ItemStack slotItem=this.main.get(j);

            if ((PickblockgoodClient.config.preventReplaceInCreative && isCreative) || !isCreative){
                RegistryEntry<Item> slotEntry=slotItem.getRegistryEntry();
                for (String listItem : doNotReplaceItemTags){
                    if (slotEntry.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(listItem)))){
                        continue outerLoop;
                    }
                }
            }
            if (!slotItem.hasEnchantments()) {
                return j;
            }
		}
        return this.selectedSlot;
    }
}
