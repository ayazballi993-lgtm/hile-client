package com.hileclient.module.modules

import com.hileclient.module.Module
import net.minecraft.client.MinecraftClient
import net.minecraft.item.Items
import net.minecraft.util.Hand

object AutoPot : Module("AutoPot") {
    private val mc get() = MinecraftClient.getInstance()
    var healthThreshold = 10f
    var delay = 20
    private var cooldown = 0

    override fun getSettings() = mapOf("healthThreshold" to healthThreshold, "delay" to delay)
    override fun applySettings(settings: Map<String, Any>) {
        if (settings.containsKey("healthThreshold")) healthThreshold = (settings["healthThreshold"] as Number).toFloat()
        if (settings.containsKey("delay")) delay = (settings["delay"] as Number).toInt()
    }

    override fun onTick() {
        if (cooldown > 0) { cooldown--; return }
        val player = mc.player ?: return
        if (player.health > healthThreshold) return

        for (i in 0..8) {
            val stack = player.inventory.getStack(i)
            if (stack.item == Items.SPLASH_POTION || stack.item == Items.POTION) {
                val prevSlot = player.inventory.selectedSlot
                player.inventory.selectedSlot = i
                mc.interactionManager?.interactItem(player, Hand.MAIN_HAND)
                player.inventory.selectedSlot = prevSlot
                cooldown = delay
                break
            }
        }
    }
}
