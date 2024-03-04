package io.github.sch_discord.web.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("subscriber")
class Subscriber(
	@Column
	var url: String,

	@Column
	var main: Boolean,

	@Column
	var library: Boolean,

	@Column
	var student: Boolean,

	@Column
	var sw: Boolean,

	@Id
	var id: Long? = null
)
