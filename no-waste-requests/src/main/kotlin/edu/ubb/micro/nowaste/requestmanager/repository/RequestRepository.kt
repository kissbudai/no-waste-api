package edu.ubb.micro.nowaste.requestmanager.repository

import edu.ubb.micro.nowaste.requestmanager.model.Request
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository for [Request].
 */
@Repository
interface RequestRepository : JpaRepository<Request, String> {

	/**
	 * Retrieves every element from source where the requester is defined by [requesterId].
	 * for retrieving elements without pagination.
	 *
	 * @param requesterId The requester who wants to get his [Request]s.
	 * @param pageable Page information to determine the requested page.
	 *
	 * @return List of [Request]s.
	 */
	fun findAllByRequesterId(requesterId: String, pageable: Pageable): Page<Request>
}