# JavaSkript

## Introduction

> JavaSkript provides a simple way for you to access the Bukkit api through JavaScript

## Usage

First you must register your plugin, the `pluginManager.register` method works like a plugin.yml, here your plugin's name, commands, description, and many other variables declared in the plugin.yml of a regular plugin. the `plugin` variable is an instance of your JS plugin.
```javascript
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
```
All code including the bukkit API is in the single variable `bukkit`, it's usually easier to wrap your code with `with(bukkit)`.
To register a command, you use the `pluginManager.registerCommand` method which takes 5/6 arguments.  The first being your `plugin` instance, the second being the name of the command, the third being an optional list of aliases, the fourth being description, fifth being the usage, and the sixth being the function that executes the command.
`pluginManager.registerCommand(plugin, name, description, usage, commandFunction)`
```javascript
with(bukkit) {
    pluginManager.registerCommand(plugin, "test", ["alias1", "alias2"], "An example command", "/test",
        function(sender, command, args){ //optional alias argument
            sender.sendMessage("I have received your command: " + command.getName())
            return true
    }) // The array of aliases is optional
}
```
While registering events, you use the`pluginManager.registerEvent` method, this takes two variables, the first being the handler and the second being the class for the event you are listeneing to 
```javascript
with(bukkit) {
    pluginManager.registerEvent(
        function(event){
            print("command: "+event.getCommand());
            print("Test successful , received command");
        }, ServerCommandEvent);
}
```

## Installation

> To install, place the JavaSkript.jar archive in your plugins folder and insert your JS plugins in the `plugins/JavaSkript` directory created on the first run
