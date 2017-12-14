package de.d3adspace.mercantor.client.util

/**
 * @author Felix Klauke <fklauke></fklauke>@itemis.de>
 */
class RoundRobinList<ContentType>(private val content: MutableList<ContentType>) {
    private var currentPosition: Int = 0

    val isEmpty: Boolean
        get() = content.isEmpty()

    fun getContent(): List<ContentType> = content

    fun size(): Int = content.size

    fun add(element: ContentType): Boolean = content.add(element)

    fun remove(element: ContentType) = content.remove(element)

    fun get(): ContentType {
        if (content.isEmpty()) {
            throw IllegalStateException("I don't have any elements.")
        }

        if (currentPosition == content.size) {
            currentPosition = 0
        }

        val element = content[currentPosition]

        currentPosition++

        return element
    }
}
