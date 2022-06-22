package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.FineItemBinding
import gov.ukuk.ernapp.models.Protocol
import kotlin.collections.ArrayList

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<Protocol> = ArrayList()


    private lateinit var listener: OnItemClickListener

    fun addListener(listener: OnItemClickListener) {
//        this.listener = listener
    }

    fun clearAdapter() {
        if (!this.list.isEmpty()) {
            this.list.clear()
        }
    }

    fun addItems(list: java.util.ArrayList<Protocol>) {
        this.list.addAll(list)
//        this.list.sortWith(compareBy {
//            val format = SimpleDateFormat()
//            format.applyPattern("yyyy-dd-mm")
//            val docDate: Date = format.parse(it.created)
//            docDate
//        })
        this.list.reverse()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            FineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Viewholder).onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class Viewholder(val binding: FineItemBinding) : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun onBind(protocol: Protocol) {
            binding.fineNumberTv.setText(protocol.code)
            binding.fineNameTv.setText(protocol.physicalData?.name + " " + protocol.physicalData?.surname + " " + protocol.physicalData?.patronymic)
            binding.sumNumberTv.setText((protocol.penaltyAmount?.plus(protocol.paymentFineTotal!!)).toString())
            binding.codePaymentTv.setText(protocol.paymentCode)
        }
    }

    interface OnItemClickListener {
        fun onClick(id: Int, type: Int)
    }
}
