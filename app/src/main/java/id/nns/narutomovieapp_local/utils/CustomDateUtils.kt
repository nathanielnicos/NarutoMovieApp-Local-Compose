package id.nns.narutomovieapp_local.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toFormattedDate(): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
        val date = LocalDate.parse(this, inputFormatter)
        date.format(outputFormatter)
    } catch (_: Exception) {
        this
    }
}