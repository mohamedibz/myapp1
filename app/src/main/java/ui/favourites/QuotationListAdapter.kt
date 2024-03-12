package ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dadm.mchazna.myapp1.databinding.QuotationItemBinding
import domain.model.Quotation

class QuotationListAdapter(val onItemClick: (String) -> Unit):
            ListAdapter<Quotation, QuotationListAdapter.ViewHolder>(QuotationDiff) {

    class ViewHolder(val onItemClick: (String) -> Unit, private val binding: QuotationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quotation){
            binding.quoteItem.text = quote.text
            binding.authorItem.text = quote.author
        }

        init {
            binding.root.setOnClickListener {
                onItemClick(binding.authorItem.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(onItemClick, QuotationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object QuotationDiff : DiffUtil.ItemCallback<Quotation>(){
    override fun areItemsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
        return oldItem == newItem
    }
}

