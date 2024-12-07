package com.gear.dev.voucher.api.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import java.time.LocalDateTime

@Entity
@Table(name = "vouchers")
data class VoucherModel(
  @Id
  @GeneratedValue(strategy = IDENTITY)
  val id: Long,

  @ManyToOne
  val user: UserModel,

  val description: String,

  @Column(name = "acquired_date")
  val acquiredDate: LocalDateTime,

  @Column(name = "expiry_date")
  val expiryDate: LocalDateTime,
)
