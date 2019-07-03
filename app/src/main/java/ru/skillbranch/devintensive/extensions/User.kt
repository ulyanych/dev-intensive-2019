package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView(): UserView {
    val fullName = "$firstName $lastName"
    val nickName = Utils.transliteration(fullName)
    val initials = Utils.initials(firstName, lastName)
    val status = if(lastVisit == null) "Never was online" else if (isOnline) "Online" else "last time online ${lastVisit.humanizeDiff()}"

    return UserView(
        id,
        fullName = fullName,
        nickName = nickName,
        avatar = avatar,
        initials = initials,
        status = status
    )
}