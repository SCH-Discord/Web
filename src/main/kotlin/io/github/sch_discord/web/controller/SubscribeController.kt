package io.github.sch_discord.web.controller

import io.github.sch_discord.web.dto.SubscribeRequest
import io.github.sch_discord.web.dto.SubscribeResult
import io.github.sch_discord.web.serivce.SubscribeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class SubscribeController(private val service: SubscribeService) {

	@PostMapping("/subscribe")
	fun registerWebhook(@RequestBody data: SubscribeRequest): Mono<ResponseEntity<SubscribeResult>> {
		val result: Mono<SubscribeResult> = service.subscribe(data)
		return result.map {
			ResponseEntity.ok(it)
		}.onErrorResume {
			Mono.just(ResponseEntity.status(500).body(SubscribeResult(SubscribeService.FAILED, it.message ?: "error")))
		}
	}
}