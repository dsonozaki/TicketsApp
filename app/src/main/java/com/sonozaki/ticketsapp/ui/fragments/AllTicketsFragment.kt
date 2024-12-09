package com.sonozaki.ticketsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.sonozaki.ticketsapp.R
import com.sonozaki.ticketsapp.adapter.ticket.AsyncListTicketDifferAdapter
import com.sonozaki.ticketsapp.databinding.FragmentAllTicketsBinding
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.states.TicketState
import com.sonozaki.ticketsapp.ui.viewmodels.TicketViewModel
import com.sonozaki.ticketsapp.utils.daysSinceEpochToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class AllTicketsFragment : Fragment() {

    private var _binding: FragmentAllTicketsBinding? = null

    @Inject
    lateinit var ticketAdapter: AsyncListTicketDifferAdapter

    private val args: AllTicketsFragmentArgs by navArgs()

    private val viewModel by viewModels<TicketViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAllTicketsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()
        observeData()
        observeBackButton()
    }

    private fun observeBackButton() {
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun loadData() {
        val travelParams = TravelParams(args.startCity, args.endCity, args.date)
        viewModel.updateTravelParams(travelParams)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.travelParamsFlow.collect {
                    setupText(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ticketDataFlow.collect {
                    when (it) {
                        is TicketState.Data -> handleData(it.result)
                        is TicketState.Loading -> handleLoading()
                    }
                }
            }
        }
    }

    private fun setupText(travelParams: TravelParams) {
        with(binding) {
            destinations.text = requireContext().getString(
                R.string.destinations,
                travelParams.startCity,
                travelParams.endCity
            )
            date.text = requireContext().getString(
                R.string.date_params,
                daysSinceEpochToDate(travelParams.departureDate)
            )
        }
    }

    private fun handleLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.allTickets.visibility = View.GONE
    }

    private fun handleData(result: RequestResult<List<Ticket>>) {
        binding.progress.visibility = View.GONE
        binding.allTickets.visibility = View.VISIBLE
        when (result) {
            is RequestResult.Data -> ticketAdapter.items = result.data
            is RequestResult.Error -> Log.w("error", "loading error")
        }
    }

    private fun setupRecyclerView() {
        with(binding.allTickets) {
            adapter = ticketAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}