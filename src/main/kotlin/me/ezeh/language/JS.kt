package me.ezeh.language

import jdk.nashorn.api.scripting.ScriptObjectMirror

class JS {
    companion object {
        val registered = ArrayList<JS>()
        @JvmStatic
        var pluginManager: PluginManager = PluginManager()
        private var last = 0
    }




    fun debug(obj: ScriptObjectMirror) {
        println(obj.keys)
    }

    fun withID(id: Int): JS {
        this.id = id
        return this
    }

    fun withID() = withID(last++)
    var id: Int = 0
}

