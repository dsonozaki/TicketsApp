package com.sonozaki.ticketsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sonozaki.ticketsapp.adapter.offer.AsyncListOfferDifferAdapter
import com.sonozaki.ticketsapp.databinding.FragmentTicketsBinding
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.states.OfferState
import com.sonozaki.ticketsapp.ui.fragments.EndpointSelectionFragment.Companion.ENDPOINT_KEY
import com.sonozaki.ticketsapp.ui.fragments.EndpointSelectionFragment.Companion.PLACEHOLDER_KEY
import com.sonozaki.ticketsapp.ui.fragments.EndpointSelectionFragment.Companion.REQUEST_KEY
import com.sonozaki.ticketsapp.ui.viewmodels.TicketsStartViewModel
import com.sonozaki.ticketsapp.utils.CyrillicInputFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TicketsStartFragment : Fragment() {

    @Inject
    lateinit var offerDifferAdapter: AsyncListOfferDifferAdapter

    @Inject
    lateinit var inputFilter: CyrillicInputFilter

    private val viewModel by viewModels<TicketsStartViewModel>()

    private var _binding: FragmentTicketsBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentTicketsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRussianTextViews()
        loadStartCity()
        listenStartCity()
        setupRecyclerView()
        listenState()
        updateStartPoint()
        listenToNavigate()
    }

    private fun listenStartCity() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startCity.collect {
                    if (it!=null) {
                        binding.startPoint.setText(it)
                    }
                }
            }
        }
    }

    private fun loadStartCity() {
        viewModel.loadStartCity()
    }

    private fun setupRussianTextViews() {
        binding.startPoint.filters = arrayOf(inputFilter)
    }

    private fun listenToNavigate() {
        binding.endPoint.setOnClickListener {
            val startPointText = binding.startPoint.text.toString()
            if (startPointText.isNotBlank()) {
                val bottomSheetFragment =
                    EndpointSelectionFragment.newInstance(startPointText)
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                bottomSheetFragment.setFragmentResultListener(REQUEST_KEY, ::bottomSheetResultListener)
            }
        }
    }

    private fun bottomSheetResultListener(endPoint: String, result: Bundle) {
        val endPoint = result.getString(ENDPOINT_KEY, "")

        if (endPoint.isNotBlank()) {
            openTicketOffersScreen(
                binding.startPoint.text.toString(),
                endPoint
            )
        }
        val openPlaceHolder =
            result.getBoolean(PLACEHOLDER_KEY, false)
        if (openPlaceHolder) {
            openPlaceHolder()
        }
    }

    private fun openPlaceHolder() {
        val action =
            TicketsStartFragmentDirections.actionTicketsToPlaceHolderFragment()
        findNavController().navigate(action)
    }

    private fun openTicketOffersScreen(startPoint: String, endPoint: String) {
        val action =
            TicketsStartFragmentDirections.actionTicketsToTicketOffersFragment(
                startPoint,
                endPoint
            )
        findNavController().navigate(action)
    }

    private fun updateStartPoint() {
        binding.startPoint.doOnTextChanged { text, _, _, _ -> viewModel.updateInitialCity(text.toString()) }
    }

    private fun setupRecyclerView() {
        with(binding.offersList) {
            adapter = offerDifferAdapter
        }
    }

    private fun listenState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.offersFlow.collect {
                    when (it) {
                        is OfferState.Loading -> {
                            setupLoading()
                        }
                        is OfferState.Data -> {
                            setupData(it.offersResult)
                        }
                    }
                }
            }
        }
    }

    private fun setupLoading() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun setupData(result: RequestResult<List<Offer>>) {
        binding.progress.visibility = View.GONE
        when (result) {
            is RequestResult.Data -> offerDifferAdapter.items = result.data
            is RequestResult.Error -> Log.w("net_error",result.errorText)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}