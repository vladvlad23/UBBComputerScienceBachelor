def createSet(newIndexSet,givenSet):
    newSet = []
    for i in range(len(givenSet)):
        if newIndexSet[i] == 0:
            newSet.append(givenSet[i])
        else:
            newSet.append(givenSet[i]*(-1))
    return newSet

def solution(indexSet,givenSet):
    newSet = createSet(indexSet,givenSet)
    if sum(newSet)>0:
        return newSet
    else:
        return None

def backtracking(newIndexSet,givenSet,solutions):
    '''
    The function will compute the requirement.
    We'll create another list which will have on the index i 0 if the element is positive and 1 if it's negative
    for each such list we will form the sum, check it and see if it is positive
    :param newSet: the new set which will change with every iteration
    :param givenSet: the original set
    :return: recursively find solutions
    '''
    if len(newIndexSet) > len(givenSet):
        return

    newIndexSet.append(0)
    for i in range(0,len(givenSet)):
        if len(newIndexSet) == len(givenSet):
            set = solution(newIndexSet,givenSet)
            if set is not None:
                if set not in solutions:
                    solutions.append(set)
        backtracking(newIndexSet[:],givenSet,solutions)

        newIndexSet[-1] = 1
        if len(newIndexSet) == len(givenSet):
            set = solution(newIndexSet,givenSet)
            if set is not None:
                if set not in solutions:
                    solutions.append(set)
        backtracking(newIndexSet[:],givenSet,solutions)

def backtrackingIterative(givenSet):
    stackReplacement = []
    solutions = []
    stackReplacement.append([0])
    stackReplacement.append([1])
    while len(stackReplacement)!=0:
        indexSet = stackReplacement.pop()
        if len(indexSet) == len(givenSet):
            set = solution(indexSet,givenSet)
            if set is not None:
                solutions.append(set)
        if len(indexSet)<len(givenSet):
            indexSetWithPlus = indexSet[:]
            indexSetWithPlus.append(0)

            indexSetWithMinus = indexSet[:]
            indexSetWithMinus.append(1)
            stackReplacement.append(indexSetWithPlus)
            stackReplacement.append(indexSetWithMinus)

    return solutions

def backtrackingIterativeProperly(givenSet):
        newSet = [-1]
        solutions = []
        while len(newSet) > 0:
            if newSet[-1] == 1:
                newSet = newSet[:-1]
            else:
                if newSet[-1] < 1:
                    newSet[-1] += 1
                if len(newSet) == len(givenSet):
                    set = solution(newSet,givenSet)
                    if set is not None and set not in solutions:
                        solutions.append(set)
                    if newSet[-1] == 1:
                        newSet = newSet[:-1]
                elif len(newSet) < len(givenSet):
                    newSet.append(-1)

        return solutions


def tests():
    first = []
    backtracking([],[1,-2,3],first)
    assert first == [[1,-2,3],[1,2,3],[-1,2,3]]

    second = []
    backtracking([],[1,2,3],second)
    assert sorted(second) == sorted(first)

    third = []
    backtracking([],[1,2],third)
    assert sorted(third) == sorted([[1,2],[-1,2]])

    fourth = []
    backtracking([],[1,-2],fourth)
    assert sorted(third) == sorted(fourth)

def testsIterative():
    first = backtrackingIterativeProperly([1,-2,3])
    assert sorted(first) == sorted([[1,-2,3],[1,2,3],[-1,2,3]])

    second = backtrackingIterativeProperly([1,2,3])
    assert sorted(second) == sorted(first)

    third = backtrackingIterativeProperly([1,2])
    assert sorted(third) == sorted([[1,2],[-1,2]])

    fourth = backtrackingIterativeProperly([1,-2])
    assert sorted(third) == sorted(fourth)

tests()
testsIterative()
