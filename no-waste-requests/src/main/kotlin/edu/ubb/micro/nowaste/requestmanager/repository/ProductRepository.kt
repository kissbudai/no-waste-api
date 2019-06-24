package edu.ubb.micro.nowaste.requestmanager.repository

import edu.ubb.micro.nowaste.requestmanager.model.ProductInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductInfo, String>