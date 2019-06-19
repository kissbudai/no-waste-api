package edu.ubb.micro.nowaste.authmanager.model

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue

@Document
data class User(
	val userName: String,
	var email: String,
	var firstName: String,
	var lastName: String
) {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	lateinit var id: String

	var password: String? = null
}