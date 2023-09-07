# Example Robocode Tank Royale Bot
This repository has an example bot written in Kotlin which is compatible with Java and the JVM, just with a nicer programming language.
It also has everything necessary to run as a host for a Robocode Tank Royale competition. So other bots can be packaged and dropped into the `bots` folder and it'll be picked up.

Ensure that you are familiar with the rules and anatomy of a tank. They're important to understanding the limitations of your tank.
https://robocode-dev.github.io/tank-royale/articles/anatomy.html

## Default competition rules
- Game type: Classic
- Arena size: 800x600
- Gun cooling rate: 0.1
- Number of rounds: 10
- Ready timeout (μs): 1,000,000
- Turn timeout (μs): 30,000

## Running as host
Unzip the included compiled sample bots
```bash
unzip sample-bots-java-0.19.2.zip
```

Run the host application
```bash
java -jar robocode-tankroyale-gui-0.19.2.jar
```

1. Go to `Config->Bot root directories` and add the folder you just unzipped
2. Start the server
3. Start game and set the mode to "classic", load all the bots you want to run and click start


## Building a bot and submitting to host
- Ensure that you set compatibility mode to JDK 11. You'll need to produce a jar file in the final deliverable
- Ensure that your bot includes a .json file with the same name as the jar. Example [here](./src/main/kotlin/org/example/BTreeBot.json). 
- Ensure that you have a .sh file with the command that would execute the jar file. See example [here](./BTreeBot/BTreeBot.sh)

Full instructions for the JVM is available in the official docs: https://robocode-dev.github.io/tank-royale/tutorial/jvm/my-first-bot-for-jvm.html

For using this repo as a template, add logic to `BTreeBot.kt` and compile. You should get a final folder in BTreeBot. Send that BTreeBot folder to the host.


## Requirements and references
- JDK 18 for this repo. The bot should be compiled down to JDK 11 for the final jar.
- https://robocode-dev.github.io/tank-royale/articles/anatomy.html
- https://github.com/robocode-dev/tank-royale