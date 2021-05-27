format long

function result = SolveGauss(n, a, b)
  x = zeros(1, n);
  
  for p=1:(n-1)
    # find line with maximum value
    q = p;
    for i=p+1:n
      if (abs(a(q, p)) < abs(a(i, p)))
        q = i;
      endif
    endfor
    if (a(q, p) == 0)
      printf("Error!\n");
      return;
    endif
    
    # swap lines
    for j=1:n
      aux = a(p, j);
      a(p, j) = a(q, j);
      a(q, j) = aux;
    endfor
    aux = b(p);
    b(p) = b(q);
    b(q) = aux;
    
    # make zeros below the current element
    for i=p+1:n
      m = a(i, p) / a(p, p);
      for j=p:n
        a(i, j) = a(i, j) - m * a(p, j);
      endfor
      b(i) = b(i) - m * b(p);
    endfor
  endfor
  if (a(n, n) == 0)
    printf("Error!\n");
    return;
  endif
  for i=n:-1:1
    rest = 0;
    for j=i+1:n
      rest = rest + x(j) * a(i, j);
    endfor
    x(i) = (b(i) - rest) / a(i, i);
  endfor
  result = x;
endfunction

disp("First problem")

a = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10];
b = [32 23 33 31];
n = size(a)(1);
x = SolveGauss(n, a, b)
cond1 = cond(a)

b2 = [32.1 22.9 33.1 30.9];
gaussResult = SolveGauss(n, a, b2);

disp("Result of Gauss:")
gaussResult
inputRelativeError = norm(b - b2) / norm(b)
resultRelativeError = norm(x - gaussResult) / norm(x)
ratio = resultRelativeError / inputRelativeError;
printf("Result is %d\n", ratio)


a2 = [10 7 8.1 7.2; 7.08 5.04 6 5; 8 5.98 9.89 9; 6.99 4.99 9 9.98];
gaussResult = SolveGauss(n, a2, b)
inputRelativeError= norm(a - a2) / norm(a)
resultRelativeError= norm(x - gaussResult) / norm(x)
ratio = resultRelativeError / resultRelativeError

disp("Second problem")

for i=10:15
  printf("C(%d) = %f\n", i, cond(hilb(i)));
endfor

disp("Third problem:")

a = [1 1 1 1; 2 3 1 5; -1 1 -5 3; 3 1 7 -2];
b = [10 31 -2 18];
n = size(a)(1);
x = SolveGauss(n, a, b)