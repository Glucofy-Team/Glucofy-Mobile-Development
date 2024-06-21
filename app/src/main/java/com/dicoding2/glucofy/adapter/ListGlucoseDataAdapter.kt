package com.dicoding2.glucofy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.data.local.entity.GlucoseDataEntity
import com.dicoding2.glucofy.databinding.ItemGlucoseBinding
import com.dicoding2.glucofy.helper.ListGlucoseDiffCallback

class ListGlucoseDataAdapter : RecyclerView.Adapter<ListGlucoseDataAdapter.GlucoseViewHolder>() {
    private lateinit var setOnItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.setOnItemClickCallback = onItemClickCallback
    }


    private val listGlucose = ArrayList<GlucoseDataEntity>()
    fun setListGlucose(listNotes: List<GlucoseDataEntity>) {
        val diffCallback = ListGlucoseDiffCallback(this.listGlucose, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listGlucose.clear()

        this.listGlucose.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlucoseViewHolder {
        val binding = ItemGlucoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GlucoseViewHolder(binding)
    }
    override fun onBindViewHolder(holder: GlucoseViewHolder, position: Int) {
        holder.bind(listGlucose[position])

        holder.itemView.setOnClickListener {
            setOnItemClickCallback.onItemClicked(listGlucose[position])
        }
    }
    override fun getItemCount(): Int {
        return listGlucose.size
    }

    inner class GlucoseViewHolder(private val binding: ItemGlucoseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GlucoseDataEntity) {
            with(binding) {
                tvGlucose.text = data.glucose.toString()
                tvCondition.text = data.condition
           }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: GlucoseDataEntity)
    }
}