package io.github.sch_discord.web.serivce

import io.github.sch_discord.web.dto.DiscordMessage
import io.github.sch_discord.web.dto.SubscribeRequest
import io.github.sch_discord.web.dto.SubscribeResult
import io.github.sch_discord.web.repository.SubscribersRepo
import io.netty.channel.ChannelOption
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.regex.Pattern

@Service
class SubscribeService(private val repo: SubscribersRepo) {

	companion object {
		private val urlPattern: Pattern = Pattern.compile("^https:\\/\\/*(discord|discordapp).com\\/api\\/webhooks\\/([\\d]+)\\/([a-zA-Z0-9_.-]*)\$")

		const val SUCCESS = 100
		const val FAILED = -1
	}

	suspend fun subscribe(data: SubscribeRequest): SubscribeResult {
		if(!urlPattern.matcher(data.url).matches()) {
			return SubscribeResult(FAILED, "디스코드 웹후크 URL이 아닙니다.")
		}

		val httpClient: HttpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
			.responseTimeout(Duration.ofMillis(1000))

		val client: WebClient = WebClient.builder()
			.clientConnector(ReactorClientHttpConnector(httpClient))
			.build()

		val resp: ResponseEntity<Void>
		try {
			resp = client.post()
				.uri(data.url)
				.bodyValue(DiscordMessage("### 구독되었습니다.\n**구독목록**\n${data.toTextList()}"))
				.retrieve()
				.toBodilessEntity()
				.awaitSingle()
		} catch (e: Exception) {
			e.printStackTrace()
			return SubscribeResult(FAILED, "ERROR!")
		}

		if (!resp.statusCode.is2xxSuccessful) {
			return SubscribeResult(FAILED, "존재하지 않는 웹후크 URL입니다.")
		}
		val id = repo.getIdByUrl(data.url)
		repo.save(data.toEntity(id)).awaitSingle()
		if (id != null) {
			return SubscribeResult(SUCCESS, "변경되었습니다.")
		}
		return SubscribeResult(SUCCESS, "구독되었습니다.")
	}
}