package me.ezeh.language

import org.bukkit.Bukkit
import org.bukkit.command.SimpleCommandMap
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredListener
import org.bukkit.plugin.java.JavaPlugin
import javax.script.ScriptException


class JavaSkript : JavaPlugin() {
    fun registerListeners() {
        val registeredListener = RegisteredListener(JS.pluginManager, { listener, event -> JS.pluginManager.onEvent(event) }, EventPriority.NORMAL, this, false)
        for (handler in HandlerList.getHandlerLists())
            handler.register(registeredListener)
    }

    fun prepareDataFolder() {
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                println("successfully created JavaSkript directory")
            } else {
                println("Unable to create JavaSkript directory, disabling.")
                Bukkit.getPluginManager().disablePlugin(this)
            }
        }

    }

    fun getSkripts(): HashSet<String> {
        prepareDataFolder()
        val skripts = HashSet<String>()
        dataFolder.listFiles().mapTo(skripts) { it.readText() }
        return skripts
    }

    override fun onEnable() {
        val cl = this::class.java.classLoader
        Thread.currentThread().contextClassLoader = cl //This practically make nashorn work while accessing my classes


        getSkripts().map {
            try {
                JavaSkriptExecutor().parse(it)
            } catch (e: ScriptException) {
                Exception("Unable to load plugin", e)// TODO store a map with file name as well
            }
        } // Parse the skripts

        addSkriptsToPluginList()
        registerListeners()


    }

    private fun addSkriptsToPluginList() {
        //you'll also wanna populate the lookupNames map with the name of your plugin against the Plugin instance, noting that, getPlugin does this: name.replace(' ', '_').toLowerCase(java.util.Locale.ENGLISH)
        // This mess of code adds my plugins to the plugin list
        val pluginManager = Bukkit.getPluginManager()

        val pluginsField = Bukkit.getPluginManager()::class.java.getDeclaredField("plugins")
        val lookupNamesField = Bukkit.getPluginManager()::class.java.getDeclaredField("lookupNames")
        val commandMapField = Bukkit.getPluginManager()::class.java.getDeclaredField("commandMap")

        pluginsField.isAccessible = true
        lookupNamesField.isAccessible = true
        commandMapField.isAccessible = true

        val plugins = ArrayList(pluginManager.plugins.asList()) // pluginsField.get(pluginManager) as ArrayList<Plugin>
        val lookupNames = lookupNamesField.get(pluginManager) as HashMap<String, Plugin>
        val commandMap = commandMapField.get(pluginManager) as SimpleCommandMap

        JS.pluginManager.plugins.map {
            val jsInstance = it.key
            val plugin = JavaSkriptPlugin(it.key, it.value)

            plugins.add(plugin)
            lookupNames.put(plugin.name, plugin)

            for ((command/*, jsInstance*/) in JS.pluginManager.registeredCommands.filter { it.value == jsInstance }) // This thing loops through the registered commands and registers it to the command map using the plugin instance
                commandMap.register((it.value["name"] as String).toLowerCase(), command)
        }

        pluginsField.set(pluginManager, plugins)
        lookupNamesField.set(pluginManager, lookupNames)
        commandMapField.set(pluginManager, commandMap)
    }
}