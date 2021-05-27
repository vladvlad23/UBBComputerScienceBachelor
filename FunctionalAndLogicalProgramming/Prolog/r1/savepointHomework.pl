%append(L1: list, L2:list,L3:list)
%append(i,i,o)
append([],X,X).
append([H|T],L2,[H|W]):-
    append(T,L2,W).

%substitute(L1:list,L2:list,p:element,R3:list)
%substitute(i,i,i,o)
substitute([],_,_).
substitute([H|T],E,R):-
    H=:=E,
    substitute(T,E,R).
substitute([H|T],E,R):-
    H=\=E,
    substitute(T,E,R1),
    R = [H|R1].



