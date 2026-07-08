package com.hileclient.config

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hileclient.module.ModuleManager
import net.fabricmc.loader.api.FabricLoader
import java.io.File

object ConfigManager {
    private val gson = Gson()
    private val configDir = File(FabricLoader.getInstance().configDir.toFile(), "hileclient")

    fun save(name: String) {
        if (!configDir.exists()) configDir.mkdirs()
        val data = mutableMapOf<String, Any>()
        for (mod in ModuleManager.modules) {
            data[mod.name] = mapOf(
                "enabled" to mod.enabled,
                "settings" to mod.getSettings()
            )
        }
        val file = File(configDir, "$name.json")
        file.writeText(gson.toJson(data))
    }

    fun load(name: String) {
        val file = File(configDir, "$name.json")
        if (!file.exists()) return
        val type = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
        val data: Map<String, Map<String, Any>> = gson.fromJson(file.readText(), type)
        for ((modName, modData) in data) {
            val mod = ModuleManager.modules.find { it.name == modName } ?: continue
            val enabled = modData["enabled"] as? Boolean ?: false
            if (enabled != mod.enabled) mod.toggle()
            val settings = modData["settings"] as? Map<String, Any> ?: emptyMap()
            mod.applySettings(settings)
        }
    }

    fun loadDefault() {
        val defaultFile = File(configDir, "default.json")
        if (defaultFile.exists()) load("default")
    }
}
