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
<p align="center">For Minecraft version(s): x - x</p>
<p  align="center">A brief description of FactionShield</p>
<br>
<br>

## 🙏 Client Information

This plugin was created as part of a commission. Please refer to the agreed upon terms and conditions for information regarding license agreements, modification ability, upkeep responsibilities etc. This can typically be found on the platform initially contacted on (for example: discord, fiverr).

#### Client contact information:

- Server name: `Server`
- Server IP: `play.server.com`
- Discord - `Name#1234`
- Minecraft - `Name`

## 🗒️ Given Requirements

- [ ] Feature 1
- [ ] Feature 1
- [ ] Feature 1
- [ ] Feature 1
- [ ] Feature 1

## 🤖 Commands

This plugin implements the `Command` custom abstract class - which is used to simply register commands using a predefined formulae. This makes for quicker development and easier maintenance of the plugin, reducing the checks and code that each class needs to have. Below is a list of commands that
this plugin implements.

| Name | Usage | Description | Cooldown | Permission |
|------|-------|-------------|----------|------------|
|/template|/template|Prints out "template plugin is epic" to the player, to show that the plugin is working as intented and the events expected are registered.|5 seconds|FactionShield.command.template|
|      |       |             |          |            |

## ⚙️ Configuration Options

This plugin is configurable based on the client requirements:
`config.yaml` is generated automatically and has the following configurable options:

- `configurable` - does x, y, z

## 🛠️ Tools Used

Multiple tools have been used during development to speed up development and keep things neat!

- [CheckStyle](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea) has been used to create well formatted, consistent code (and enforce final usage)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/old/) has been used to code the plugin, as it offers great plugin support!

## 👣 Versioning

Whilst developing this plugin, I have used [Semantic versioning](http://semver.org/) for the current versions of the plugin, see the [list of created versions so far](https://github.com/MinecraftFreelance/FactionShield/tags)!. I have created an automatic versioning system that automatically
increments the version (see `.github/workflows/release.yaml`), this automatically increases the version every time a commit is made to the main branch of this repository.

## 🏗️ Building the plugin

This plugin is automatically versioned using a GitHub action (as mentioned above), in order to build the plugin. Create an environment variable called "RELEASE_VERSION" on your machine and give it a value (you can do this using `$RELEASE_VERSION=value` on a Linux/Unix machine.

This Spigot plugin is built using maven, using the provided `pom.xml` file.

```
mvn build
```

This will create a jar file in the root directory of the repository, this can be used to put into the `/plugins` folder.

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/billy-robinson-a6486714a/
