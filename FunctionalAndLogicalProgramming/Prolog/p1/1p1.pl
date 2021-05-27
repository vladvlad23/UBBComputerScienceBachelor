%a. Write a predicate to determine the lowest common multiple of a list formed from integer numbers.
%b. Write a predicate to add a value v after 1-st, 2-nd, 4th, 8-th, � element in a list.

lowestCommonMultiple(A,B,Result):-
    A1 is A,
    B1 is B,
    greatestCommonDivisor(A1,B1,ResultGCD),
    Result is (A*B)/ResultGCD.

greatestCommonDivisor(A,0,R):- R is A.
greatestCommonDivisor(A,B,R):-
    B =\= 0,
    B1 is mod(A,B),
    A1 is B,
    greatestCommonDivisor(A1,B1,R).


removeAllOccurences(_,[],[]).
removeAllOccurences(E,[Head|Tail],FinalList):-
    Head =:= E,
    removeAllOccurences(E,Tail,FinalList).
removeAllOccurences(E,[Head|Tail],FinalList):-
    Head =\= E,
    removeAllOccurences(E,Tail,FinalList1),
    FinalList = [Head,FinalList1].



