package com.vsahin.volkytv.data.model

data class Entry(
    val id: String,
    val title: String,
    val description: String,
    val publishedDate: Long,
    val credits: List<Credit>,
    val parentalRatings: List<ParentalRating>,
    val contents: List<Content>,
    val images: List<Image>,
    val categories: List<Category>
)