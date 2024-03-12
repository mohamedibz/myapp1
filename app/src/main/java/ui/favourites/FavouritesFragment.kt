package ui.favourites

import android.content.ActivityNotFoundException
import android.content.ClipData.Item
import android.content.Intent
import android.media.MediaRouter.SimpleCallback
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dadm.mchazna.myapp1.R
import dadm.mchazna.myapp1.databinding.FragmentFavouritesBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.URI

class FavouritesFragment : Fragment(R.layout.fragment_favourites), MenuProvider{

    private var _binding: FragmentFavouritesBinding? = null
    private  val binding
        get() = _binding!!

    private val touchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteQuotationAtPosition(direction)
        }

    }

    private val viewModel: FavouritesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        val adapter = QuotationListAdapter(::onItemClick)
        binding.textView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect{
                    adapter.submitList(viewModel.list.value)
                }
            }
        }

        adapter.submitList(viewModel.list.value)

        requireActivity().addMenuProvider(this,
            viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDeleteAllMenuVisible.collect{ requireActivity().invalidateMenu() }
            }
        }

        val touchHelper2 = ItemTouchHelper(touchHelper)

        touchHelper2.attachToRecyclerView(binding.textView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)


    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_favourites, menu)
        menu.findItem(R.id.deleteAll).setVisible(viewModel.isDeleteAllMenuVisible.value)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.deleteAll) {
            findNavController().navigate(R.id.deleteAllDialogFragment)
            return true
        }
        return false
    }

    fun onItemClick(author: String): Unit{
        if(author == "Anonymous"){
            Snackbar.make(binding.root, "Author is anonymous", Snackbar.LENGTH_SHORT).show()
        }else{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + author))
            try {
                this.startActivity(intent)
            }catch (e: ActivityNotFoundException){
                Snackbar.make(binding.root, "Is not possible to show author's data", Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}