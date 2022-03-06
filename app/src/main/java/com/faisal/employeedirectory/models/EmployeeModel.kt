package com.faisal.employeedirectory.models

data class EmployeeModel(
    var id: Int? = null,
    var name: String? = null,
    var username: String? = null,
    var email: String? = null,
    var profileImage: String? = null,
    var address: Address? = Address(),
    var phone: String? = null,
    var website: String? = null,
    var company: Company? = null
)

data class Address(
    var street: String? = null,
    var suite: String? = null,
    var city: String? = null,
    var zipcode: String? = null,
    var geo: GeoLocation? = null
)

data class GeoLocation(
    var lat: String? = null,
    var lng: String? = null
)

data class Company(
    var name: String? = null,
    var catchPhrase: String? = null,
    var bs: String? = null
)