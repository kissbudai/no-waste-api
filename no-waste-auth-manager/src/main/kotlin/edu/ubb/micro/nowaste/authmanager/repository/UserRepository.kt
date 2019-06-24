package edu.ubb.micro.nowaste.authmanager.repository

import edu.ubb.micro.nowaste.authmanager.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

	fun findUserByUserName(userName: String): User?
}