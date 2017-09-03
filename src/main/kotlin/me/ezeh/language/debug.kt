package me.ezeh.language

fun main(args: Array<String>) {
    JavaSkriptExecutor().parse("""
pluginManager.register({
    name: "Test plugin",
    description: "Example plugin",
    version: 1.0,
    commands: {
        test: {
            usage: "/test"
        }
    },
    authors: ["spammy23"]
}, plugin);
pluginManager.debug(this)
with(bukkit){
    Bukkit.broadcastMessage("This should work") // it did work!
    pluginManager.registerEvent(
        function(event){
            print("command: "+event.getCommand());
            print("Test successful , received command");
        }, ServerCommandEvent);
}



""".trimMargin())
}
