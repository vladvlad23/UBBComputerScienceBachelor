%a. For a list of integer number,
% write a predicate to add in list after 1-st, 3-rd, 7-th, 15th element a given value e.

%addAfter(L:List, E: number, R:List).
%flow(i,i,o).

addAfter([],E,[E]).
addAfter([Head|Tail],E,Result):-
    addAfter(Tail,E,Result1),
    Result = [Head|Result1].


%insertAfterGiven(L:List,E:number,N:number,R:List).
%flow(i,i,i,o).

insertAfterGiven([],_,_,[]).
insertAfterGiven([Head|Tail],E,N,Result):-
    N =\= 1,
    N =\= 3,
    N =\= 7,
    N =\= 15,
    N1 is N+1,
    insertAfterGiven(Tail,E,N1,Result1),
    Result = [Head|Result1].
insertAfterGiven([Head|Tail],E,N,Result):-
    N =:= 1,
    N1 is N+1,
    insertAfterGiven(Tail,E,N1,Result1),
    Result = [Head,E|Result1].
insertAfterGiven([Head|Tail],E,N,Result):-
    N =:= 3,
    N1 is N+1,
    insertAfterGiven(Tail,E,N1,Result1),
    Result = [Head,E|Result1].
insertAfterGiven([Head|Tail],E,N,Result):-
    N =:= 7,
    N1 is N+1,
    insertAfterGiven(Tail,E,N1,Result1),
    Result = [Head,E|Result1].
insertAfterGiven([Head|Tail],E,N,Result):-
    N =:= 15,
    N1 is N+1,
    insertAfterGiven(Tail,E,N1,Result1),
    Result = [Head,E|Result1].

%B

insertHeterog([E],[E]).
insertHeterog([Head1,Head2|Tail],Result):-
    not(is_list(Head2)),
    insertHeterog([Head2|Tail],Result1),
    Result = [Head1|Result1].

insertHeterog([Head1,Head2|Tail],Result):-
    is_list(Head2),
    insertAfterGiven(Head2,Head1,1,ListWithAppendage),
    insertHeterog(Tail,Result1),
    Result = [Head1,ListWithAppendage|Result1].















