package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.FineItemBinding
import gov.ukuk.ernapp.models.protocol.Protocol

import kotlin.collections.ArrayList

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val list = mutableListOf<Protocol>()

    fun clearAdapter() {
        if (!this.list.isEmpty()) {
            this.list.clear()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(list: MutableList<Protocol>) {
        if(!this.list.containsAll(list)){
            this.list.addAll(list)
            notifyDataSetChanged()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val binding =
            FineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)


    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val protocol = list[position]
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

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: FineItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var onProtocolClickListener: ((Protocol) -> Unit)? =  null
    fun setOnProtocolClickListener(listener: (Protocol) -> Unit) {
        onProtocolClickListener = listener
    }

}