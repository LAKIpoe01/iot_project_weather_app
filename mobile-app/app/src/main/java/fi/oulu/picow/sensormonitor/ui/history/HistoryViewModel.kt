package fi.oulu.picow.sensormonitor.ui.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    var selectedRange by mutableStateOf(HistoryRange.DAY_24H)
        private set

    // 0 = current period (today / this week / etc.)
    // -1 = previous period (yesterday / last week / etc.)
    var periodOffset by mutableStateOf(0)
        private set

    fun selectRange(range: HistoryRange) {
        selectedRange = range
        periodOffset = 0 // reset to current when changing range
    }

    fun goToPreviousPeriod() {
        periodOffset -= 1
    }

    fun goToNextPeriod() {
        if (periodOffset < 0) {
            periodOffset += 1
        }
        // Optionally prevent going into the future: offset must be <= 0
    }

    /**
     * For now this just returns a simple label.
     * Later you can compute real dates based on range + offset.
     */
    fun getCurrentPeriodLabel(): String {
        return when (selectedRange) {
            HistoryRange.DAY_24H -> when (periodOffset) {
                0 -> "Today (last 24 h)"
                -1 -> "Yesterday"
                else -> "${-periodOffset} days ago (24 h)"
            }
            HistoryRange.WEEK -> when (periodOffset) {
                0 -> "This week"
                -1 -> "Last week"
                else -> "${-periodOffset} weeks ago"
            }
            HistoryRange.MONTH -> when (periodOffset) {
                0 -> "This month"
                -1 -> "Last month"
                else -> "${-periodOffset} months ago"
            }
            HistoryRange.YEAR -> when (periodOffset) {
                0 -> "This year"
                -1 -> "Last year"
                else -> "${-periodOffset} years ago"
            }
        }
    }
}
