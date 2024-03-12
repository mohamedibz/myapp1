package data.newquotation.model

import android.util.Log
import domain.model.Quotation
import retrofit2.Response
import java.io.IOException

fun RemoteQuotationDto.toDomain() = Quotation(id = senderLink, text = quoteText, author= quoteAuthor)

fun Response<RemoteQuotationDto>.toDomain() =
    if (isSuccessful) Result.success((body() as RemoteQuotationDto).toDomain())
    else {
        Result.failure(IOException())
    }
