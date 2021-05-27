%append(L1: list, L2:list,L3:list)
%append(i,i,o)
append([],X,X).
append([H|T],L2,[H|W]):-
    append(T,L2,W).

%substitute(L1:list,L2:list,p:element,R3:list)
%substitute(i,i,i,o)
substitute([],_,_,R):- R=[].
substitute([H|T],L,E,R):-
    H=:=E,
    substitute(T,L,E,R1),
    append(L,R1,R).
substitute([H|T],L,E,R):-
    H=\=E,
    substitute(T,L,E,R1),
    R = [H|R1].

%determine the element from the n-th position in a list
%determine(L:list,n:Integer,E).
%flow(i,i,o).

determine(_,N,_):-
    N < 1,
    write("N not good").
determine([Head|_],N,E):-
    N =:= 1,
    E is Head.
determine([_|Tail],N,E):-
    N =\= 1,
    N1 is N-1,
    determine(Tail,N1,E).
