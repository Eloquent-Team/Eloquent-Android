package berlin.eloquent.eloquentandroid.home.models

import berlin.eloquent.eloquentandroid.database.Recording

class DataSource {
    companion object {
        fun createDataSet(): ArrayList<Recording> {
            val list = ArrayList<Recording>()
            list.add(
                Recording(
                    "Congress for Development",
                    "2020-03-12",
                    31L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "GitHub Test Speech",
                    "2020-03-11",
                    134L,
                    listOf()
                )
            )

            list.add(
                Recording(
                    "Automated Software Testing Assessment",
                    "2020-02-10",
                    1405L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "Help me, this is no joke",
                    "2020-02-09",
                    1209374L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "Help me",
                    "2020-02-09",
                    1293856L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "OMG",
                    "2020-02-09",
                    1382L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "No waayy",
                    "2020-02-09",
                    945L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "How about no?",
                    "2020-02-09",
                    2382L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "Crona is killing my vibe",
                    "2020-02-09",
                    13L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "Germany wants shutdown",
                    "2020-02-09",
                    483L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "Why, Where, Who?",
                    "2020-02-09",
                    61L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "This way ->",
                    "2020-02-09",
                    42L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "Let's gooo",
                    "2020-02-09",
                    69L,
                    listOf()
                )
            )
            list.add(
                Recording(
                    "First tryyy First tryyy First tryyy First tryyy First tryyy First tryyy First tryyy",
                    "2020-02-09",
                    123456L,
                    listOf()
                )
            )
            return list
        }
    }
}