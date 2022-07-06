package com.valerasetrakov.fragmentslesson.base

/**
 * Список фильтров для чатов
 */
enum class FilterEvent {
    /**
     * Все чаты
     */
    ALL,

    /**
     * Отображать только прочитанные чаты
     */
    ONLY_READ,

    /**
     * Отображать только непрочитанные чаты
     */
    ONLY_UNREAD;
}