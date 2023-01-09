package com.example.gggsapplication.RentALawn

// Creating another data class that will be used to organized the data collected from the database
data class LawnListing(var address : String ?= null, var availableHours : String ?= null,
                       var price : String ?= null, var email : String ?= null,
                       var phone : String ?= null, var visibility : Boolean = false)
