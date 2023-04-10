fun solution(first: Set<Int>, second: Set<Int>): Set<Int> {
    val result: MutableSet<Int> = mutableSetOf()
    val size = second.size
    for (elemet in first) {
        if (elemet % size == 0) result.add(elemet)
    }
    return result.toSet()
}
