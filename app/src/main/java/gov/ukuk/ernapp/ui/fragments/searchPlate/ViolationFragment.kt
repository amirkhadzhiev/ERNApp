package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gov.ukuk.ernapp.data.network.Resource

import gov.ukuk.ernapp.databinding.FragmentViolationBinding
import gov.ukuk.ernapp.models.InfoGov
import gov.ukuk.ernapp.models.violation.ProtocolsByPin
import gov.ukuk.ernapp.ui.activities.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ViolationFragment : Fragment() {
    private lateinit var binding : FragmentViolationBinding
    private val viewModel: SearchViewModel by viewModel()
    private val args : ViolationFragmentArgs by navArgs()
    private lateinit var plate : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViolationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plate = args.plate.toUpperCase()

        viewModel.getCarByPlate(InfoGov(plate))

        viewModel.carByPlate.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let {
                        viewModel.getViolationsByPlateAndPin(plate, it.pin)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error2", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.violation.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let {

                        var protocol : ProtocolsByPin? = null
                        for (vio in it.protocolsByPin){
                            if(vio.paymentNumber ==  args.paymentNumber){
                                protocol = vio
                            }
                        }

                        binding.apply {
                            dateBorn.text = "Дата рождения: " + protocol?.birthdate
                            address.text = "Аддрес: " + protocol?.address
                            auto.text = "Авто: " + protocol?.carBrand + " " + protocol?.carModel
                            paymentCode.text = "Код оплаты: " + protocol?.paymentNumber
                            isPayed.text =  protocol?.paymentStatusName
                            dateRegistration.text = "Дата регистрации: " + protocol?.violationDate
                            violationPlace.text = "Место нарушения: " + protocol?.violationPlace
                            article.text = "Статья: " + protocol?.violationArticle + " " + protocol?.violationType

                            linear.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error2", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        })
        binding.btnClose.setOnClickListener{
            findNavController().popBackStack()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }
}