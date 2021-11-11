package com.xl.xl_base.tool.ktx

fun <E> MutableList<E>.addIndex(index: Int, element: E) {
    if (index + 1 > size) {
        add(element)
    } else {
        add(index, element)
    }
}

fun <E> List<E>.subIndex(index: Int) = if (index + 1 > size) this else this.subList(0, index)

fun Array<String>.containsIgnore(target: String?): Boolean {
    for (element in this) {
        if (element.equals(target, true)) {
            return true
        }
    }
    return false
}

fun Collection<String>.containsIgnore(target: String?): Boolean {
    for (element in this) {
        if (element.equals(target, true)) {
            return true
        }
    }
    return false
}

