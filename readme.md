<div align="center" >
<a target="blank" >
<img src="https://github.com/user-attachments/assets/7c8b9bdf-5e56-47ec-8180-bd57230ff5ce2" width="15%" >
</a>
</div>
<div align="center" >
<h1>Instagram Live</h1>


❤️❤️🎁 *Connect to Instagram live in 3 lines* 🎁❤️❤️

<div align="center" >
<a href="https://www.nuget.org/packages/InstaLiveDotNet/" target="blank" >
<img src="https://img.shields.io/nuget/v/SoftCircuits.Silk.svg?style=flat-square" width="120px"  height="28px" >
</a>


<a href="https://discord.gg/e2XwPNTBBr" target="blank" >
<img src="https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white" >
</a>

<a target="blank" >
<img src="https://img.shields.io/badge/dotnet-7.0-blue" height="28px" >
</a>
</div>
</div>

# Introduction
A C# library dedicated for connecting to Instagram live.


Join the support [discord](https://discord.gg/e2XwPNTBBr) and visit the `#instagram-support` channel for questions, contributions and ideas. Feel free to make pull requests with missing/new features, fixes, etc

**NOTE:** This is not an official API. It's a reverse engineering project.

## Getting started
1. Install the package <a href="https://www.nuget.org/packages/InstaLiveDotNet/" target="blank" ><img src="https://img.shields.io/nuget/v/SoftCircuits.Silk.svg?style=flat-square" width="100px"  height="25px"></a>
```java
<PackageReference Include="InstaLiveDotNet" Version="1.0.0" />
```
<br>
</br>

2. Create your first chat connection
```C#

var client = InstagramLive
    .NewClient()
    .Configure(properties =>
    {
        //User credentials to login
        properties.UserName = "username";
        properties.Password = "********";

        //Or sessionId + deviceId
        properties.SessionId = "session id";
        properties.DeviceId = "device id    ";
    })
    .OnError((live, @event) =>
    {
        Console.WriteLine($"we have error { @event.Exception.Message}");
    })
    .OnConnected((live, @event) =>
    {
        Console.WriteLine("Connected to live");
    })
    .OnDisconnected((liveClient, data) =>
    {
        Console.WriteLine("OnDisconnected to live");
    })
    .OnJoin((liveClient, data) =>
    {
        Console.WriteLine("User joined to live "+data.ToJson());
    })
    .OnComment((live, @event) =>
    {
        Console.WriteLine("Comment "+@event.ToJson());
    })
    .OnSystemComment((liveClient,  @event) =>
    {
        Console.WriteLine("System Comment "+@event.ToJson());
    })
    .Build();




await client.Connect("jacolwol");
```

## Contributing

[Library documentation for contributors](https://github.com/jwdeveloper/TikTokLiveJava/wiki)

Your improvements are welcome! Feel free to open an <a href="https://github.com/jwdeveloper/TikTok-Live-Java/issues">issue</a> or <a href="https://github.com/jwdeveloper/TikTok-Live-Java/pulls">pull request</a>.
