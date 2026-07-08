package com.hileclient.module

import com.hileclient.module.modules.*

object ModuleManager {
    val modules = mutableListOf<Module>()
    val killAura = KillAura()
    val autoPot = AutoPot()
    val triggerBot = TriggerBot()
    val aimAssist = AimAssist()

    fun init() {
        modules.addAll(listOf(killAura, autoPot, triggerBot, aimAssist))
    }

    fun onTick() {
        modules.filter { it.enabled }.forEach { it.onTick() }
    }
}
