%insert(L: list, E: element, L2: list):
%(i,i,0)
insert([], E, [E]).
insert([H|T], E, [E,H|T]).
insert([H|T], E, [H|R]) :-
         insert(T, E, R).

%addToEndOfList(L:List,E:element,L2: List)
%(i,i,o)
addToEndOfList([],E,[E]).
addToEndOfList([H|T],E,[H|R]):-
	addToEndOfList(T,E,R).

%reverseList(L:list, L2:list)
%(i,o)
reverseList([],[]).
reverseList([H|T],R) :-
	reverseList(T,R1),
	addToEndOfList(R1,H,R).

%allDiff(L:list,E: element)
%(i,i).
allDiff([Head|Tail],Element):-
	Head =\= Element,
	Head-Element =:= 1.

allDiff([Head|Tail],Element):-
	Head =\= Element,
	Head-Element =:= -1.

allDiff([Head|Tail],Element):-
	Head =\= Element,
	Head-Element =\= 1,
	Head-Element =\= -1,
	allDiff(Tail,Element).


%second arg is reversed R and by calling all diff from here we check all elements from end to start
%allDiffWrapper(L:list, L2:list)
%(i,i)
allDiffWrapper(_,[]).
allDiffWrapper([Head1|Tail1],[Head|Tail]):-
	Head1 =\= Head,
	allDiff([Head1|Tail1],Head),
	allDiffWrapper([Head1|Tail1],Tail).

%if the argument is already head, no point in testing the previous elements as there are none
allDiffWrapper([Head1|Tail1],[Head|Tail]):-
	Head1 =:= Head,
	allDiffWrapper([Head1|Tail1],Tail).
		
%predicate reverses list and then calls the alldiff wrapper to test the list
%conditionWrapper(L:list)
%(i)
conditionWrapper(R):-
	reverseList(R,R1),
	allDiffWrapper(R,R1).

%permutations(L:list, L:list).
%(i,o)
permutations([], []).
permutations([H|T], R) :-
    permutations(T, RP),
    insert(RP, H, R),
    conditionWrapper(R).

generateList(N,Max,R):-
	N =:= Max,
	R = [].
generateList(N,Max,R):-
	N < Max,	
	N1 is N+1,
	generateList(N1,Max,R1),
	R = [N|R1].

generatePermutations(N,R1):-
	Max is N+1,
	generateList(1,Max,R),	
	permutations(R,R1).

