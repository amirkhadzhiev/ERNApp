package gov.ukuk.ernapp.ui.fragments.searchPIN

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import gov.ukuk.ernapp.data.network.Status
import gov.ukuk.ernapp.databinding.FragmentSearchPinBinding
import gov.ukuk.ernapp.models.Protocol
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList
import androidx.lifecycle.Observer
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.SendPin
import gov.ukuk.ernapp.models.TundukProtocol1
import gov.ukuk.ernapp.ui.fragments.searchPlate.SearchViewModel


class SearchPinFragment : Fragment() {

    private var _binding: FragmentSearchPinBinding? = null
    private val binding get() = _binding!!
    private val searchPinAdapter = SearchPinAdapter()
    private val searchBgPinAdapter = SearchBgPinAdapter()
    private val viewModel : SearchPinViewModel by viewModel()
    private val viewModelBg : SearchViewModel by viewModel()
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchPinBinding.inflate(inflater, container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupViews()
    }

    private fun initialize(){
        binding.searchPinRv.adapter = searchPinAdapter
        binding.searchBgPinRv.adapter = searchBgPinAdapter
//        searchAdapter.addListener(this)
    }

    private fun setupViews(){
        binding.searchBtn.setOnClickListener {
            searchPinAdapter.clearAdapter()
            searchBgPinAdapter.clearAdapter()
            dismissKeyboard(requireActivity())
            name = binding.searchEt.text.toString()

            if(name.trim().length>0) {
                fetchDataPin(name)
                getCarsByPin(SendPin(name))
            }else{
                Toast.makeText(requireContext(), "Пожалуйста, не оставляйте поик пустым! ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchDataPin(inn: String){
        viewModel.fetchFilteredDataByPin(inn).observe(viewLifecycleOwner, Observer { resources ->
            when(resources.status){
                Status.SUCCESS -> {
                    resources.data?.let {
                        if(it.size==0) {
                            Toast.makeText(requireContext(),"Протоколы не найдены", Toast.LENGTH_SHORT).show()
                        }
                    }
                    resources.data?.let {
                        searchPinAdapter.addItems(it as ArrayList<Protocol>)
                        System.out.println(it)
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getCarsByPin(pin: SendPin){
        viewModel.getCarsInfoByPin(pin).observe(viewLifecycleOwner, Observer { resources ->
            when(resources.status){
                Status.SUCCESS -> {
                    resources.data?.let {
                        if(it.size==0) {
                            Toast.makeText(requireContext(),"Не найдено зарегестрированных машин по ИНН", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            for (i in it) {
                                fetchDataBG(Info(i.govPlate))
                            }
                        }

                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun fetchDataBG(plate: Info){
        viewModelBg.getViolationsByPl(plate).observe(viewLifecycleOwner, Observer { resources ->
            when(resources.status){
                Status.SUCCESS -> {

                    System.out.println(resources.data)
                    resources.data?.let {
                        val protocols = it.protocols as ArrayList<TundukProtocol1>
                        for(protocol in protocols){
                            protocol.govPlate = plate.plate
                        }
                        searchBgPinAdapter.addItems(protocols)
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Штрафы не найдены", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    fun dismissKeyboard(activity: Activity){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
            activity.currentFocus!!.applicationWindowToken, 0
        )
    }

}