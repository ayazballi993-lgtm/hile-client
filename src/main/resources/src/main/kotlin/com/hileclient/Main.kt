package com.hileclient

import com.hileclient.config.ConfigManager
import com.hileclient.friend.FriendManager
import com.hileclient.gui.ClickGUI
import com.hileclient.module.ModuleManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.minecraft.command.argument.StringArgumentType

object Main : ClientModInitializer {
    private lateinit var openGuiKey: KeyBinding

    override fun onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(
            KeyBinding("key.hileclient.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "Hile Client")
        )

        ModuleManager.init()
        ConfigManager.loadDefault()

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (openGuiKey.wasPressed()) {
                client.setScreen(ClickGUI())
            }
            ModuleManager.onTick()
        }

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("gui").executes {
                    MinecraftClient.getInstance().send {
                        MinecraftClient.getInstance().setScreen(ClickGUI())
                    }
                    1
                }
            )
            dispatcher.register(
                literal("config").then(literal("save").then(argument("name", StringArgumentType.word()).executes { ctx ->
                    val name = StringArgumentType.getString(ctx, "name")
                    ConfigManager.save(name)
                    ctx.source.sendFeedback(net.minecraft.text.Text.literal("Config '$name' kaydedildi."))
                    1
                }))
            )
            dispatcher.register(
                literal("config").then(literal("load").then(argument("name", StringArgumentType.word()).executes { ctx ->
                    val name = StringArgumentType.getString(ctx, "name")
                    ConfigManager.load(name)
                    ctx.source.sendFeedback(net.minecraft.text.Text.literal("Config '$name' yüklendi."))
                    1
                }))
            )
            dispatcher.register(
                literal("friend").then(literal("add").then(argument("name", StringArgumentType.word()).executes { ctx ->
                    val name = StringArgumentType.getString(ctx, "name")
                    FriendManager.addFriend(name)
                    ctx.source.sendFeedback(net.minecraft.text.Text.literal("Arkadas eklendi: $name"))
                    1
                }))
            )
            dispatcher.register(
                literal("friend").then(literal("remove").then(argument("name", StringArgumentType.word()).executes { ctx ->
                    val name = StringArgumentType.getString(ctx, "name")
                    FriendManager.removeFriend(name)
                    ctx.source.sendFeedback(net.minecraft.text.Text.literal("Arkadas silindi: $name"))
                    1
                }))
            )
            dispatcher.register(
                literal("friend").then(literal("list").executes {
                    val list = FriendManager.getFriends().joinToString(", ")
                    MinecraftClient.getInstance().player?.sendMessage(net.minecraft.text.Text.literal("Arkadaslar: $list"), false)
                    1
                })
            )
        }
    }
}
