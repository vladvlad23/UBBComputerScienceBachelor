factorialTeRog(0, 1).
factorialTeRog(N, F) :-
    N > 0,
    N1 is N-1,
    factorialTeRog(N1, F1),
    F is N * F1.
