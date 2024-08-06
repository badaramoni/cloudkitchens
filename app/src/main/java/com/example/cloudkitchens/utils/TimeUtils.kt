/**
 * Utility object for time-related functions.
 */
object TimeUtils {

    /**
     * Returns a string representing the relative time ago from the given timestamp.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return A string representing how long ago the timestamp was from the current time.
     */
    fun getRelativeTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        if (diff < 0) {
            return "....."
        }

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return when {
            seconds < 60 -> "$seconds seconds ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            else -> {
                val days = hours / 24
                "$days days ago"
            }
        }
    }
}
