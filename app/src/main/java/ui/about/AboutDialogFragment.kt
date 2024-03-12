package ui.about

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dadm.mchazna.myapp1.R
import dadm.mchazna.myapp1.databinding.FragmentAboutBinding

class AboutDialogFragment : DialogFragment(R.layout.fragment_about) {

    private var _binding: FragmentAboutBinding? = null
    private  val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAboutBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




