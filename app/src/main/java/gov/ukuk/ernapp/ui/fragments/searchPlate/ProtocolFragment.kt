package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gov.ukuk.ernapp.databinding.FragmentProtocolBinding
import gov.ukuk.ernapp.ui.activities.MainActivity


class ProtocolFragment : Fragment() {
    private lateinit var binding : FragmentProtocolBinding
    private val args : ProtocolFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProtocolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val protocol = args.protocol
        binding.apply {
            auto.text = "Авто: " + protocol.vehicleBrand + " " + protocol.vehicleModel
            protocolNumber.text = "Номер протокола: " +  protocol.code
            article.text = "Статья: " + protocol.part?.parent?.number + " " + protocol.part?.parent?.title
            description.text = "Описание нарушения: " + protocol.part?.title
            paymentCode.text = "Код оплаты: " +  protocol.paymentCode
            paySum.text = "Сумма к оплате: " + (protocol.paymentFineTotal + protocol.penaltyAmount).toInt() +
                    " (" + protocol.paymentFineTotal.toInt() + " штраф + " + protocol.penaltyAmount.toInt() + " пеня)"
            INN.text = "ИНН: " + protocol.physicalData.inn
            dateRegistration.text = "Дата регистрации: " + protocol.registrationDate
        }

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