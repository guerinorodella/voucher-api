package com.gear.dev.voucher.api.repository

import com.gear.dev.voucher.api.model.VoucherModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VoucherRepository : CrudRepository<VoucherModel, Long>
