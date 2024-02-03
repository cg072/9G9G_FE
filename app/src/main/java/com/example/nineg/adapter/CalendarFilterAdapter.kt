package com.example.nineg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nineg.R
import com.example.nineg.databinding.ItemCheckFilterBinding
import com.example.nineg.model.CheckFilterModel

class CalendarFilterAdapter(private val onClick: (CheckFilterModel) -> Unit) :
    ListAdapter<CheckFilterModel, RecyclerView.ViewHolder>(CalendarFilterDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCheckFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckFilterViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckFilterViewHolder) {
            holder.bind(getItem(position))
        }
    }

    class CheckFilterViewHolder(
        private val binding: ItemCheckFilterBinding,
        private val onClick: (CheckFilterModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CheckFilterModel) {
            binding.itemCheckFilterTitle.text = item.title
            binding.itemCheckFilterIcon.visibility =
                if (item.isCheck) View.VISIBLE else View.INVISIBLE
            binding.itemCheckFilterDim.visibility =
                if (item.isCheck) View.VISIBLE else View.INVISIBLE

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

}

object CalendarFilterDiffUtil : DiffUtil.ItemCallback<CheckFilterModel>() {
    override fun areItemsTheSame(oldItem: CheckFilterModel, newItem: CheckFilterModel): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: CheckFilterModel, newItem: CheckFilterModel): Boolean {
        return oldItem.isCheck == newItem.isCheck
    }
}