package com.nyra.storyapp

import com.nyra.storyapp.data.model.DetailStory

object DataDummy {
    fun generateDummyQuoteResponse(): List<DetailStory> {
        val items: MutableList<DetailStory> = arrayListOf()
        for (i in 0..100) {
            val quote = DetailStory(
                "id $i",
                "name $i",
                "description $i",
                i.toString(),
                "createdAt + $i",
                (i * 1.0),
                (i * 1.0),
            )
            items.add(quote)
        }
        return items
    }
}