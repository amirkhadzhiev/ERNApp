package gov.ukuk.ernapp.ui.fragments.searchPIN

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.FinePinItemBinding
import gov.ukuk.ernapp.models.TundukProtocol1
import gov.ukuk.ernapp.models.protocol.Protocol
import gov.ukuk.ernapp.ui.fragments.searchPlate.SearchAdapter

class SearchBgPinAdapter : RecyclerView.Adapter<SearchBgPinAdapter.ViewHolder>(){

    val listBg: ArrayList<TundukProtocol1> = ArrayList()



    fun clearAdapter() {
        if (!this.listBg.isEmpty()) {
            this.listBg.clear()
        }
    }

    fun addItems(listBg: java.util.ArrayList<TundukProtocol1>) {
        if(!this.listBg.containsAll(listBg)){
            this.listBg.addAll(listBg)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FinePinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val protocol =  listBg[position]
        holder.binding.apply {
            sumNumberTv.setText(protocol.violationAmmount)
            codePaymentTv.setText(protocol.paymentNumber)
            numberCarPin.setText(protocol.govPlate)

        }

        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putSerializable("plate", protocol.govPlate)
                putSerializable("paymentNumber", protocol.paymentNumber)
            }
            view.findNavController().navigate(gov.ukuk.ernapp.R.id.action_fragmentSearchPin_to_violationFragment, bundle)
        }
    }



    override fun getItemCount(): Int = listBg.size

    class ViewHolder(val binding: FinePinItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private var onProtocolClickListener: ((Protocol) -> Unit)? =  null
    fun setOnProtocolClickListener(listener: (Protocol) -> Unit) {
        onProtocolClickListener = listener
    }


}