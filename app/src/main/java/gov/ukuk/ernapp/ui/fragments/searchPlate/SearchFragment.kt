package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import gov.ukuk.ernapp.data.network.Status
import gov.ukuk.ernapp.databinding.FragmentSearchBinding
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.Protocol
import gov.ukuk.ernapp.models.TundukProtocol1
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchAdapter = SearchAdapter()
    private val searchBgAdapter = SearchBgAdapter()
    private val viewModel : SearchViewModel by viewModel()
    private lateinit var name: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupViews()
    }

    private fun initialize(){
        binding.searchRv.adapter = searchAdapter
        binding.searchBgRv.adapter = searchBgAdapter
//        searchAdapter.addListener(this)
    }


    private fun setupViews(){
        binding.searchBtn.setOnClickListener {
            searchAdapter.clearAdapter()
            searchBgAdapter.clearAdapter()
            dismissKeyboard(requireActivity())
            name = binding.searchEt.text.toString()

            if(name.trim().length>0) {
                fetchData(name)
                fetchDataBG(Info(name))
            }else{
                Toast.makeText(requireContext(), "Пожалуйста, не оставляйте поик пустым! ", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun fetchData(plateNumber: String){
        viewModel.fetchFilteredDataProt(plateNumber).observe(viewLifecycleOwner, Observer { resources ->
            when(resources.status){
                Status.SUCCESS -> {
                    resources.data?.let {
                        if(it.size==0) {
                            Toast.makeText(requireContext(),"Протоколы не найдены", Toast.LENGTH_SHORT).show()
                        }
                    }
                    resources.data?.let {
                        searchAdapter.addItems(it as ArrayList<Protocol>)
                        System.out.println(it)
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun fetchDataBG(plate: Info){
        viewModel.getViolationsByPl(plate).observe(viewLifecycleOwner, Observer { resources ->
            when(resources.status){
                Status.SUCCESS -> {

                    System.out.println(resources.data)
                    resources.data?.let {
                        searchBgAdapter.addItems( it.protocols as ArrayList<TundukProtocol1>)
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

//    override fun onClick(id: Int, type: Int) {
//        val bundle = Bundle()
//        bundle.getInt("idKey", id)
//        bundle.getInt("typeKey", type)
//        findNavController().navigate(R.id.detailFragment,bundle)
//    }


}