package me.ezeh.language

import jdk.nashorn.api.scripting.ScriptObjectMirror
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginLoader
import org.bukkit.plugin.PluginLogger
import java.io.File
import java.io.InputStream

class JavaSkriptPlugin(val skript: JS, val details: ScriptObjectMirror) : Plugin {
    val pluginName = details["name"]
    override fun getDataFolder() = File(name)

    override fun onCommand(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): Boolean {
        //TODO? I don't register the command to JavaSkriptPlugin instance
        return false
    }

    override fun saveDefaultConfig() {
        TODO("not implemented")
    }

    override fun getResource(p0: String?): InputStream {
        TODO("not implemented")
    }

    override fun getName() = pluginName as String

    override fun onTabComplete(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): MutableList<String> {
        TODO("not implemented")
    }

    override fun isNaggable() = false

    override fun getLogger() = PluginLogger(this)


    override fun reloadConfig() {
        TODO("not implemented")
    }

    override fun onEnable() {
        //TODO
    }

    override fun isEnabled() = true //TODO

    override fun onLoad() {
        TODO("not implemented")
    }

    override fun setNaggable(p0: Boolean) {
        TODO("not implemented")
    }

    override fun getConfig(): FileConfiguration {
        TODO("not implemented")
    }

    override fun getPluginLoader(): PluginLoader {
        TODO("not implemented")
    }

    override fun getDescription(): PluginDescriptionFile {
        return PluginDescriptionFile(name, (details["version"] as Double).toString(), "me.ezeh.language.JavaSkript")
    }

    override fun getServer() = Bukkit.getServer() // TODO check that this is OK

    override fun onDisable() {

        // TODO
    }

    override fun getDefaultWorldGenerator(p0: String?, p1: String?): ChunkGenerator {
        TODO("not implemented")
    }

    override fun saveConfig() {
        TODO("not implemented")
    }

    override fun saveResource(p0: String?, p1: Boolean) {
        TODO("not implemented")
    }
}