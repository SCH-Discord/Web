package io.github.sch_discord.web.repository

import io.github.sch_discord.web.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SubscribersRepo : JpaRepository<Subscriber, Long> {

	@Query("SELECT s.id FROM Subscriber s WHERE s.url = :url")
	fun getIdByUrl(url: String) : Long?
}