package io.github.sch_discord.web.repository

import io.github.sch_discord.web.entity.Subscriber

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscribersRepo : R2dbcRepository<Subscriber, Long> {

	@Query("SELECT s.id FROM subscriber s WHERE s.url = :url")
	suspend fun getIdByUrl(url: String) : Long?
}