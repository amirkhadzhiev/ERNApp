package gov.ukuk.ernapp.ui.fragments.searchPlate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gov.ukuk.ernapp.databinding.CarItemBinding
import gov.ukuk.ernapp.models.CarInfo


class SearchCarAdapter : RecyclerView.Adapter<SearchCarAdapter.ViewHolder>(){

    private val listBg: ArrayList<CarInfo> = ArrayList()

    fun clearAdapter() {
        if (!this.listBg.isEmpty()) {
            this.listBg.clear()
        }
    }

    fun addItems(listBg: java.util.ArrayList<CarInfo>) {
        if(!this.listBg.containsAll(listBg)){
            this.listBg.addAll(listBg)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCarAdapter.ViewHolder {
        val binding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SearchCarAdapter.ViewHolder, position: Int) {
        val car = listBg[position]
        holder.binding.apply {
            auto.text = car.brand + " " + car.model
            carNumber.text = "Гос.номер: " + car.govPlate
            color.text = "Цвет: " + car.color
            autoType.text = "Тип авто: " + car.bodyType
            autoYear.text = "Год авто: "  + car.year
            VIN.text = "VIN код: " + car.vin

        }


      //  holder.itemView.setOnClickListener { view ->
      //      val bundle = Bundle().apply {
      //          putSerializable("plate", tProtocol.govPlate)
      //          putSerializable("position", position)
      //      }
      //          view.findNavController().navigate(gov.ukuk.ernapp.R.id.action_fragmentSearch_to_violationFragment, bundle)
      //      }

    }

    override fun getItemCount(): Int = listBg.size

    class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)


}