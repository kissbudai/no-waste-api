package edu.ubb.micro.nowaste.requestmanager.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ProductInfo(
	@Id
	val productId: String,
	val status: Status
) {

	enum class Status(val value: Int) {
		OPEN(0),
		PENDING(1),
		COMPLETED(2),
		UNKNOWN(-1)
	}
}