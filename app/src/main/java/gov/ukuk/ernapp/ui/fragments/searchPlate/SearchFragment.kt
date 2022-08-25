package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import gov.ukuk.ernapp.R
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.databinding.FragmentSearchBinding


import gov.ukuk.ernapp.models.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchAdapter = SearchAdapter()
    private val searchBgAdapter = SearchBgAdapter()
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var name: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservables()
        initialize()
        setupViews()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObservables() {
        viewModel.protocols.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let {
                        binding.titleProtocols.visibility = View.VISIBLE
                        if (it.isNotEmpty()) {
                            searchAdapter.addItems(it)

                            var sum = 0
                            it.forEach { prot ->
                                sum += prot.penaltyAmount.plus(prot.paymentFineTotal).toInt()
                            }
                            binding.searchRv.visibility = View.VISIBLE
                            binding.titleProtocols.text =
                                "Протоколы в ЕРН:            Колличество: " + it.size +
                                        "          Общая сумма: " + sum
                            binding.titleProtocols.setTextColor(Color.parseColor("#FF0000"))
                        } else {
                            binding.titleProtocols.setTextColor(Color.parseColor("#347C2C"))
                            binding.titleProtocols.text = "Протоколов в ЕРН нету"
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error3", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.tundukBezGorod.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let {
                        if (it.protocols?.isNotEmpty() == true) {
                            val prots = it.protocols as ArrayList<TundukProtocol1>
                            var sum = 0
                            prots.forEach { prot ->
                                prot.govPlate = name
                                sum += prot.violationAmmount.substring(
                                    0,
                                    prot.violationAmmount.indexOf(".")
                                ).toInt()

                            }

                            for(protocol in it.protocols){
                                protocol.govPlate = name.toUpperCase()
                            }
                            searchBgAdapter.addItems(prots)
                            binding.searchBgRv.visibility = View.VISIBLE
                            binding.titleFines.visibility = View.VISIBLE
                            binding.titleFines.text =
                                "Штрафы в Безопасном городе:            Колличество: " + prots.size +
                                        "          Общая сумма: " + sum
                            binding.titleFines.setTextColor(Color.parseColor("#FF0000"))
                        } else {
                            binding.titleFines.visibility = View.VISIBLE
                            binding.titleFines.setTextColor(Color.parseColor("#347C2C"))
                            binding.titleFines.text = "Штрафов в Безопасном городе нет"
                        }
                    }
                }
                is Resource.Error -> {
                    binding.titleFines.visibility = View.VISIBLE
                    binding.titleFines.setTextColor(Color.parseColor("#347C2C"))
                    binding.titleFines.text = "Штрафов в Безопасном городе нет"
                }
            }
        })

        viewModel.carByPlate.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let {
                        binding.apply {
                            ownerName.text = it.ownerFullName
                            birthday.text = it.birthDate
                            pin.text = it.pin
                            carNumber.text =  it.govPlate
                            model.text = it.brand + " " + it.model
                            color.text =  it.color

                            binding.infoCar.visibility = View.VISIBLE
                            binding.pBar.visibility = View.GONE
                            binding.carData.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error2", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initialize() {
        searchAdapter.setOnProtocolClickListener {
            val bundle = Bundle().apply {
                putSerializable("protocol", it)
            }
            findNavController().navigate(
                R.id.action_fragmentSearch_to_protocolFragment,
                bundle
            )
        }
        binding.searchRv.adapter = searchAdapter
        binding.searchBgRv.adapter = searchBgAdapter
    }


    private fun setupViews() {
        binding.searchBtn.setOnClickListener {

            searchAdapter.clearAdapter()
            searchBgAdapter.clearAdapter()
            dismissKeyboard(requireActivity())


            name = binding.searchEt.text.toString()
            if (name.trim().length > 0) {
                viewModel.fetchFilteredDataProt(name)
                viewModel.getViolationsByPl(Info(name))
                viewModel.getCarByPlate(InfoGov(name.toUpperCase()))
            } else {
                Toast.makeText(
                    requireContext(),
                    "Пожалуйста, не оставляйте поиcк пустым! ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun dismissKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
            activity.currentFocus!!.applicationWindowToken, 0
        )
    }



}
