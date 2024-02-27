package io.github.sch_discord.web.dto

import io.github.sch_discord.web.entity.Subscriber

data class SubscribeRequest(
	val url: String,
	val main: Boolean,
	val library: Boolean,
	val student: Boolean,
	val sw: Boolean
) {

	fun toEntity(id: Long?) : Subscriber {
		return Subscriber(
			id = id,
			url = url,
			main = main,
			library = library,
			student = student,
			sw = sw
		)
	}

	fun toTextList() : String {
		var text = ""
		if (main) text += "- 대학포털\n"
		if (library) text += "- 도서관\n"
		if (student) text += "- 학생지원팀\n"
		if (sw) text += "- SW중심대학산업단\n"
		return text
	}
}
