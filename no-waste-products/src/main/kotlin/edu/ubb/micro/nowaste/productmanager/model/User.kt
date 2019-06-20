package edu.ubb.micro.nowaste.productmanager.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
	@Id
	val id: String,
	val firstName: String,
	val lastName: String
)