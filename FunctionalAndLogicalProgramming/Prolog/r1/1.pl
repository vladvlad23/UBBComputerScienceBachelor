%turn a given list to a set
belongs(_,[]):-false.
belongs(E,[H|T]):-
    E=:=H;
    belongs(E,T).

lToSet([],[]).
lToSet([Head|Tail],Set):-
    belongs(Head,Tail),
    !,
    lToSet(Tail,Set).
lToSet([Head|Tail],[Head|X]):-
    lToSet(Tail, X).

unionOfSets([],Set,Final) :- merge([],Set,Final).
unionOfSets([Head|Tail],Set,Final):-
    belongs(Head,Set),
    !,
    unionOfSets(Tail,Set,Final).
unionOfSets([Head|Tail],[Head|Set]):-
    unionOfSets(Tail,Set).

