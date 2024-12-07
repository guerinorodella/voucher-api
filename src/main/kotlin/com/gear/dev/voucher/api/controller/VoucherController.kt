package com.gear.dev.voucher.api.controller

import com.gear.dev.voucher.api.repository.VoucherRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.ZoneOffset

@RestController
@RequestMapping("voucher-api/v1/voucher")
class VoucherController(
  private val voucherRepository: VoucherRepository,
) {

  @GetMapping("/{id}")
  fun getVoucher(
    @RequestHeader httpHeaders: HttpHeaders,
    @PathVariable id: Long,
  ): ResponseEntity<VoucherResponse> {
//    val sessionId = httpHeaders.getFirst("session-id") ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    val voucherResult = voucherRepository.findById(id)
    if (voucherResult.isEmpty)
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    val voucher = voucherResult.get()
    // TODO: validar se o voucher pertence ao usuário da sessão solicitado na request

    return ResponseEntity.ok(
      VoucherResponse(
        voucher.id,
        voucher.description,
        voucher.acquiredDate.toEpochSecond(ZoneOffset.UTC),
        voucher.expiryDate.toEpochSecond(ZoneOffset.UTC),
      )
    )
  }

}

data class VoucherResponse(
  val id: Long,
  val description: String,
  val acquiredDate: Long,
  val expiryDate: Long,
)