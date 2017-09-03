package me.ezeh.language

import javax.script.Invocable
import javax.script.ScriptEngineManager
import org.bukkit.*;

class JavaSkriptExecutor {
    val engine = ScriptEngineManager().getEngineByName("nashorn") ?: throw Exception("Unable to get the required JS engine")
    val invocable = engine as Invocable

    init {
        javaClass.classLoader.loadClass("org.bukkit.Bukkit")
        Package.getPackage("org.bukkit") // load the package first
        val packages = Package.getPackages()
                .filter { it.name.startsWith("org.bukkit") }
                .map { it.name }
                .joinToString(", ")
        engine.eval(
                """
var javaskript = Java.type("me.ezeh.language.JS");
var plugin = new javaskript();
var pluginManager = javaskript.getPluginManager();
var bukkit = new JavaImporter($packages);
"""
        )
    }

    fun parse(code: String) {
        engine.eval(code)
    }
}