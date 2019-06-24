package edu.ubb.micro.nowaste.requestmanager.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "requests")
data class Request(
	val productId: String,
	var status: Status,
	val requesterId: String,
	val createdAt: Long
) {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	var id: String = ""

	enum class Status(val code: Int) {
		PENDING(0),
		COMPLETED(1),
		CANCELLED(2),
		UNKNOWN(-1)
	}
}