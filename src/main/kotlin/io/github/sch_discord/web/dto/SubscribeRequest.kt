package io.github.sch_discord.web.dto

import io.github.sch_discord.web.entity.Subscriber

data class SubscribeRequest(
	val url: String,
	val main: Boolean,
	val library: Boolean,
	val instagram: Boolean,
	val student: Boolean,
	val sanhak: Boolean,
	val sw: Boolean
) {

	fun toEntity(id: Long?) : Subscriber {
		return Subscriber(
			id = id,
			url = url,
			main = main,
			library = library,
			instagram = instagram,
			student = student,
			sanhak = sanhak,
			sw = sw
		)
	}
}
