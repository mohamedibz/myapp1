package data.newquotation.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteQuotationDto(
    val quoteText : String,
    val quoteAuthor : String,
    val senderName : String,
    val senderLink : String
)
