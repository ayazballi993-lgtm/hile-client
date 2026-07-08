package com.hileclient.gui

import com.hileclient.module.ModuleManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class ClickGUI : Screen(Text.literal("Hile Client")) {
    private var scrollY = 0.0
    private val itemHeight = 25
    private val panelColor = 0xAA0000FF.toInt()
    private val buttonColor = 0xFF5555FF.toInt()

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        context.fill(0, 0, width, height, panelColor)
        super.render(context, mouseX, mouseY, delta)

        var y = 40 - scrollY
        for (mod in ModuleManager.modules) {
            if (y + itemHeight > 0 && y < height) {
                context.fill(width / 2 - 100, y.toInt(), width / 2 + 100, (y + itemHeight).toInt(), buttonColor)
                val text = "${mod.name}: ${if (mod.enabled) "§aAcik" else "§cKapali"}"
                context.drawTextWithShadow(textRenderer, text, width / 2 - 95, y.toInt() + 6, 0xFFFFFF)
            }
            y += itemHeight
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        var y = 40 - scrollY
        for (mod in ModuleManager.modules) {
            if (mouseY >= y && mouseY <= y + itemHeight && mouseX >= width / 2 - 100 && mouseX <= width / 2 + 100) {
                mod.toggle()
                return true
            }
            y += itemHeight
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double): Boolean {
        scrollY = (scrollY - verticalAmount * 10).coerceIn(0.0, (ModuleManager.modules.size * itemHeight - height + 40).toDouble().coerceAtLeast(0.0))
        return true
    }

    override fun shouldPause() = false
    override fun close() { client?.setScreen(null) }
}
