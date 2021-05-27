%checkValley(L:List, Down: Integer)
%flow(i,i).
checkValley([_],0).
checkValley([],0).
checkValley([H1,H2|T],Down):-
	H1>H2,
	Down =:= 1,
	checkValley([H2|T],Down).

checkValley([H1,H2|T],Down):-
	H1<H2,
	Down =:= 1,
	checkValley([H2|T],0).

checkValley([H1,H2|T],_):-
	H1==H2,
	checkValley([H2|T],0).

checkValley([H1,H2|_],Down):-
	H1>H2,
	Down =:= 0,
	fail.

checkValley([H1,H2|T],Down):-
	H1<H2,
	Down =:= 0,
	checkValley([H2|T],Down).

%checkValleyWrapper(L:list)
%flow(i)
checkValleyWrapper([H1,H2|T]):-
	H1>H2,
	Down is 1,
	checkValley([H2|T],Down).

