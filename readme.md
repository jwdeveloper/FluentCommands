<div align="center" >
<a target="blank" >
<img src="https://github.com/user-attachments/assets/95823146-0171-4619-8eb0-d862f801a5ad" width="15%" >
</a>
</div>
<div align="center" >
<h1>Commands Framework</h1>


👾 *No more difficult commands* ️👾

<div align="center" >
<a href="https://jitpack.io/#jwdeveloper/TikTok-Live-Java" target="blank" >
<img src="https://jitpack.io/v/jwdeveloper/TikTok-Live-Java.svg" width="20%" >
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

## Getting started
1. Install the package <a href="https://www.nuget.org/packages/InstaLiveDotNet/" target="blank" ><img src="https://img.shields.io/nuget/v/SoftCircuits.Silk.svg?style=flat-square" width="100px"  height="25px"></a>
```java
<PackageReference Include="InstaLiveDotNet" Version="1.0.0" />
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
                .buildAndRegister();
    }
}
```

3. Call the command in game!

`/hello-world john 12 Steave`


## Contributing

[Library documentation for contributors](https://github.com/jwdeveloper/FluentCommands)

Your improvements are welcome! Feel free to open an <a href="https://github.com/jwdeveloper/FluentCommands/issues">issue</a> or <a href="https://github.com/jwdeveloper/FluentCommands/pulls">pull request</a>.
