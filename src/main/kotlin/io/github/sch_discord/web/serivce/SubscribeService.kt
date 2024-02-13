package io.github.sch_discord.web.serivce

import io.github.sch_discord.web.dto.SubscribeRequest
import io.github.sch_discord.web.dto.SubscribeResult
import io.github.sch_discord.web.repository.SubscribersRepo
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class SubscribeService(private val repo: SubscribersRepo) {

	companion object {
		private val urlPattern: Pattern = Pattern.compile("^https:\\/\\/*(discord|discordapp).com\\/api\\/webhooks\\/([\\d]+)\\/([a-zA-Z0-9_.-]*)\$")

		private const val SUCCESS = 100
		private const val FAILED = -1
	}

	fun subscribe(data: SubscribeRequest) : SubscribeResult {
		if(!urlPattern.matcher(data.url).matches()) {
			return SubscribeResult(FAILED, "디스코드 웹후크 URL이 아닙니다.")
		}
		val id = repo.getIdByUrl(data.url)
		repo.save(data.toEntity(id))
		if(id != null) {
			return SubscribeResult(SUCCESS, "변경되었습니다.")
		}
		return SubscribeResult(SUCCESS, "구독되었습니다.")
	}
}