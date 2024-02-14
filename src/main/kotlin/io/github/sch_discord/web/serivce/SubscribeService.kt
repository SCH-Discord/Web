package io.github.sch_discord.web.serivce

import io.github.sch_discord.web.dto.SubscribeRequest
import io.github.sch_discord.web.dto.SubscribeResult
import io.github.sch_discord.web.repository.SubscribersRepo
import io.netty.channel.ChannelOption
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
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

	fun subscribe(data: SubscribeRequest): Mono<SubscribeResult> {
		if(!urlPattern.matcher(data.url).matches()) {
			return Mono.just(SubscribeResult(FAILED, "디스코드 웹후크 URL이 아닙니다."))
		}

		val httpClient: HttpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
			.responseTimeout(Duration.ofMillis(1000))

		val client: WebClient = WebClient.builder()
			.clientConnector(ReactorClientHttpConnector(httpClient))
			.build()

		val result = client.get()
			.uri(data.url)
			.retrieve()
			.toBodilessEntity()
			.publishOn(Schedulers.boundedElastic())
			.map {
				if (!it.statusCode.is2xxSuccessful) {
					return@map SubscribeResult(FAILED, "존재하지 않는 웹후크 URL입니다.")
				}
				val id = repo.getIdByUrl(data.url)
				repo.save(data.toEntity(id))
				if (id != null) {
					return@map SubscribeResult(SUCCESS, "변경되었습니다.")
				}
				return@map SubscribeResult(SUCCESS, "구독되었습니다.")
			}.onErrorReturn(SubscribeResult(FAILED, "존재하지 않는 웹후크 URL입니다."))
		return result
	}
}