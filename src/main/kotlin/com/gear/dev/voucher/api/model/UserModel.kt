package com.gear.dev.voucher.api.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "users")
data class UserModel(
  @Id
  @GeneratedValue(strategy = IDENTITY)
  val id: Long,
  val name: String,
  val email: String,
  val password: String,
)
