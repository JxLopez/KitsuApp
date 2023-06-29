package com.jxlopez.kitsuapp.model

data class ProducerResponse(
    val data: Producer
)
data class Producer(
    val id: String,
    val attributes: ProducerAttributes,
)

data class ProducerAttributes(
    val name: String
)