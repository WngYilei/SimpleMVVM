package com.xl.common.tool.ktx

/**
 * 描述:Collection 扩展
 *
 * author zys
 * create by 2020/5/28
 */
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

