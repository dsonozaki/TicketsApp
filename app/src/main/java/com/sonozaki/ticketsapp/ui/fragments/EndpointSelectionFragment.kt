package com.sonozaki.ticketsapp.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonozaki.ticketsapp.databinding.FragmentBottomSheetSearchBinding
import com.sonozaki.ticketsapp.utils.CyrillicInputFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EndpointSelectionFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var inputFilter: CyrillicInputFilter

    private var _binding: FragmentBottomSheetSearchBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        expandBottomSheet()
    }

    /**
     * Set bottom sheet to full size
     */
    private fun expandBottomSheet() {
        val bottomSheetDialog = dialog as BottomSheetDialog
        val parentLayout =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        val bottomSheetBehavior = BottomSheetBehavior.from(parentLayout)
        bottomSheetBehavior.skipCollapsed = true
        val height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheetBehavior.peekHeight = height
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.rootContainer.layoutParams.height = height
        binding.rootContainer.requestLayout()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRussianTextViews()
        setupDialogBackground()
        restoreStartPoint()
        listenToButtons()
        listenToTextInput()
    }

    /**
     * Restore start point text
     */
    private fun restoreStartPoint() {
        arguments?.let {
            val startPoint = it.getString(START_POINT) ?: ""
            binding.startPoint.text = startPoint
        }
    }

    /**
     * Setup transparent background
     */
    fun setupDialogBackground() {
        dialog?.apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    /**
     * Show user next screen when he press enter button
     */
    private fun listenToTextInput() {
        binding.endPoint.apply {
            setOnEditorActionListener { _: TextView?, eventId: Int, _: KeyEvent? ->
                if (eventId == EditorInfo.IME_ACTION_SEND) {
                    moveToTicketOffers(text.toString())
                }
                return@setOnEditorActionListener true
            }
        }
    }

    /**
     * Prohibit text other than cyrillic
     */
    private fun setupRussianTextViews() {
        binding.endPoint.filters = arrayOf(inputFilter)
    }

    /**
     * Handle button clicks. Some buttons change arrival point text and open next screen, others open placeholder.
     */
    private fun listenToButtons() {
        with(binding) {
            binding.phuket.setOnClickListener {
                val endPointText = binding.phuketText.text.toString()
                endPoint.setText(endPointText)
                delayMoveToTicketOffers(endPointText)
            }
            binding.anywhere.setOnClickListener {
                val endPointText = binding.anywhereText.text.toString()
                endPoint.setText(endPointText)
                delayMoveToTicketOffers(endPointText)
            }
            binding.sochi.setOnClickListener {
                val endPointText = binding.sochiText.text.toString()
                endPoint.setText(endPointText)
                delayMoveToTicketOffers(endPointText)
            }
            binding.istanbul.setOnClickListener {
                val endPointText = binding.istanbulText.text.toString()
                endPoint.setText(endPointText)
                delayMoveToTicketOffers(endPointText)
            }
            binding.hotTickets.setOnClickListener {
                moveToPlaceholder()
            }
            binding.complexRoute.setOnClickListener {
                moveToPlaceholder()
            }
            binding.weekend.setOnClickListener {
                moveToPlaceholder()
            }
            binding.clear.setOnClickListener {
                endPoint.setText("")
            }
        }
    }

    private fun moveToPlaceholder() {
        val resultBundle = Bundle().apply {
            putBoolean(PLACEHOLDER_KEY, true)
        }
        setFragmentResult(REQUEST_KEY, resultBundle)
        dismiss()
    }


    private fun delayMoveToTicketOffers(endpointText: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(DELAY)
            moveToTicketOffers(endpointText)
        }
    }

    private fun moveToTicketOffers(endpointText: String) {
        if (endpointText.isBlank())
            return
        val resultBundle = Bundle().apply {
            putString(ENDPOINT_KEY, endpointText)
        }
        setFragmentResult(REQUEST_KEY, resultBundle)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "endpoint_selection"
        private const val DELAY = 300L
        const val START_POINT = "startpoint_key"
        const val ENDPOINT_KEY = "endpoint_key"
        const val PLACEHOLDER_KEY = "placeholder_key"
        const val REQUEST_KEY = "endpoint_request"
        fun newInstance(startPoint: String): EndpointSelectionFragment {
            return EndpointSelectionFragment().apply {
                arguments = bundleOf(START_POINT to startPoint)
            }
        }
    }

}