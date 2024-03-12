package di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.newquotation.NewQuotationDataSource
import data.newquotation.NewQuotationDataSourceImpl
import data.newquotation.NewQuotationRepository
import data.newquotation.NewQuotationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NewQuotationBinderModule {

    @Binds
    abstract fun bindNewQuotationRepository(impl: NewQuotationRepositoryImpl): NewQuotationRepository

    @Binds
    abstract fun bindNewQuotationDataSource(impl: NewQuotationDataSourceImpl): NewQuotationDataSource

}

