package io.github.sch_discord.web.controller

import io.github.sch_discord.web.dto.SubscribeRequest
import io.github.sch_discord.web.dto.SubscribeResult
import io.github.sch_discord.web.serivce.SubscribeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SubscribeController(private val service: SubscribeService) {

	@PostMapping("/subscribe")
	suspend fun registerWebhook(@RequestBody data: SubscribeRequest): ResponseEntity<SubscribeResult> {
		val result: SubscribeResult = service.subscribe(data)
		return ResponseEntity.ok(result)
	}
}