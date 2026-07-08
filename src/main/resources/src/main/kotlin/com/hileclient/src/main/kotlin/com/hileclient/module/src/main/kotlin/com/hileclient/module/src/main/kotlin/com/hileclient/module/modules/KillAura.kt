package com.hileclient.module.modules

import com.hileclient.friend.FriendManager
import com.hileclient.module.Module
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Hand
import kotlin.math.*

object KillAura : Module("KillAura") {
    private val mc get() = MinecraftClient.getInstance()
    var range = 4.5
    var delay = 8
    private var cooldown = 0

    override fun getSettings() = mapOf("range" to range, "delay" to delay)
    override fun applySettings(settings: Map<String, Any>) {
        if (settings.containsKey("range")) range = (settings["range"] as Number).toDouble()
        if (settings.containsKey("delay")) delay = (settings["delay"] as Number).toInt()
    }

    override fun onTick() {
        if (cooldown > 0) { cooldown--; return }
        val player = mc.player ?: return
        val target = mc.world?.players?.asSequence()
            ?.filter { it != player && it.isAlive && player.distanceTo(it) <= range && !FriendManager.isFriend(it.gameProfile.name) }
            ?.sortedBy { player.distanceTo(it) }
            ?.firstOrNull() ?: return

        val diffX = target.x - player.x
        val diffY = (target.y + target.height / 2) - (player.y + player.eyeHeight)
        val diffZ = target.z - player.z
        val dist = sqrt(diffX * diffX + diffZ * diffZ)
        player.yaw = Math.toDegrees(atan2(diffZ, diffX)).toFloat() - 90f
        player.pitch = Math.toDegrees(-atan2(diffY, dist.toDouble())).toFloat()

        mc.interactionManager?.attackEntity(player, target)
        player.swingHand(Hand.MAIN_HAND)
        cooldown = delay
    }
}
