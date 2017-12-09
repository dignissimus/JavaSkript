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

pluginManager.debug(this);

with(bukkit){
    pluginManager.registerCommand(plugin, "test", "An example command", "/test",
    function(sender, command, args){ //optional alias argument
        sender.sendMessage("I have received your command: " + command.getName());
        return true
    }); //optional alias array
    Bukkit.broadcastMessage("This should work"); // it did work!
    pluginManager.registerEvent(
        function(event){
            print("command: "+event.getCommand());
            print("Test successful , received command");
        }, ServerCommandEvent);
}



