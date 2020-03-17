package berlin.eloquent.eloquentandroid.home.models

class DataSource {
    companion object {
        fun createDataSet(): ArrayList<Recording> {
            val list = ArrayList<Recording>()
            list.add(
                Recording(
                    "Congress for C.R.",
                    "2020-03-12",
                    "20:30",
                    arrayOf()
                )
            )
            list.add(
                Recording(
                    "Congress for Martin",
                    "2020-03-12",
                    "20:45",
                    arrayOf()
                )
            )

            list.add(
                Recording(
                    "Playing Piano",
                    "2020-03-12",
                    "2:30",
                    arrayOf()
                )
            )
            list.add(
                Recording(
                    "Someone is screaming in the background",
                    "2020-03-12",
                    "20:30",
                    arrayOf()
                )
            )
            return list
        }
    }
}