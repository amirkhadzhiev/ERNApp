package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.FineBgItemBinding
import gov.ukuk.ernapp.models.TundukProtocol1

class SearchBgAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val listBg: ArrayList<TundukProtocol1> = ArrayList()


    private lateinit var listener: SearchAdapter.OnItemClickListener

    fun addListener(listener: OnItemClickListener) {
//        this.listener = listener
    }

    fun clearAdapter() {
        if (!this.listBg.isEmpty()) {
            this.listBg.clear()
        }
    }

    fun addItems(listBg: java.util.ArrayList<TundukProtocol1>) {
        this.listBg.addAll(listBg)
//        this.list.sortWith(compareBy {
//            val format = SimpleDateFormat()
//            format.applyPattern("yyyy-dd-mm")
//            val docDate: Date = format.parse(it.created)
//            docDate
//        })
        this.listBg.reverse()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            FineBgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Viewholder).onBind(listBg[position])
    }

    override fun getItemCount(): Int = listBg.size

    class Viewholder(val binding: FineBgItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(abc: TundukProtocol1) {
            binding.sumNumberTv.setText(abc.violationAmmount)
            binding.codePaymentTv.setText(abc.paymentNumber)
//            binding.locationCreatedTv.setText(protocol.created)
        }
    }

    interface OnItemClickListener {
        fun onClick(id: Int, type: Int)
    }

}