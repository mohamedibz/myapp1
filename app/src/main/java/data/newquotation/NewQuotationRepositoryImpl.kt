package data.newquotation

import data.newquotation.model.toDomain
import domain.model.Quotation
import kotlinx.coroutines.flow.update
import utils.NoInternetException
import javax.inject.Inject
import kotlin.random.Random

class NewQuotationRepositoryImpl @Inject constructor(
    private val dataSource: NewQuotationDataSource,
    private val checker: ConnectivityChecker ) : NewQuotationRepository {

    override suspend fun getNewQuotation(): Result<Quotation> {
        return if(checker.isConnectionAvailable()){

            dataSource.getQuotation(arrayOf("en", "ru", "xx").random()).toDomain()
        }else{
            Result.failure(NoInternetException("No Internet Connection"))
        }
    }

}