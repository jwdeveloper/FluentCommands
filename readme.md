<div align="center" >
<a target="blank" >
<img src="https://github.com/user-attachments/assets/95823146-0171-4619-8eb0-d862f801a5ad" width="15%" >
</a>
</div>
<div align="center" >
<h1>Commands Framework</h1>


👾 *No more difficult commands* ️👾

<div align="center" >
<a href="https://central.sonatype.com/artifact/io.github.jwdeveloper.spigot.commands/core" target="blank" >
<img src="https://img.shields.io/maven-central/v/io.github.jwdeveloper.spigot.commands/core" width="20%" >
</a>

<a href="https://discord.gg/e2XwPNTBBr" target="blank" >
<img src="https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white" >
</a>

<a target="blank" >
<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" >
</a>
</div>
</div>

# Introduction
A Spigot/Paper library dedicated for simplifying commands registration for your plugin!

See the [documentation](https://jwdeveloper.github.io/FluentCommands/) to see more advanced examples! 

Join the support [discord](https://discord.gg/2hu6fPPeF7) and visit the `#programming` channel for questions, contributions and ideas. Feel free to make pull requests with missing/new features, fixes, etc

<div align="center" >
   <img align="center" src="https://github.com/user-attachments/assets/1248cd52-4d26-4a38-a764-df005a5d15bd"  >
</div>



## Getting started
1. Install the dependencie 
```xml
<dependency>
    <groupId>io.github.jwdeveloper.spigot.commands</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```
<br>
</br>

2. Create your first command
```java

public final class Example extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandsApi commandsApi = CommandsFramework.enable(this);

        commandsApi.create("hello-world")
                //Properties
                .withPermissions("use.hello.world")
                .withDescription("This command say hello world to player")

                //Arguments
                .addTextArgument("animal")
                .addNumberArgument("number-of-people")
                .addPlayerArgument("main-player")

                //Events
                .onPlayerExecute((command, event) ->
                {
                    var sender = event.sender();
                    var player = event.argumentPlayer(0);
                    var size = event.argumentDouble(1);
                })
                .onServerExecute((command, event) ->
                {
                    event.sender().sendMessage("This command can be only use by players!");
                })
                .register();
    }
}
```

3. Call the command in game!

`/hello-world john 12 Steave`


## Contributing

[Library documentation for contributors](https://github.com/jwdeveloper/FluentCommands)

Your improvements are welcome! Feel free to open an <a href="https://github.com/jwdeveloper/FluentCommands/issues">issue</a> or <a href="https://github.com/jwdeveloper/FluentCommands/pulls">pull request</a>.
