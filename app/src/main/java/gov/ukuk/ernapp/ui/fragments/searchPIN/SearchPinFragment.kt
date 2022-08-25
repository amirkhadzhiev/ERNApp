package gov.ukuk.ernapp.ui.fragments.searchPIN

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import gov.ukuk.ernapp.databinding.FragmentSearchPinBinding

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import gov.ukuk.ernapp.R
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.models.CarInfo
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.SendPin
import gov.ukuk.ernapp.models.TundukProtocol1
import gov.ukuk.ernapp.ui.fragments.searchPlate.SearchCarAdapter
import gov.ukuk.ernapp.ui.fragments.searchPlate.SearchViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class SearchPinFragment : Fragment() {

    private var _binding: FragmentSearchPinBinding? = null
    private val binding get() = _binding!!
    private val searchPinAdapter = SearchPinAdapter()
    private val searchBgPinAdapter = SearchBgPinAdapter()
    private val searchCarAdapter = SearchCarAdapter()
    private val viewModel: SearchPinViewModel by viewModel()
    private lateinit var name: String
    private var carNumber: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchPinBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservables()
        initialize()
        setupViews()

    }

    private fun setObservables() {

        viewModel.protocols.observe(viewLifecycleOwner, Observer { responce ->
            when (responce) {
                is Resource.Succes -> {
                    responce.data?.let {
                        if (it.size == 0) {
                            binding.titleProtocolsPin.visibility = View.VISIBLE
                            binding.titleProtocolsPin.setTextColor(Color.parseColor("#347C2C"))
                            binding.titleProtocolsPin.text = "Протоколов в ЕРН нету"
                            binding.pBar.visibility = View.GONE
                        }
                    }
                    responce.data?.let {
                        searchPinAdapter.addItems(it)
                        System.out.println(it)
                        var sum = 0
                        it.forEach { prot ->
                            sum += prot.penaltyAmount.plus(prot.paymentFineTotal).toInt()
                        }
                        binding.titleProtocolsPin.visibility = View.VISIBLE
                        binding.searchPinRv.visibility = View.VISIBLE
                        binding.titleProtocolsPin.text =
                            "Протоколы в ЕРН:            Колличество: " + it.size +
                                    "          Общая сумма: " + sum
                        binding.titleProtocolsPin.setTextColor(Color.parseColor("#FF0000"))
                        binding.pBar.visibility = View.GONE

                    }
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    binding.pBar.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.titleProtocolsPin.visibility = View.GONE
                    binding.pBar.visibility = View.VISIBLE
                    binding.searchPinRv.visibility = View.GONE
                }

            }
        })


        viewModel.carInfos.observe(viewLifecycleOwner, Observer { responce ->
            when (responce) {
                is Resource.Succes -> {
                    responce.data?.let { it ->
                        if (it.size == 0) {
                            binding.titleCarsPin.text = "Машин не найдено"
                            binding.titleCarsPin.visibility = View.VISIBLE
                            binding.pBar.visibility = View.GONE
                        } else {
                            searchCarAdapter.addItems(it as ArrayList<CarInfo>)
                            GlobalScope.launch {
                                it.forEach {
                                    viewModel.getViolationsByPl(Info(it.govPlate)).join()
                                    carNumber = it.govPlate
                                }
                            }

                            binding.titleCarsPin.text = "Машины: "
                            binding.titleCarsPin.visibility = View.VISIBLE
                            binding.searchCarRv.visibility = View.VISIBLE
                            binding.pBar.visibility = View.GONE

                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    binding.pBar.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.titleCarsPin.visibility = View.GONE
                    binding.pBar.visibility = View.VISIBLE
                    binding.searchCarRv.visibility = View.GONE
                }
            }
        })

        viewModel.tundukBezGorod.observe(viewLifecycleOwner, Observer { responce ->
            when (responce) {
                is Resource.Succes -> {

                    responce.data?.let {
                        System.out.println(responce.data)
                        val protocols = it.protocols as ArrayList<TundukProtocol1>
                        for(protocol in protocols){
                            protocol.govPlate = carNumber.toUpperCase()
                        }
                        searchBgPinAdapter.addItems(protocols)

                        var sum = 0
                        searchBgPinAdapter.listBg.forEach { prot ->
                            sum += prot.violationAmmount.substring(
                                0,
                                prot.violationAmmount.indexOf(".")
                            ).toInt()
                        }
                        binding.titleFinesPin.text =
                            "Штрафы в Безопасном городе:            Колличество: " + searchBgPinAdapter.listBg.size +
                                    "          Общая сумма: " + sum
                        binding.titleFinesPin.setTextColor(Color.parseColor("#FF0000"))
                        binding.pBar.visibility = View.GONE
                        binding.searchBgPinRv.visibility = View.VISIBLE
                        binding.titleFinesPin.visibility = View.VISIBLE
                    }
                }

                is Resource.Error -> {
                    binding.titleFinesPin.setTextColor(Color.parseColor("#347C2C"))
                    binding.titleFinesPin.text = "Штрафов в Безопасном городе нет"
                    binding.titleFinesPin.visibility = View.VISIBLE
                    binding.pBar.visibility = View.GONE
                }

                is Resource.Loading -> {
                    binding.titleFinesPin.visibility = View.GONE
                    binding.pBar.visibility = View.VISIBLE
                    binding.searchBgPinRv.visibility = View.GONE
                }
            }
        })

    }

    private fun initialize() {
        searchPinAdapter.setOnProtocolClickListener {
            val bundle = Bundle().apply {
                putSerializable("protocol", it)
            }
            findNavController().navigate(
                R.id.action_fragmentSearchPin_to_protocolFragment,
                bundle
            )

        }
        binding.searchPinRv.adapter = searchPinAdapter
        binding.searchBgPinRv.adapter = searchBgPinAdapter
        binding.searchCarRv.adapter = searchCarAdapter
//        searchAdapter.addListener(this)

    }

    private fun setupViews() {
        binding.searchBtn.setOnClickListener {
            searchPinAdapter.clearAdapter()
            searchBgPinAdapter.clearAdapter()
            searchCarAdapter.clearAdapter()
            dismissKeyboard(requireActivity())

            name = binding.searchEt.text.toString()

            if (name.trim().length > 0) {
                viewModel.fetchFilteredDataByPin(name)
                viewModel.getCarsInfoByPin(SendPin(name))

            } else {
                Toast.makeText(
                    requireContext(),
                    "Пожалуйста, не оставляйте поик пустым! ",
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