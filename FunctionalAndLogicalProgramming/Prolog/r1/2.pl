/*
 * 2. a. Substitute the i-th element from a list, with a value v.
 * b. Determine difference of two sets represented as lists.
*/

belongs(_,[]):-false.
belongs(E,[H|T]):-
    E=:=H;
    belongs(E,T).


replace([_|T], 0, X, [X|T]).
replace([H|T], Poz, X, [H|R]):-
    Poz>0,
    Poz1 is Poz-1,
    replace(T,Poz1,X,R).


setDif([],X,X).
setDif([H|T],T2,Final):-
    belongs(H,T2),
    setDif(T,T2,Final).
setDif([H|T],T2,[H|Final]):-
    \+belongs(H,T2),
    setDif(T,T2,Final).
