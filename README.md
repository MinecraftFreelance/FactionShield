![Author banner](https://i.imgur.com/2Pqnm9N.png)

[![AUTHOR - BillyDotWS](https://img.shields.io/static/v1?label=AUTHOR&message=BillyDotWS&color=2ea44f&style=for-the-badge&logo=discord+)](https://billy.ws) [![LinkedIn][linkedin-shield]][linkedin-url] [![Version](https://img.shields.io/github/v/release/MinecraftFreelance/FactionShield?label=VERSION&style=for-the-badge)](https://img.shields.io/github/v/release/MinecraftFreelance/ParkourPlugin?label=VERSION&style=for-the-badge)

---
<br>
<h3 align="center" style="font-size: 2vw;
  text-transform: uppercase;
  text-align: center;
  line-height: 1;
  ">
  FactionShield
</h3>
<p align="center">Native spigot version: 1.18.2</p>
<p align="center">Dependency: FactionsUUID & Space (not included)</p>
<p  align="center">If a faction shield block is placed in a faction claim, enemies have 20 seconds (this time is configurable) to get out of the land</p>
<br>
<br>

[![Preview](https://img.youtube.com/vi/miJ5Wsp0Rqc/0.jpg)](https://www.youtube.com/watch?v=miJ5Wsp0Rqc)

## ⚠️ Notice
This plugin was made public due to a chargeback filed against it, you are welcome to modify it and use it as you wish.

## 🗒️ Road Map
- [x] Replace the neutron generator belt with a custom machine
- [x] When someone enters faction land, if the faction has a shield machine, give them x seconds to leave.
- [x] Make the messages customisable (including the title screen, death message etc)

## ⚙️ Configuration Options

This plugin is configurable based on the client requirements:
`config.yaml` is generated automatically and has the following configurable options:
```
escape_time: 20 # the amount of time players are given to escape
```

`messages.yaml` is generated automatically and has the following configurable options:
```
placed_shield: '&eYou have placed a shield! &cEnemy factions will be punished!' # message sent when a shield is placed
not_in_faction: '&cYou are not in a faction, join one to be able to place this!' # player is not in a faction
not_your_land: '&cYou cannot place this outside of your land!' # not the faction of the player's land
warning_chat_message: '&c{{faction}} has a shield enabled! &eYou will die in {{time}} if you don''t escape!' # warning message
death_message: '&cWell, you were warned, don''t come back!' # message once the person dies
delete_message: '&7Your faction shield at &e{{location}} &7has been destroyed!' # a message sent to all faction members when a shield is deleted
title: 
  title: '&c&l{{remaining_time}}' # the title (big message) on a player's screen
  subtitle: '&7Exit this land, or die!' # the subtitle (little message) on a player's screen
```

## 🛠️ Tools Used

Multiple tools have been used during development to speed up development and keep things neat!

- [CheckStyle](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea) has been used to create well formatted, consistent code (and enforce final usage)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/old/) has been used to code the plugin, as it offers great plugin support!

## 👣 Versioning

Whilst developing this plugin, I have used [Semantic versioning](http://semver.org/) for the current versions of the plugin, see the [list of created versions so far](https://github.com/MinecraftFreelance/FactionShield/tags)!. I have created an automatic versioning system that automatically
increments the version (see `.github/workflows/release.yaml`), this automatically increases the version every time a commit is made to the main branch of this repository.

## 🏗️ Building the plugin
First of all - input the dependency jars into a folder called `/libs` (`Space.jar` and `Factions.jar` - as these are premium plugins, they are not included within this repository).

This plugin is automatically versioned using a GitHub action (as mentioned above), in order to build the plugin. You will also need to create the following environment variables, this is automatically assigned when forking the repository:
- `RELEASE_VERSION`: the release version (e.g. 1.0.0)
- `GITHUB_REF_NAME`: the branch that is being built (e.g. stage)
- `GITHUB_RUN_NUMBER`: the current build number (e.g. 1)
- `BUILD_PATH`: the location on your machine to put the jar


This Spigot plugin is built using maven, using the provided `pom.xml` file.

```
mvn build
```

This will create a jar file in the defined directory of the repository (discussed above), this can be used to put into the `/plugins` folder.

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/billy-robinson-a6486714a/
