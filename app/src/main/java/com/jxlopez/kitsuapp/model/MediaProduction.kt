package com.jxlopez.kitsuapp.model

data class MediaProductions(
    val data: List<MediaProduction> = emptyList()
)
data class MediaProduction(
    val id: String,
    val attributes: MediaProductionAttributes
)

data class MediaProductionAttributes(
    val createdAt: String,
    val role: String
)

enum class MediaProductionRole(val role: String) {
    STUDIO("studio"),
    PRODUCER("Producer")
}
