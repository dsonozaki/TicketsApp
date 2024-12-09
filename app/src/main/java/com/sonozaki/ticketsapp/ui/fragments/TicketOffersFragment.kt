package com.sonozaki.ticketsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.sonozaki.ticketsapp.R
import com.sonozaki.ticketsapp.adapter.ticketOffer.AsyncListTicketOfferDifferAdapter
import com.sonozaki.ticketsapp.databinding.FragmentOfferTicketsBinding
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.entities.Travel
import com.sonozaki.ticketsapp.states.TicketOfferState
import com.sonozaki.ticketsapp.ui.viewmodels.TicketOffersViewModel
import com.sonozaki.ticketsapp.utils.CyrillicInputFilter
import com.sonozaki.ticketsapp.utils.formatDaysSinceEpoch
import com.sonozaki.ticketsapp.utils.timestampToDays
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class TicketOffersFragment: Fragment() {

    private var _binding: FragmentOfferTicketsBinding? = null

    @Inject
    lateinit var ticketOfferAdapter: AsyncListTicketOfferDifferAdapter

    @Inject
    lateinit var inputFilter: CyrillicInputFilter

    private val args: TicketOffersFragmentArgs by navArgs()


    private val viewModel by viewModels<TicketOffersViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRussianTextViews()
        setupText()
        observeData()
        observeEnteredText()
        observeClickedCalendars()
        observeClickedButtons()
        observeSubscriptionEvent()
        setupOpenNextScreenButton()
    }

    /**
     * Next fragment opening and passing data about travel
     */
    private fun setupOpenNextScreenButton() {
        binding.seeAllTickets.setOnClickListener {
            viewModel.travelParams?.let {
                val action =
                    TicketOffersFragmentDirections.actionTicketOffersFragmentToAllTicketsFragment(
                        it.departureDate,
                        it.startCity,
                        it.endCity
                    )
                findNavController().navigate(action)
            }
        }
    }



    private fun observeSubscriptionEvent() {
        binding.subscriptionSwitch.setOnCheckedChangeListener { _, checked ->
            val message = if (checked) {
                R.string.subscribed
            } else {
                R.string.unsubscribed
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeClickedButtons() {
        //clear text button handling
        binding.clear.setOnClickListener {
            binding.endPoint.setText("")
        }
        //change texts button handling
        binding.reverse.setOnClickListener {
            val endPoint = binding.endPoint.text.toString()
            val startPoint = binding.startPoint.text.toString()
            binding.endPoint.setText(startPoint)
            binding.startPoint.setText(endPoint)
            updateTravelPoints()
        }
        //back button handling
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    /**
     * Show date picker
     * @param updateDate if true, update departure date data on positive button click.
     */
    private fun showDatePicker(updateDate: Boolean) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .build()

        datePicker.show(parentFragmentManager, DATE_PICKER)

        datePicker.addOnPositiveButtonClickListener { selection ->
            if (updateDate) {
                val unixTimeMillis = selection ?: 0L
                val days = timestampToDays(unixTimeMillis)
                viewModel.updateDate(days)
            }
        }
    }

    /**
     * Handle clicks on date selection chips
     */
    private fun observeClickedCalendars() {
        binding.arrivalDate.setOnClickListener {
            showDatePicker(true)
        }
        binding.departureDate.setOnClickListener {
            showDatePicker(false)
        }
    }

    /**
     * Update travel destinations data when user clicks enter button
     */
    private fun observeEnteredText() {
        binding.endPoint.setOnEditorActionListener { _: TextView?, eventId: Int, _: KeyEvent? ->
            if (eventId == EditorInfo.IME_ACTION_SEND) {
                updateTravelPoints()
            }
            return@setOnEditorActionListener true
        }
        binding.startPoint.setOnEditorActionListener { _: TextView?, eventId: Int, _: KeyEvent? ->
            if (eventId == EditorInfo.IME_ACTION_SEND) {
                updateTravelPoints()
            }
            return@setOnEditorActionListener true
        }
    }

    /**
     * Observe ticket offers data
     */
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ticketOffersFlow.collect {
                    when (it) {
                        is TicketOfferState.Data -> handleData(it.data)
                        is TicketOfferState.Loading -> handleLoading()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dateFlow.collect {
                    showDate(it)
                }
            }
        }
    }

    private fun showDate(date: Long) {
        val (day, dayOfWeek) = formatDaysSinceEpoch(requireContext(),date)
        binding.dayMonthText.text = day.trimEnd('.')
        binding.dayWeekText.text = requireContext().getString(R.string.dateFormat,dayOfWeek)
    }

    private fun handleData(result:  RequestResult<List<TicketOffer>>) {
        binding.progress.visibility = View.GONE
        binding.flights.visibility = View.VISIBLE
        when (result) {
            is RequestResult.Data -> {
                ticketOfferAdapter.items = result.data.take(3)
            }
            //place for handling errors
            is RequestResult.Error -> Log.w("Loading error",result.errorText)
        }
    }

    private fun handleLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.flights.visibility = View.GONE
    }

    /**
     * Restore text from passed arguments
     */
    private fun setupText() {
        if (!viewModel.textSetupEnded) {
            binding.startPoint.setText(args.startPoint)
            binding.endPoint.setText(args.endPoint)
            updateTravelPoints()
        }
        viewModel.textSetupEnded = true
    }

    private fun updateTravelPoints() {
        viewModel.updateTravelPoints(Travel(binding.startPoint.text.toString(), binding.endPoint.text.toString()))
    }

    /**
     * Setup recyclerview adapter
     */
    private fun setupRecyclerView() {
        with(binding.flights) {
            adapter = ticketOfferAdapter
        }
    }

    /**
     * Prohibit text other than cyrillic one
     */
    private fun setupRussianTextViews() {
        binding.startPoint.filters = arrayOf(inputFilter)
        binding.endPoint.filters = arrayOf(inputFilter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DATE_PICKER = "MATERIAL_DATE_PICKER"
    }
}