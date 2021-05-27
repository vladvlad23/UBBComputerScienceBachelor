%add(L: list, E: number, N:number, L2:list)
%(i,o).
add([],_,_,[]).
add([Head|Tail],E,N,L1):-
	N =\= 1,
	N =\= 3,
	N =\= 7,
	N =\= 15,
	N1 is N+1,
	add(Tail,E,N1,L2),
	L1 = [Head|L2].
add([Head|Tail],E,N,L1):-
	N =:= 1,
	N1 is N+1,
	add(Tail,E,N1,L2),
	L1 = [Head,E|L2].

add([Head|Tail],E,N,L1):-
	N =:= 3,
	N1 is N+1,
	add(Tail,E,N1,L2),
	L1 = [Head,E|L2].

add([Head|Tail],E,N,L1):-
	N =:= 7,
	N1 is N+1,
	add(Tail,E,N1,L2),
	L1 = [Head,E|L2].

add([Head|Tail],E,N,L1):-
	N =:= 15,
	N1 is N+1,
	add(Tail,E,N1,L2),
	L1 = [Head,E|L2].
addHeterogeneous([],[]).
addHeterogeneous([Head],[Head]).
addHeterogeneous([Head,Head2|Tail],R):-
	not(is_list(Head2)),
	addHeterogeneous([Head2|Tail],R1),
	R = [Head|R1].
addHeterogeneous([Head,Head2|Tail],R):-
	is_list(Head2),
	add(Head2,Head,1,NewHead2),
	addHeterogeneous(Tail,R1),
	R = [Head,NewHead2|R1].

	

