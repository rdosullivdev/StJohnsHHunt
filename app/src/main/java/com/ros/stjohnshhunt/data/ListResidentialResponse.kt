package com.ros.stjohnshhunt.data

data class ListResidentialResponse(val ErrorCode: ErrorCode,
                                   val Paging: Paging,
                                   val Results: List<PropertyDetails>?)

data class ErrorCode(val Id: Long, val Description: String?)

data class Paging(
    val RecordsPerPage: Int,
    val CurrentPage: Int,
    val TotalRecords: Int,
    val MaxRecords: Int,
    val TotalPages: Int)

data class PropertyDetails(
    val Id: String,
    val MlsNumber: String?,
    val PublicRemarks: String?,
    val Building: Building?,
    val Individual: List<Realtor>?,
    val Property: Property?,
    val Land: Land?,
    val PostalCode: String?,
    val RelativeDetailsURL: String?,
    val InsertedDateUTC: Long?,
    val TimeOnRealtor: String?)

data class Property(
    val Price: String?, 
    val Type: String?,
    val Address: BuildingAddress?,
    val Photo: List<Photo>?,
    val OwnershipType: String?,
    val PriceUnformattedValue: String?)

data class Building(
    val BathroomTotal: String?,
    val Bedrooms: String?,
    val SizeInterior: String?,
    val StoriesTotal: String?,
    val Type: String?)

data class BuildingAddress(
    val AddressText: String?,
    val Longitude: String?,
    val Latitude: String?)

data class Realtor(val IndividualID: Long, val Name: String?)

data class Land(val SizeTotal: String?, val AccessType: String?)

data class Photo(val HighResPath: String?, val LowResPath: String?)

/*
 */