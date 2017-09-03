package me.ezeh.language

import org.bukkit.event.Event

@FunctionalInterface
interface EventListener {
    fun handle(event: Event)
}