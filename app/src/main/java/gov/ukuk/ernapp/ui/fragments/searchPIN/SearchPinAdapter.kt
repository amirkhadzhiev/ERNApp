package gov.ukuk.ernapp.ui.fragments.searchPIN

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.FineItemBinding
import gov.ukuk.ernapp.models.protocol.Protocol

class SearchPinAdapter : RecyclerView.Adapter<SearchPinAdapter.ViewHolder>(){

    private val listPin = mutableListOf<Protocol>()


    fun clearAdapter() {
        if (!this.listPin.isEmpty()) {
            this.listPin.clear()
        }
    }

    fun addItems(list: MutableList<Protocol>) {
        if(!listPin.containsAll(list)){
            listPin.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val protocol = listPin[position]

        holder.binding.apply {
            fineNumberTv.setText(protocol.code)
            fineNameTv.setText(protocol.physicalData?.name + " " + protocol.physicalData?.surname + " " + protocol.physicalData?.patronymic)
            sumNumberTv.setText((protocol.penaltyAmount?.plus(protocol.paymentFineTotal!!)).toString())
            codePaymentTv.setText(protocol.paymentCode)
        }
        holder.itemView.setOnClickListener{
            onProtocolClickListener?.let { it(protocol) }
        }

    }

    override fun getItemCount(): Int = listPin.size

    class ViewHolder(val binding: FineItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    private var onProtocolClickListener: ((Protocol) -> Unit)? =  null
    fun setOnProtocolClickListener(listener: (Protocol) -> Unit) {
        onProtocolClickListener = listener
    }



}