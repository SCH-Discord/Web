package io.github.sch_discord.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchDiscordApplication

fun main(args: Array<String>) {
	runApplication<SchDiscordApplication>(*args)
}
