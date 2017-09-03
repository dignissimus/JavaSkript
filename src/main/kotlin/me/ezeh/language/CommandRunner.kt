package me.ezeh.language

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

@FunctionalInterface
interface CommandRunner {
    fun onCommand(sender: CommandSender, command: Command, args: Array<String>, alias: String): Boolean
}