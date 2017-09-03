package me.ezeh.language

import jdk.internal.dynalink.beans.StaticClass
import jdk.nashorn.api.scripting.ScriptObjectMirror
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
class PluginManager : Listener {
    val plugins = HashMap<JS, ScriptObjectMirror>() // create details class
    val registeredEvents = HashMap<EventListener, Class<Event>>()
    val registeredCommands = HashMap<Command, JS>()
    fun debug(obj: ScriptObjectMirror) {
        println(obj.keys)
    }

    fun register(details: ScriptObjectMirror, plugin: JS) {
        print(details["name"] as String + ": ")
        println(details.keys.zip(details.values))
        plugins.put(plugin, details)

    }

    fun registerEvent(handler: EventListener, event: StaticClass) {
        if (Event::class.java.isAssignableFrom(event.representedClass)) {
            registeredEvents.put(handler, event.representedClass as Class<Event>)
            println("registered event! Named ${event.representedClass.name}")
        } else {
            println("could not register event named ${event.representedClass.name}")
        }
    }

    @EventHandler
    fun onEvent(event: Event) {
        for ((key, value) in registeredEvents)
            if (value.isAssignableFrom(event::class.java))
                key.handle(event)

    }

    fun registerCommand(plugin: JS, name: String, aliases: Array<String>, description: String, usage: String, runner: CommandRunner) {
        registeredCommands.put(object : Command(name, description, usage, ArrayList(aliases.asList())) {
            override fun execute(sender: CommandSender, alias: String, args: Array<String>): Boolean {
                return runner.onCommand(sender, this, args, alias)
            }

        }, plugin)
    }
    fun registerCommand(plugin: JS, name: String, description: String, usage: String, runner: CommandRunner){ // I'm not using Kotlin default params due to nashorn not liking them therefore this method must exist
        registerCommand(plugin, name, emptyArray<String>(), description, usage, runner)
    }

    fun registerCommand(plugin: JS, name: String, runner: CommandRunner) {
        registerCommand(plugin, name, "", "/$name", runner)
    }
}
