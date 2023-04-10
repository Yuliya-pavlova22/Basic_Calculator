fun solution(setSource: Set<String>, listSource: MutableList<String>): MutableSet<String> {
    return (setSource.toMutableSet() + listSource).toMutableSet()
}
