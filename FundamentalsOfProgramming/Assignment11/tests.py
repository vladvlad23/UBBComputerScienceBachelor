from main import backtrackingIterativeProperly
from main import backtracking

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

def runTests():
    tests()
    testsIterative()