package edu.ubb.micro.nowaste.authmanager.service.user

import edu.ubb.micro.nowaste.authmanager.model.User
import edu.ubb.micro.nowaste.authmanager.service.ServiceException
import edu.ubb.micro.nowaste.authmanager.repository.UserRepository
import org.springframework.dao.DataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

	override fun getUserById(userId: String): User {

		try {
			return userRepository.findByIdOrNull(userId) ?: throw ResourceNotFoundException("Unable to find user")
		} catch (ex: DataAccessException) {
			throw ServiceException("Can't query user with id $userId", ex)
		}
	}
}