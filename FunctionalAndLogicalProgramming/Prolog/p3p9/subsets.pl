subsets([],[]).
subsets([_|L],S):-
    subsets(L,S).
subsets([A|L],[A|S]):-
    subsets(L,S).

permutations([],[]).
permutations([

pairs([A|L],X) :- pairs2(A,L,X).
pairs([_|L],X) :- pairs(L,X).
pairs2(A,[B|_],[A,B]).
pairs2(A,[_|L],X) :- pairs2(A,L,X).

subset([E|_],I,[E]):-
    E mod 2 =:= I.
subset([_|L],I,L1):-
    subset(L,I,L1).
subset([A|L],I,[A|L1]):-
    A mod 2 =:= 0,
    subset(L,I,L1).
subset([A|L],I,[A|L1]):-
    A mod 2 =:= 1,
    I1 is 1-I,
    subset(L,I1,L1).



inserare([], E, [E]).
inserare([H|T], E, [E,H|T]).
inserare([H|T], E, [H|R]) :-
         inserare(T, E, R).

% permutari(l1...ln) =
%	[], n = 0
%	inserare(permutari(l2...ln), l1), otherwise

% permutari(L:list, R:list)
% permutari(i, o)

permutari([], []).
permutari([H|T], R) :-
    permutari(T, RP),
    inserare(RP, H, R).

% createList(n) =
%	[], n = 0
%	n + createList(n - 1), n > 0

% createList(N:number, R:list)
% createList(i, o)

createList(0, []).
createList(N, [N|R]) :-
    N > 0,
    NN is N - 1,
    createList(NN, R).

% checkPerm(L:list, E:number)
% checkPerm(i, i)

checkPerm([], _).
checkPerm([H|T], L) :-
    check(L, H),
    checkPerm(T, [H|L]).

% diff(a, b) =
%	a - b, a > b
%	b - a, a < b

% diff(A:number, B:number, R:number)
% diff(i, i, o)

diff(A, B, R) :-
    A > B,
    R is A - B.
diff(A, B, R) :-
    A =< B,
    R is B - A.

% check(l1...ln, e) =
%	true, n = 0
%	true, diff(l1, e) = 1
%	check(l2...ln, e), n > 0
%	false, otherwise

% check(L:list, E:number)
% check(i, i)

check([], _).
check([H|_], X) :-
    diff(X, H, R),
    R =:= 1, !.
check([_|T], X) :-
    check(T, X).

% allsolutions(L:list, R:list)
% allsolutions(i, o)
onesolution(L, R) :-
    permutari(L, R),
    checkPerm(R, []).

% allsolutions(N:number, R:list)
% allsolutions(i, o)

allsolutions(N, R) :-
    createList(N, RL),
    findall(RP, onesolution(RL, RP), R).











