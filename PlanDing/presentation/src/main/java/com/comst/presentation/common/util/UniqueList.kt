package com.comst.presentation.common.util

class UniqueList<T : Any, K>(private val keySelector: (T) -> K, private val list: List<T> = emptyList()) {

    fun addOrUpdate(item: T): UniqueList<T, K> {
        val newList = list.toMutableList()
        val existingIndex = newList.indexOfFirst { keySelector(it) == keySelector(item) }
        if (existingIndex >= 0) {
            newList[existingIndex] = item
        } else {
            newList.add(item)
        }
        return UniqueList(keySelector, newList)
    }

    fun remove(item: T): UniqueList<T, K> {
        val newList = list.toMutableList()
        val existingIndex = newList.indexOfFirst { keySelector(it) == keySelector(item) }
        if (existingIndex >= 0) {
            newList.removeAt(existingIndex)
        }
        return UniqueList(keySelector, newList)
    }

    fun toList(): List<T> {
        return list.toList()
    }

    fun contains(item: T): Boolean {
        return list.any { keySelector(it) == keySelector(item) }
    }
}