package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.FineBgItemBinding
import gov.ukuk.ernapp.databinding.FinePinItemBinding
import gov.ukuk.ernapp.models.TundukProtocol1


class SearchBgAdapter : RecyclerView.Adapter<SearchBgAdapter.ViewHolder>() {

    private val listBg: ArrayList<TundukProtocol1> = ArrayList()

    fun clearAdapter() {
        if (!this.listBg.isEmpty()) {
            this.listBg.clear()
        }
    }

    fun addItems(listBg: java.util.ArrayList<TundukProtocol1>) {
        if (!this.listBg.containsAll(listBg)) {
            this.listBg.addAll(listBg)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBgAdapter.ViewHolder {
        val binding =
            FinePinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SearchBgAdapter.ViewHolder, position: Int) {
        val tProtocol = listBg[position]
        holder.binding.apply {
            codePaymentTv.setText(tProtocol.paymentNumber)
            sumNumberTv.setText(
                tProtocol.violationAmmount.substring(
                    0,
                    tProtocol.violationAmmount.indexOf(".")
                )
            )
            numberCarPin.setText(tProtocol.govPlate)
        }


        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putSerializable("plate", tProtocol.govPlate)
                putSerializable("paymentNumber", tProtocol.paymentNumber)
            }
            view.findNavController()
                .navigate(gov.ukuk.ernapp.R.id.action_fragmentSearch_to_violationFragment, bundle)
        }

    }

    override fun getItemCount(): Int = listBg.size

    class ViewHolder(val binding: FinePinItemBinding) : RecyclerView.ViewHolder(binding.root)


}