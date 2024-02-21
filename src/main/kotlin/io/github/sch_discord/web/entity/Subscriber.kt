package io.github.sch_discord.web.entity

import jakarta.persistence.*

@Entity
class Subscriber(
	@Column(unique = true, nullable = false)
	var url: String,

	@Column(nullable = false)
	var main: Boolean,

	@Column(nullable = false)
	var library: Boolean,

	@Column(nullable = false)
	var instagram: Boolean,

	@Column(nullable = false)
	var student: Boolean,

	@Column(nullable = false)
	var sw: Boolean,

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null
)
