%Write a predicate to transform a list in a set,
%considering the first occurence[1,2,3,1,2] is [1,2,3] not [3,1,2]
belongs(_,[]):-false.
belongs(E,[H|T]):-
    E=:=H;
    belongs(E,T).

removeAll(_, [], []).
removeAll(X, [X|T], L):- removeAll(X, T, L), !.
removeAll(X, [H|T], [H|L]):- removeAll(X, T, L ).

%flow model(i,0)
lToSet([],[]).
lToSet([Head|Tail],Set):-
    belongs(Head,Tail),
    removeAll(Head,Tail,Set1),
    lToSet(Set1,Set2), %don't ask why i need set2, problem was that Set somehow bound to Set1 so i had to make Set unbound before i bound it [head|rest of list]
    Set = [Head|Set2].
lToSet([Head|Tail],Set):-
    not(belongs(Head,Tail)),
    lToSet(Tail, Set1),
    Set = [Head|Set1].

% Write a predicate to decompose a list in a list respecting the
% following: [list of even numbers list of odd
% numbers] and also return the number of even numbers and the numbers of
% odd numbers. listToOddAndEven(LInput:list,LOdd:list,LEven:list).
% listToOddAndEven(i,o,o).

listToOddAndEven([],LOdd,LEven):-
    LOdd = [],
    LEven = [].
listToOddAndEven([H|T],LOdd,LEven):-
    0 is mod(H,2),
    listToOddAndEven(T,LOdd,LEven1),
    LEven = [H|LEven1].
listToOddAndEven([H|T],LOdd,LEven):-
    1 is mod(H,2),
    listToOddAndEven(T,LOdd1,LEven),
    LOdd = [H|LOdd1].








