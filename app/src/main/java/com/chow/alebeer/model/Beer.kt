package com.chow.alebeer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chow.alebeer.other.orZero

data class BeerResponse(
    val price: String?,
    val name: String?,
    val image: String?,
    val id: Int?
)

data class BeerModel(
    val price: String,
    val name: String,
    val image: String = "",
    val id: Int,
    val note: String = "",
    val isSaving: Boolean = false,
    val localImagePath: String = ""
)

@Entity(tableName = "beer")
data class BeerEntity(
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    val localImagePath: String,
    @ColumnInfo(name = "note")
    val note: String,
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long
) : BaseModel()

fun BeerResponse?.toBeerModel() = BeerModel(
    price = this?.price.orEmpty(),
    name = this?.name.orEmpty(),
    image = this?.image.orEmpty(),
    id = this?.id.orZero()
)

fun BeerModel.toBeerEntity() = BeerEntity(
    price = price,
    name = name,
    note = note,
    id = id,
    localImagePath = localImagePath,
    createdAt = System.currentTimeMillis()
)

fun BeerEntity.toBeerModel() = BeerModel(
    price = price,
    name = name,
    localImagePath = localImagePath,
    id = id,
    note = note
)

fun List<BeerResponse?>?.toBeerList() = this?.map { it.toBeerModel() }.orEmpty()


