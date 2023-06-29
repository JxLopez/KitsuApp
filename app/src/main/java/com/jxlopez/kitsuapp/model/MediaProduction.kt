package com.jxlopez.kitsuapp.model

data class MediaProductions(
    val data: List<MediaProduction> = emptyList()
)
data class MediaProduction(
    val id: String,
    val attributes: MediaProductionAttributes,
    //val relationships: MediaProductionRelationships
)

data class MediaProductionAttributes(
    val createdAt: String,
    val role: String
)

data class MediaProductionRelationships(
    val media: MediaProductionMedia,
    val company: MediaProductionCompany
)

data class MediaProductionMedia(
    val links: MediaProductionLinks
)

data class MediaProductionCompany(
    val links: MediaProductionLinks
)

data class MediaProductionLinks(
    val self: String,
    val related: String
)

enum class MediaProductionRole(val role: String) {
    STUDIO("studio"),
    PRODUCER("Producer")
}
