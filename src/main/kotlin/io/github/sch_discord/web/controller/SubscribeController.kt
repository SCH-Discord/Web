package io.github.sch_discord.web.controller

import io.github.sch_discord.web.dto.SubscribeRequest
import io.github.sch_discord.web.dto.SubscribeResult
import io.github.sch_discord.web.serivce.SubscribeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SubscribeController(private val service: SubscribeService) {

	@PostMapping("/subscribe")
	fun registerWebhook(@RequestBody data: SubscribeRequest) : SubscribeResult {
		return service.subscribe(data)
	}
}