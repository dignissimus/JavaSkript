pluginManager.register({
    name: "Friendly-Mode",
    description: "A simple way to toggle PvP for players",
    version: 1.0,
    commands: { // actually, this value is not used. TODO use it
        friendly: {
            usage: "/test"
        }
    },
    authors: ["spammy23"]
}, plugin)

states = {}

function orDefault(value, fallback) {
    return value == undefined ? fallback : value
}
with(bukkit) {
    pluginManager.registerCommand(plugin, "friendly", ["f"],
        "Friendly mode main command", "/friendly toggle, /friendly [on/off] <player>, /friendly reload",
        function (sender, command, args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to execute this command")
                return true
            }

            switch (args[0].toLowerCase()) {
            case "toggle":
                if (sender.hasPermission("friendly.toggle")) {
                    player.sendMessage("Friendly Mode: " + value)
                    isEnabled = state[sender.getName()].toLowerCase()
                }
                break
            case "on":
                if (player.hasPermission("friendly.on")) {
                    states[args[1]] = true
                }
                break
            case "off":
                if (player.hasPermission("friendly.off")) {
                    states[args[1]] = false
                }
                break
            default:
                sender.sendMessage("Please enter a valid argument")
                return false
            }
            return true
        }
    )
    pluginManager.registerEvent(function (event) {
        event.setKeepInventory(orDefault(states[event.getPlayer().getName()], false))
    }, PlayerDeathEvent)
    pluginManager.registerEvent(function (event) {
        event.setKeepInventory(orDefault(states[event.get]))
        damage = event.getDamage()
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            return
        }
        if (orDefault(states[event.getEntity().getName()], false)) {
            event.getDamager().sendMessage(ChatColor.RED + attacked.getName() + ChatColor.WHITE + " Has Friendly Mode Turned ON")
            event.setCancelled(true)
        }
        if (orDefault(states[event.getDamager().getName()], false)) {
            event.getDamager().sendMessage("You Have Friendly Mode ON: You cannot Attack Others")
            event.setCancelled(true)
        }
    }, EntityDamageByEntityEvent)
}
