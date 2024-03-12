package ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor() : ViewModel() {

    private val _list : MutableStateFlow<List<Quotation>> = getFavouriteQuotations()
    val list get() = _list.asStateFlow()

    val isDeleteAllMenuVisible = list.map { list ->
        list.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = true
    )

    private fun getFavouriteQuotations(): MutableStateFlow<List<Quotation>> {
        val list = mutableListOf<Quotation>()
        repeat(18){
            val num = (0..99).random()
            list.add(
                Quotation(
                    id = "$num",
                    text = "Quotation text #$num",
                    author = "Author #$num"
                )
            )
        }

        list.add(Quotation(id = "123", text = "fsdvfdsovv", author = "Anonymous"))
        list.add(Quotation(id = "1234", text = "fsdvfdsovv", author = "Albert Einstein"))

        return MutableStateFlow(list)
    }

    public fun deleteAllQuotations(){
        _list.update { emptyList() }
    }

    public fun deleteQuotationAtPosition(position: Int) {
        if (position in 0 until _list.value.size) {
            // Crear una copia de la lista y eliminar la cita en la posición indicada
            val tempList = _list.value.toMutableList()
            val element = tempList.get(position)
            _list.update {
                tempList.minus(element)
            }
        }
    }
}