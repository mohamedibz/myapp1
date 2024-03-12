package ui.newquotation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.newquotation.NewQuotationRepository
import domain.model.Quotation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject constructor(private val rep : NewQuotationRepository) : ViewModel(){
    
    private val _username : MutableStateFlow<String> = getUserName()
    val username get() = _username.asStateFlow()

    private val _quotation : MutableStateFlow<Quotation?> = MutableStateFlow(null)
    val quotation get() = _quotation.asStateFlow()

    private val _show : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val show get() = _show.asStateFlow()

    private val _showFav : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showFav get() = _showFav.asStateFlow()

    private val _showErr : MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val showErr get() = _showErr.asStateFlow()

    fun resetError(){
        _showErr.update { null }
    }

    private fun getUserName(): MutableStateFlow<String> {
        val temp = setOf("Alice", "Bob",
            "Charlie", "David", "Emma", "").random()
        return MutableStateFlow(temp)
    }

    fun getNewQuotation(){
        _show.update { true }
        viewModelScope.launch {
            rep.getNewQuotation().fold(onSuccess = { _quotation.value = it }, onFailure = { _showErr.value = it })
        }
        _show.update { false }
        _showFav.update { true }
    }

    fun isEmpty(): Boolean{
        return _quotation.value == null
    }

    fun addToFavourites(){
        _showFav.update { false }
    }

}