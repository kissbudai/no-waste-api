package edu.ubb.micro.nowaste.productmanager.repository

import edu.ubb.micro.nowaste.productmanager.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository for [Category] model.
 */
@Repository
interface CategoryRepository : JpaRepository<Category, String>