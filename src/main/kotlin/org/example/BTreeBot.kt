package org.example

import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color

class BTreeBot: Bot(BotInfo.fromFile("BTreeBot.json")) {

    override fun run() {
        this.bodyColor = Color.RED
        println("Hello World!")
        while (true) {
            turnRight(45.0)
            turnGunLeft(360.0)
        }
    }


}

fun main(args: Array<String>) {
    BTreeBot().start()
}