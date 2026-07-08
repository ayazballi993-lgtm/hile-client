package com.hileclient.module

abstract class Module(val name: String) {
    var enabled = false
    open fun onEnable() {}
    open fun onDisable() {}
    open fun onTick() {}
    fun toggle() {
        enabled = !enabled
        if (enabled) onEnable() else onDisable()
    }
    open fun getSettings(): Map<String, Any> = mapOf()
    open fun applySettings(settings: Map<String, Any>) {}
}
