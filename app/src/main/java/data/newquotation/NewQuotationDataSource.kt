package data.newquotation

import data.newquotation.model.RemoteQuotationDto
import domain.model.Quotation
import retrofit2.Response

interface NewQuotationDataSource {
    suspend fun getQuotation(lan : String): Response<RemoteQuotationDto>
}