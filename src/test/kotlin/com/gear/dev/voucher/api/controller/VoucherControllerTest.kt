package com.gear.dev.voucher.api.controller

import com.gear.dev.voucher.api.model.UserModel
import com.gear.dev.voucher.api.model.VoucherModel
import com.gear.dev.voucher.api.repository.VoucherRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExtendWith(MockKExtension::class)
class VoucherControllerTest {

  @MockK
  lateinit var repository: VoucherRepository

  @InjectMockKs
  lateinit var instance: VoucherController

  @Test
  fun `returns 200 OK - valid voucher id`() {
    val now = LocalDateTime.now()
    val aValidVoucher = VoucherModel(
      100L,
      UserModel(100L, "foo", "foo@mail", "hashedpass"),
      "aValidVoucher",
      now,
      now.plusMonths(3)
    )
    every { repository.findById(any()) } answers { Optional.of(aValidVoucher) }

    val response = instance.getVoucher(HttpHeaders(), 100L)

    assertNotNull(response)
    assertNotNull(response.body)
    assertEquals(HttpStatus.OK.value(), response.statusCode.value())

    val responseBody = response.body as VoucherResponse
    assertEquals(100, responseBody.id)
    assertEquals("aValidVoucher", responseBody.description)
    assertEquals(now.toEpochSecond(ZoneOffset.UTC), responseBody.acquiredDate)
    assertEquals(now.plusMonths(3).toEpochSecond(ZoneOffset.UTC), responseBody.expiryDate)
  }

  @Test
  fun `returns 404 - when resource does not exists`() {
    every { repository.findById(any()) } answers { Optional.ofNullable(null) }
    val response = instance.getVoucher(HttpHeaders(), 100L)
    assertNotNull(response)
    assertNull(response.body)
    assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode.value())
  }
}