package com.penguinairlines.hivetraker.data.models
import java.util.Date

data class Log ( //if val is null display as N/A
    val logName: String,
    val hiveName: String,
    val notes: String? = null,
    val date: Date = Date(),
    val temper: String? =null,
    val hiveCondition: String?=null,
    val numFrames: Int?=null, //Need to implement way to have previous log of numFrames be used if user does not include
    val beeFramesBool: Boolean? = null,
    val numBeeFrames: Int? = null,
    val honeyFramesBoolBody: Boolean? = null,
    val numHoneyFramesBody: Int? = null,
    val honeyFramesBoolTop: Boolean? = null,
    val numHoneyFramesTop: Int? = null,
    val broodEgg: Boolean? = null,
    val broodLarvae: Boolean? = null,
    val cappedBrood: Boolean? = null,
    val layingPatter: String? = null,
    val queenSeen: Boolean? = null,
    val queenNotes: String? = null,
    val queenCells: Boolean? = null,
    val queenCellsNotes: String? = null,
    val noQueen: Boolean? = null,
    val droneCells: Boolean? = null,
    val dronesBool: Boolean? = null,
    val dronesNotes: String? = null,
    val disease: String? = null,
    val pest: String? = null,
    val diseasePestNotes: String? = null,
    val weather: String? = null
)