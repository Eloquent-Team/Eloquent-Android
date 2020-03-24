package berlin.eloquent.eloquentandroid.home.models

import berlin.eloquent.eloquentandroid.database.Recording

class DataSource {
    companion object {
        fun createDataSet(): ArrayList<Recording> {
            val list = ArrayList<Recording>()
            list.add(
                Recording(
                    0L,
                    "Congress for Development",
                    "2020-03-12",
                    31L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "GitHub Test Speech",
                    "2020-03-11",
                    134L,
                    ""
                )
            )

            list.add(
                Recording(
                    0L,
                    "Automated Software Testing Assessment",
                    "2020-02-10",
                    1405L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "Help me, this is no joke",
                    "2020-02-09",
                    1209374L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "Help me",
                    "2020-02-09",
                    1293856L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "OMG",
                    "2020-02-09",
                    1382L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "No waayy",
                    "2020-02-09",
                    945L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "How about no?",
                    "2020-02-09",
                    2382L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "Crona is killing my vibe",
                    "2020-02-09",
                    13L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "Germany wants shutdown",
                    "2020-02-09",
                    483L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "This way ->",
                    "2020-02-09",
                    42L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "Why, Where, Who?",
                    "2020-02-09",
                    61L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "Let's gooo",
                    "2020-02-09",
                    69L,
                    ""
                )
            )
            list.add(
                Recording(
                    0L,
                    "First tryyy First tryyy First tryyy First tryyy First tryyy First tryyy First tryyy",
                    "2020-02-09",
                    123456L,
                    ""
                )
            )
            return list
        }
    }
}