package com.app.masterplan.data.mapper

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateSerializer {

    fun toLocalDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ISO_DATE
        return LocalDate.parse(date,formatter)
    }

    fun toLocalDateTime(date: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.parse(date,formatter)
    }


}