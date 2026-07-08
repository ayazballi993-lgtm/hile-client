package com.hileclient.module.modules

import com.hileclient.module.Module
import net.minecraft.client.MinecraftClient
import kotlin.math.*

object AimAssist : Module("AimAssist") {
    private val mc get() = MinecraftClient.getInstance()
    var range = 5.0
    var smooth = 5.0

    override fun getSettings() = mapOf("range" to range, "smooth" to smooth)
    override fun applySettings(settings: Map<String, Any>) {
        if (settings.containsKey("range")) range = (settings["range"] as Number).toDouble()
        if (settings.containsKey("smooth")) smooth = (settings["smooth"] as Number).toDouble()
    }

    override fun onTick() {
        val player = mc.player ?: return
        val target = mc.world?.players?.asSequence()
            ?.filter { it != player && it.isAlive && player.distanceTo(it) <= range }
            ?.sortedBy { player.distanceTo(it) }
            ?.firstOrNull() ?: return

        val diffX = target.x - player.x
        val diffY = (target.y + target.height / 2) - (player.y + player.eyeHeight)
        val diffZ = target.z - player.z
        val dist = sqrt(diffX * diffX + diffZ * diffZ)

        val targetYaw = Math.toDegrees(atan2(diffZ, diffX)).toFloat() - 90f
        val targetPitch = Math.toDegrees(-atan2(diffY, dist.toDouble())).toFloat()

        player.yaw += ((targetYaw - player.yaw) / smooth).coerceIn(-45f, 45f)
        player.pitch += ((targetPitch - player.pitch) / smooth).coerceIn(-30f, 30f)
    }
}
