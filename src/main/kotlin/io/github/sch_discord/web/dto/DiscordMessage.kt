package io.github.sch_discord.web.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DiscordMessage(
	val content: String
) {
	@JsonProperty("username")
	val name = "SCHCode"

	@JsonProperty("avatar_url")
	val avatarUrl = "https://raw.githubusercontent.com/SCH-Discord/image/main/profile.png"
}
