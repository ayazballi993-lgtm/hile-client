package com.hileclient.module.modules

import com.hileclient.module.Module
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Hand
import net.minecraft.util.hit.EntityHitResult

object TriggerBot : Module("TriggerBot") {
    private val mc get() = MinecraftClient.getInstance()
    private var cooldown = 0

    override fun onTick() {
        if (cooldown > 0) { cooldown--; return }
        val hit = mc.crosshairTarget as? EntityHitResult ?: return
        val entity = hit.entity
        if (entity !is LivingEntity) return

        mc.interactionManager?.attackEntity(mc.player, entity)
        mc.player?.swingHand(Hand.MAIN_HAND)
        cooldown = 10
    }
}
