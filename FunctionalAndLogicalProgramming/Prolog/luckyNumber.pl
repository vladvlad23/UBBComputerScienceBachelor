lucky(N):- N =:= 7; N=:=4.
%number is lucky if it only contains 4 and/or 7
lucky(N):- N>0,(N mod 10 =:= 7;N mod 10 =:= 4),
    lucky(N div 10).
