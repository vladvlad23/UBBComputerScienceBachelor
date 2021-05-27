eliminatePerfectSquares([],[]).
eliminatePerfectSquares([H|T],R):-
	perfectSquare(H,1),
	eliminatePerfectSquares(T,R).
eliminatePerfectSquares([H|T],R):-
	not(perfectSquare(H,1)),
	eliminatePerfectSquares(T,R1),
	R = [H|R1].

perfectSquare(X,Y):-
	X =\= Y*Y,
	Y1 is Y+1,
	Y1 =< X/2,
	perfectSquare(X,Y1).

perfectSquare(X,Y):-
	X=:=Y*Y.


insertAtEnd(X,[ ],[X]).
insertAtEnd(X,[H|T],[H|Z]) :- insertAtEnd(X,T,Z).    


reverseList([],Z,Z).

reverseList([H|T],Z,Acc) :- reverseList(T,Z,[H|Acc]).
	
multiplyProcedure([],_,0,_).
multiplyProcedure([H|T],Y,R,Increment):-
	X1 is H*Increment,
	Increment1 is Increment*10,
	multiplyProcedure(T,Y,R1,Increment1),
	R is R1+(X1*Y).	
	
multiplyWrapper(X,Y,R):-
	multiplyProcedure(X,Y,R,1).

multiplyByHand(X,Y,R):-
	reverseList(X,ReversedList,[]),
	multiplyWrapper(ReversedList,Y,R).
