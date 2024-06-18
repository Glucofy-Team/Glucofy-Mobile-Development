package com.dicoding2.glucofy.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding2.glucofy.data.local.entity.GlucoseDataEntity

class ListGlucoseDiffCallback(private val oldFavoriteList: List<GlucoseDataEntity>, private val newFavoriteList: List<GlucoseDataEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].id == newFavoriteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavoriteList[oldItemPosition]
        val newNote = newFavoriteList[newItemPosition]
        return oldNote.glucose == newNote.glucose && oldNote.date == newNote.date
    }
}