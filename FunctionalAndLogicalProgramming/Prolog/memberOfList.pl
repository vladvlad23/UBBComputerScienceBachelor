memberOfList(E,[E|_]).
memberOfList(E,[_|L]) :- memberOfList(E,L)
