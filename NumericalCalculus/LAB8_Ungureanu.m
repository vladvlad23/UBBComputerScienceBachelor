disp("Start laboratory")
function y = simpson(f, a, b, n)
    h=(b-a)/n;
    x=a:h:b;
    y= h ./ 6 .* ( f(a) + f(b) + ...
      4 .* sum(f((x(2:n + 1) + x(1:n)) ./ 2)) + ...
      2 .* sum(f(x(2:n))));
end                

givenFunction = @(x) 2./(1+x.^2);
trapeziumFormula = @(a,b) (b-a)/2*(givenFunction(a)+givenFunction(b));

disp("\nResults for 1.a")
functionIntegral = integral(givenFunction,0,1)
trapeziumIntegral = trapeziumFormula(0,1)

disp("\nResults for b (look at graph):")
fill([0,0,1,1],[0,givenFunction(0),givenFunction(1),0],'b');

simpsonResult = simpson(givenFunction, 0, 1, 1);
printf("Results for c: Simpson approx is %d\n", simpsonResult)

disp("\nStart problem 2: ")
a = 1.4;
b = 2;
c = 1;
d = 1.5;

f = @(x,y) log(x + 2.*y);
res = (b - a) * ( d - c ) / 16 * ...
    ( f(a, c) + f(a, d) + f(b, c) + f(b, d) + ...
      2 * f((a + b) / 2, c) + ...
      2 * f((a + b) / 2, d) + 2 * f(a,(c + d) / 2) + ...
      2 * f(b, (c + d) / 2) + ...
      4 * f((a + b) / 2, (c + d) / 2));
printf("\nTrapezium formula for double integrals: %f\n", res)



disp("\nStart problem 3: ")
function res = repeatedTrapezium(a, b, f, n)
  h = (b-a)/n;
  i = a:h:b;
  res = h / 2 * (f( i(1)) + 2 * sum(f(i(2:end - 1))) + f(i(end)));
end

r = 110;
p = 75;
a = 0;
b = 2*pi;

f = @(x) sqrt(1-(p/r)^2*sin(x));

n1 = 2
n2 = 3
printf("\nPicked values %d and %d\n", n1,n2)

disp("Results: ")
firstTrapez = repeatedTrapezium(a,b,f,n1);
h1 = 60*r/(r*r - p*p) * firstTrapez

secondTrapez = repeatedTrapezium(a,b,f,n2);
h2 = 60*r/(r*r - p*p) * secondTrapez

disp("\nStart problem 4: ")
a=1; b = 2;
f = @(x) x .* log(x);
n = 1;
true_value = 0.636294368858383;
error = 0.0007;
printf("Error is %f\n", error);

while abs(repeated_trapezium(a, b, f, n) - true_value) > error
  n = n + 1;
end

printf("Minimum value of n is %d and the approximation is: %d\n", n, repeated_trapezium(a, b, f, n));                                            

disp("\nStart problem 5: ")
a = 0; b = pi;
f = @(x) 1 ./ (4+sin(20 .*x));
n1 = 10; n2 = 30;

r1 = simpson(f, a, b, n1);
r2 = simpson(f, a, b, n2);

printf("For n=%d, approximation of f is %d\n", n1, r1);
printf("For n=%d, approximation of f is %d\n", n2, r2);

disp("Start problem 6: ")
val = erf(0.5);
a = 0; b = 0.5;
f = @(x) e .^ (-x .^2);

n1 = 4;
approx1 = 2/sqrt(pi)*simpson(f, a, b, n1);
error1 = abs(approx1 - val);
printf("For n1=%d, the error is %d\n", n1, error1);

n2 = 10;
approx2 = 2/sqrt(pi)*simpson(f, a, b, n2);
error2 = abs(approx2 - val);
printf("For n2=%d, the error is %d\n", n2, error2);